package com.global.fems.message.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 类描述：动态数据集
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class MsgBody extends SerialBean {

	private DataStores dataStores;

	public DataStores getDataStores() {
		return dataStores;
	}

	public void setDataStores(DataStores dataStores) {
		this.dataStores = dataStores;
	}
}
