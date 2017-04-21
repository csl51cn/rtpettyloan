package com.global.fems.interfaces;


/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
public enum FaceCodeEnum {

	CODE_000001("000001","个人结售汇额度查询"),
	CODE_000002("000002","个人结售汇额度登记指令查询"),
	CODE_000003("000003","美元折算率查询 "),
	CODE_000004("000004","个人结售汇额度登记"),
	CODE_000005("000005","个人结售汇信息撤消"),
	CODE_000006("000006","个人结售汇信息修改"),
	CODE_000007("000007","结售汇额度登记指令同步核对"),
	CODE_000008("000008","个人结售汇信息补录"),
	CODE_000009("000009","个人结售汇明细信息查询"),
	CODE_000010("000010","预关注风险提示/关注名单告知"),
	CODE_000011("000011","外管登录用户信息更新");
	
	private String code;
	private String value;

	private FaceCodeEnum(String code, String value) {
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
		for (FaceCodeEnum _enum : FaceCodeEnum.values()) {
			if (code.equals(_enum.getCode())) {
				return _enum.getValue();
			}
		}
		return null;
	}
}
