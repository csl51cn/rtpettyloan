package com.global.fems.message.util;

/**
 * 
 * @author chen.feng
 * @date 2015-7-1
 * @version v1.0
 */
public class MessageUtil {

	public static int getBytesLen(byte[] inlen) {
		int len1 = inlen[0] * 256;
		int len2 = ((int) inlen[1]);
		return len1 + len2;
	}
}
