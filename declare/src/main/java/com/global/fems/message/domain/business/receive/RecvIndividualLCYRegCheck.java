package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 类描述：结售汇额度登记指令同步核对接口 接口代码：000007
 *
 * @ClassName: RecvIndividualLCYRegCheck
 * @Description:
 * @author leonardLeo
 * @date 2015-6-24 下午09:29:57
 *
 */
public class RecvIndividualLCYRegCheck extends DataStores {

	/** 1.同步日期 */
	private String SYNC_DATE;

	public String getSYNC_DATE() {
		return SYNC_DATE;
	}

	public void setSYNC_DATE(String sYNCDATE) {
		SYNC_DATE = sYNCDATE;
	}

}
