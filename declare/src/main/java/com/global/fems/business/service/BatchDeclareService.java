package com.global.fems.business.service;

import com.global.param.domain.ResultModel;

/**
 * 批量申报管理Service接口
 */
public interface BatchDeclareService {
    ResultModel sendBatchFile(String ids, String transactionType,String userId) throws Exception;
}
