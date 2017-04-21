package com.global.fems.interfaces.server;

public class Utils {

	/**
	 * 合并二维byte数组为一维byte数组
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] mergence(byte[][] src) {
		int tLen = 0;
		for (int i = 0; i < src.length; i++) {
			tLen = tLen + src[i].length;
		}
		byte[] tmp = new byte[tLen];

		int iTmp = 0;
		for (int i = 0; i < src.length; i++) {
			if (i > 0)
				iTmp = iTmp + src[i - 1].length;
			System.arraycopy(src[i], 0, tmp, iTmp, src[i].length);
		}
		return tmp;
	}
}
