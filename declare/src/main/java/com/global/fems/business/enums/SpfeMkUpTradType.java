package com.global.fems.business.enums;


public enum SpfeMkUpTradType{
	
	TRADTYPE_OCCUPYJH_BL("JH","Y","000080"),
	TRADTYPE_UNOCCUPYJH_BL("JH","N","000081"),
	TRADTYPE_OCCUPYGH_BL("GH","Y","000082"),
	TRADTYPE_UNOCCUPYGH_BL("GH","N","000083");
	
	private String type;
	private String isOccup;
	private String code;
	
	private SpfeMkUpTradType(String type,String isOccup,String code) {
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
	public static SpfeMkUpTradType get(String isOccup,String type){
		for(SpfeMkUpTradType sltt:SpfeMkUpTradType.values()){
			if(isOccup.equals(sltt.getIsOccup()) && type.equals(sltt.getType())){
				return  sltt;
			}
		}
		return null;
	}
}