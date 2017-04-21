package com.global.framework.system.constants;

/**
 * 登录日志状态
 * @author cqchenf@qq.com
 * @date 2013-1-27 下午6:59:07
 * @version v1.0
 */
public enum LogoutLogStatusConstants {

	/** 非正常退出 */
	EXCEPTION("0","异常"),
	
	/** 正常退出 */
	NORMAL("1","正常");
	
	private String code;
	private String name;
	
	LogoutLogStatusConstants(String code, String name) {
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
