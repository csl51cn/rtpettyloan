package com.global.workflow.domain;

import org.apache.commons.lang.StringUtils;

/**
 * 交易状态
 * 
 * @author chen.feng
 * @date 2015-6-20
 * @version v1.0
 */
public enum TransStateEnum {

	SAVE("0", "新建"), 
	HANDLE("1", "经办"), 
	CHECK("2", "复核"), 
	AUTH("3", "授权"),
	COMPLETE("4", "结束"), 
	AGAIN("5", "经办更正"), 
	CANCEL("6", "删除"), 
	FALIED("7", "失败");

	private String code;
	private String value;

	TransStateEnum(String code, String value) {
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
	
	/**
	 * 获取流程状态名称
	 * 
	 * @param code 流程状态代码
	 * @return
	 */
	public static String getValueByCode(String code){
		for(TransStateEnum state : TransStateEnum.values()){
			if(StringUtils.equals(code, state.getCode())){
				return state.getValue();
			}
		} 
		return TransStateEnum.SAVE.getValue();
	}
	
	public static TransStateEnum getByCode(String code){
		for(TransStateEnum state : TransStateEnum.values()){
			if(StringUtils.equals(code, state.getCode())){
				return state;
			}
		} 
		return null;
	}
}
