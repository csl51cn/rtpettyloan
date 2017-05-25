package com.global.fems.business.service;

import com.global.param.domain.ResultModel;

/**
 * 实时网签申报管理Service接口
 */
public interface RealTimeDeclareService {
    ResultModel sendPettyLoanContract(String ids) throws Exception;
}
