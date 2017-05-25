package com.global.fems.business.service.impl;

import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.service.ContractInfoService;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 贷款合同信息管理Service接口实现类
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {
    @Autowired
    private ContractInfoDao contractInfoDao;

    public ResultModel save(ContractInfoCycleNode contractInfoCycleNode) {


        return null;
    }
}
