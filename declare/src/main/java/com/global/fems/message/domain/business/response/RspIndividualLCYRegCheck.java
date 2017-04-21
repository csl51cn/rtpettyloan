package com.global.fems.message.domain.business.response;

import com.global.fems.message.domain.DataStores;
/**
 * 类描述：结售汇额度登记指令同步核对接口反馈 接口代码：000007
* @ClassName: RspIndividualLCYRegCheck 
* @Description: TODO
* @author leonardLeo 
* @date 2015-6-25 上午11:43:20 
*
 */
public class RspIndividualLCYRegCheck extends DataStores {
	
	/** 1.文件名*/
	private String SYNC_FILE_NAME;

	public String getSYNC_FILE_NAME() {
		return SYNC_FILE_NAME;
	}

	public void setSYNC_FILE_NAME(String sYNCFILENAME) {
		SYNC_FILE_NAME = sYNCFILENAME;
	}
	
}
