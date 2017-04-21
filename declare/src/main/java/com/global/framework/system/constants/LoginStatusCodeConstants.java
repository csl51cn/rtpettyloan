package com.global.framework.system.constants;

/**
 * 登录状态码常量
 * @author cqchenf@qq.com
 * @date 2012-1-5 下午10:16:41
 * @version v1.0
 */
public enum LoginStatusCodeConstants {

	/** 登录成功 */
	OK("0","登录成功"),
	
	/** 验证码检验失败 */
	VALIDATE_CODE_ERROR("1","验证码输入错误或失效"),
	
	/** 帐号锁定 */
	ACCOUNT_LOCKED("2","帐号被锁定"),
	
	/** 密码过期 */
	PASSWORD_EXPIRED("3","密码过期"),
	
	/** 首次登录 */
	FIRST_LOGIN("4","首次登录"),
	
	/** 用户名不存在 */
	USERNAME_ERROR("5","用户名不存在"),
	
	/** 密码错误 */
	PASSWORD_ERROR("6","密码错误"),
	
	/** 密码错误次数达到最大限制 */
	MAX_WRONG_TIMES("7","密码输入错误达到最大限制次数，请速联系系统管理员解锁"),
	
	REPAT_LOGIN("8","同一浏览器不允许同时登录多个账户"),
	
	/** 用户名或密码错误*/
	USERNAME_PWD_ERROR("9","用户名或密码错误"),
	
	ALREADY_LOGIN("10","已登录"),
	
	UNKNOW_ERROR("99","未知错误");
	
	private String code;
	private String name;
	
	LoginStatusCodeConstants(String code, String name) {
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
