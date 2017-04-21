package com.global.fems.business.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 交易类型枚举
 * 
 * @author Sili Jiang
 * @version 2015-07-10
 * 
 */
public enum TradeTypeEnum {
	JH("JH", "结汇"), 
	GH("GH", "购汇");

	private String code;
	private String name;

	private TradeTypeEnum(String code, String name) {
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

	public static String getCodeByName(String name) {
		for (TradeTypeEnum e : TradeTypeEnum.values()) {
			if (StringUtils.equals(e.getName(), name)) {
				return e.getCode();
			}
		}
		return null;
	}

	public static String getNameByCode(String code) {
		for (TradeTypeEnum e : TradeTypeEnum.values()) {
			if (StringUtils.equals(e.getCode(), code)) {
				return e.getName();
			}
		}
		return null;
	}

}
