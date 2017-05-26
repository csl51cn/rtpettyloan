package com.global.fems.business.service.impl;

import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.service.ContractInfoService;
import com.global.framework.dbutils.support.DAOException;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 贷款合同信息管理Service接口实现类
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {
    @Autowired
    private ContractInfoDao contractInfoDao;

    /**
     * 保存贷款合同信息
     * @param contractInfoCycleNode
     * @return
     */
    public ResultModel save(ContractInfoCycleNode contractInfoCycleNode) {

        try {
            //设置上报类型,初始保存时,默认为新增:100001
            contractInfoCycleNode.setReportType("100001");
            //设置组织机构代码
            contractInfoCycleNode.setOrgCode("91500000584252884K");
            //设置是否发送,0:否,1:是
            contractInfoCycleNode.setIsSend("0");
            //设置记录保存时间
            contractInfoCycleNode.setInsertDate(new Date());
            //设置是否是最新记录
            contractInfoCycleNode.setIsLast("Y");
            contractInfoDao.save(contractInfoCycleNode);
            return ResultModel.ok();
        } catch (DAOException e) {

            return ResultModel.fail();
        }
    }
}
