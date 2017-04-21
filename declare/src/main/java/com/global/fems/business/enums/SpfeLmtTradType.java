package com.global.fems.business.enums;


public enum SpfeLmtTradType{
	
	TRADTYPE_OCCUPYJH("JH","Y","000040"),
	TRADTYPE_UNOCCUPYJH("JH","N","000041"),
	TRADTYPE_OCCUPYGH("GH","Y","000042"),
	TRADTYPE_UNOCCUPYGH("GH","N","000043");
	
	private String type;
	private String isOccup;
	private String code;
	
	private SpfeLmtTradType(String type,String isOccup,String code) {
		this.isOccup = isOccup;
		this.type = type;
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIsOccup() {
		return isOccup;
	}
	public void setIsOccup(String isOccup) {
		this.isOccup = isOccup;
	}
	public static SpfeLmtTradType get(String isOccup,String type){
		for(SpfeLmtTradType sltt:SpfeLmtTradType.values()){
			if(isOccup.equals(sltt.getIsOccup()) && type.equals(sltt.getType())){
				return  sltt;
			}
		}
		return null;
	}
}