package com.global.fems.batch.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.global.fems.batch.service.BatchService;
import com.global.workflow.dao.TaskInfoDao;

/**
 * 类描述： 批处理服务类
 *
 * @author chen.feng
 * @date 2015-7-8
 * @version v1.0
 */
public class BatchServiceImpl implements BatchService {

	/**
	 * 产生文件目录及文件名
	 */
	String filepath = "D://test//";
	/**
	 * 产生备份文件目录
	 */
	String localPathBak = "D://test//bak";
	
	@Autowired
	private TaskInfoDao taskInfoDao;
	
	public void generateLCYRegSyncFile() {
		//查询数据
//		List<TaskInfo> taskList = taskInfoDao.queryTaskListByDate(date);
//		
//		/** 产生文件及备份 */
//		try {
//			generateFile(taskList, filepath, localPathBak);
//		} catch (Exception e) {
//			throw new DataCheckException(null,e.getMessage());
//		}
	}
	/**
	 * 生成文件
	 * 
	 * @param data
	 * @param filepath
	 * @param localPathBak
	 * @throws Exception 
	 */
	private void generateFile(List data, String filepath,
			String localPathBak) throws Exception {
		FileWriter fw = null;
		BufferedWriter bw = null;
		File newFile = null;
		try {
			newFile = new File(filepath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			fw = new FileWriter(newFile);
			bw = new BufferedWriter(fw);

			for (int i = 0; i < data.size(); i++) {
				if (i != (data.size() - 1)) {
					bw.write((String) (data.get(i)));
					bw.newLine();
					bw.flush();
				} else {
					bw.write((String) (data.get(i)));
					bw.newLine();
					bw.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.flush();
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		backupFile(localPathBak, newFile);
	}

	/**
	 * 备份文件
	 * 
	 * @param backupPath
	 * @param file
	 * @throws Exception 
	 */
	public void backupFile(String backupPath, File file) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateStr = formatter.format(new Date(System.currentTimeMillis()));
		File dir = new File(backupPath + File.separator + dateStr);
		if (!dir.exists()) {
			dir.mkdir();
		}
		FileWriter fw = null;
		FileReader fr = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			File newFile = new File(backupPath + File.separator + dateStr
					+ File.separator + file.getName());
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			fr = new FileReader(file);
			fw = new FileWriter(newFile);
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);

			String tmpbuf = null;
			while ((tmpbuf = br.readLine()) != null) {
				bw.write(tmpbuf);
				bw.newLine();
				bw.flush();
			}
			System.out.println("备份文件[" + file.getName() + "]成功!");
		} catch (Exception e) {
			System.out.println("备份文件[" + file.getName() + "]至路径 [" + backupPath
					+ "]失败!" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				br.close();
			} catch (Exception e2) {
			}
		}

	}
}
