package com.global.framework.system.constants;

/**
 * 公共常量类
 * 
 * @author cqchenf@qq.com
 * 
 */
public class Constants {
	
	/** 机构级别标识 */
	public static final String ORG_LEVEL = "ORG_LEVEL";
	
	public static final String CHANNEL = "CHANNEL";
	
	public enum Status {
		activate("Y", "激活"), stop("N", "停用");

		Status(String code, String value) {
			this.code = code;
			this.value = value;
		}

		private String code;
		private String value;

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

	}
	
	/**是否预置*/
	public enum IsFix {
		YES("Y", "是"),NO("N", "否");
		
		IsFix(String code, String value) {
			this.code = code;
			this.value = value;
		}

		private String code;
		private String value;

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
	}
	
	/** 性别 */
	public enum Sex {
		MALE("1","男"),
		FEMALE("2","女");
		
		Sex(String code, String name) {
			this.code = code;
			this.name = name;
		}
		
		private String code;
		private String name;
		
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
	
	public enum LogType{
		NEW ("1","新增"),
		EDIT ("2","编辑"),
		DELETE ("3","删除"),
		LOGIN ("4","登录"),
		LOGOUT ("5","退出");
		
		
		LogType(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		private String code; 
		private String value;
		
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
	}
}
