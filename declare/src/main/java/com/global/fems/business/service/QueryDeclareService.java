package com.global.fems.business.service;

import com.global.param.domain.ResultModel;

/**
 * 查询上报结果管理Service接口
 */
public interface QueryDeclareService {

    ResultModel queryDeclared(String batchNo, String dataType) throws Exception;

}
