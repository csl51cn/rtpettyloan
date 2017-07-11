package com.global.fems.business.Strategy;

import com.global.param.domain.ResultModel;

/**
 * 批量发送报文文件策略接口
 */
public interface SendBatchFileStrategy {

    ResultModel sendBatchFile(String ids) throws Exception;

}
