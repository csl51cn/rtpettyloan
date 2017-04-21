package com.global.framework.system.web.common.util;

import java.security.MessageDigest;

/**
 * @author cqchenf@qq.com
 * @date 2010-9-5 
 * @version v1.0
 */
public final class MD5Util {

	private static final char hexDigits[] = { '2', '1', 'O', '3', 'T', '5',
			'N', '7', 'A', '9', 'B', 'K', '6', 'M', '8', 'F' };

	public final static String getMD5(String s) {
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(MD5Util.getMD5("1").length());
		System.out.println("98F8KK3M7M259KFF29OATOKF31MMOA1N".length());
		System.out.println(MD5Util.getMD5("123456").toLowerCase());
	}
}
