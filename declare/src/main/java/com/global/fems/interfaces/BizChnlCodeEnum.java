package com.global.fems.interfaces;

/**
 * 类描述：
 * 
 * @author chen.feng
 * @date 2015-7-11
 * @version v1.0
 */
public enum BizChnlCodeEnum {

	/** 柜台渠道（接口模式） */
	CODE_12("12", "柜台渠道（接口模式）"),
	/** 网上银行 */
	CODE_21("21", "网上银行"),
	/** 手机银行 */
	CODE_22("22", "手机银行"),
	/** 自助终端 */
	CODE_23("23", "自助终端"),
	/** 电话银行 */
	CODE_24("24", "电话银行"),
	/** 特许兑换机构（接口模式） */
	CODE_42("42", "特许兑换机构（接口模式）");

	private String code;
	private String value;

	private BizChnlCodeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String getValueByCode(String code) {
		for (BizChnlCodeEnum _enum : BizChnlCodeEnum.values()) {
			if (code.equals(_enum.getCode())) {
				return _enum.getValue();
			}
		}
		return null;
	}
	
	public static BizChnlCodeEnum getByCode(String code) {
		for (BizChnlCodeEnum _enum : BizChnlCodeEnum.values()) {
			if (code.equals(_enum.getCode())) {
				return _enum;
			}
		}
		return null;
	}
}
