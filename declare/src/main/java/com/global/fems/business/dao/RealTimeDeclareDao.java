package com.global.fems.business.dao;

import com.global.fems.business.domain.PettyLoanContract;

import java.util.List;

/**
 * 实时网签管理Dao接口
 */
public interface RealTimeDeclareDao {
    List<PettyLoanContract> findContractBySignDate(String startDate, String endDate);
}
