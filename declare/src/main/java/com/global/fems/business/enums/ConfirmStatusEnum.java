package com.global.fems.business.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 预关注风险提示/关注名单告知状态
 * 
 * @author Sili Jiang
 * @version 2015-07-17
 * 
 */
public enum ConfirmStatusEnum {

	/**
	 * 未告知
	 */
	NOT_SIGN("0", "未告知"),
	/**
	 * 已告知
	 */
	SIGN("1", "已告知");

	private String code;

	private String name;

	private ConfirmStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getNameByCode(String code) {
		for (ConfirmStatusEnum e : ConfirmStatusEnum.values()) {
			if (StringUtils.equals(e.getCode(), code)) {
				return e.getName();
			}
		}
		return null;
	}

}
