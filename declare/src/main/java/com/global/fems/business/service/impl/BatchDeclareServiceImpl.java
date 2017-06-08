package com.global.fems.business.service.impl;

import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.service.BatchDeclareService;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * 批量申报管理Service接口实现类
 */
@Service
public class BatchDeclareServiceImpl implements BatchDeclareService {
    @Autowired
    private ContractInfoDao contractInfoDao;
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;


    public ResultModel sendBatchFile(String ids) throws Exception {

        String[] idsArr = ids.split(",");
        ArrayList<ContractInfoCycleNode> list = new ArrayList<ContractInfoCycleNode>();
        for (String id : idsArr) {
            ContractInfoCycleNode contractById = contractInfoDao.findContractById(id);
            list.add(contractById);
        }
        Map map = jrbBizInfoDeclareManager.sendContractInfoBatchFile(list);

        return null;
    }
}
