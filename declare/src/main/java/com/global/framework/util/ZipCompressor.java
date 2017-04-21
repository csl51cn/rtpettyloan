package com.global.framework.util;

import java.io.BufferedInputStream;    
import java.io.File;    
import java.io.FileInputStream;    
import java.io.FileOutputStream;    
import java.util.zip.CRC32;    
import java.util.zip.CheckedOutputStream;    
   
import org.apache.tools.zip.ZipEntry;    
import org.apache.tools.zip.ZipOutputStream;    

/**
 * ZIP压缩工具类
 * 
 * @author chen.feng
 * @date 2014-7-17 下午12:31:36
 */
public class ZipCompressor {
	private static final int BUFFER = 8192;

	private File zipFile;
	
	/**
	 * 
	 * @param pathName 需要生成的目录文件名
	 */
	public ZipCompressor(String pathName) {
		zipFile = new File(pathName);
	}

	/**
	 * 源目录或源文件
	 * @param srcPathName
	 */
	public void compress(String srcPathName) {
		File file = new File(srcPathName);
		if (!file.exists()) {
			throw new RuntimeException("压缩ZIP包失败：" + srcPathName + "不存在！");
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
					new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compress(file, out, basedir);
			
			out.flush();
			out.close();
			cos.flush();
			cos.close();
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			throw new RuntimeException("压缩ZIP包失败：", e);
		}
	}

	private void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		} else {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressFile(file, out, basedir);
		}
	}

	/** 压缩一个目录 */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
