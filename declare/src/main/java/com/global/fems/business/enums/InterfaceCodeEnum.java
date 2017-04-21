package com.global.fems.business.enums;

/**
 * 个人结售汇监测接口代码
 * 
 * @author Sili Jiang
 * @version 2015-07-07
 *
 */
public enum InterfaceCodeEnum {
	
	/**
	 * 个人结售汇额度查询
	 */
	QUOTA_QUERY("000001","个人结售汇额度查询"),
	/**
	 * 个人结售汇额度登记指令查询
	 */
	REG_QUERY("000002","个人结售汇额度登记指令查询"),
	/**
	 * 美元折算率查询
	 */
	USD_CSV_QUERY("000003","美元折算率查询"),
	/**
	 * 个人结售汇额度登记
	 */
	QUOTA_REG("000004","个人结售汇额度登记"),
	/**
	 * 个人结售汇信息撤消
	 */
	MSG_CANCEL("000005","个人结售汇信息撤消"),
	/**
	 * 个人结售汇信息修改
	 */
	MSG_CHANGE("000006","个人结售汇信息修改"),
	/**
	 * 000007
	 */
	COMMAND_SYN("000007","结售汇额度登记指令同步核对接口");
	
	private String code;
	private String name;

	private InterfaceCodeEnum(String code, String name) {
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
	
}
