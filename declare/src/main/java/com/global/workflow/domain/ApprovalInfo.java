package com.global.workflow.domain;

/**
 * 审批信息
 * 
 * @author chen.feng
 * @date 2013-6-20
 * @version v1.0
 */
public class ApprovalInfo {

	private String isPass;// 是否通过
	private String approvalOpinion;// 审批意见

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
}
