package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.RealTimeDeclareService;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 实时网签管理Service
 */
@Service
public class RealTimeDeclareServiceImpl implements RealTimeDeclareService {



    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;

    public ResultModel sendPettyLoanContract(String ids) throws Exception {
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            PettyLoanContract contract = pettyLoanContractDao.findPettyLoanContractById(id);
            Map map = jrbBizInfoDeclareManager.realTimeDeclare(contract);
            if(map != null && map.get("validateError") != null){//数据校验失败
                //给出合同信息
                StringBuilder validateError = new StringBuilder("当前记录申报失败,此条记录之前的记录已申报(如果有).合同信息--合同编号:");
                validateError.append(contract.getContractNo());
                validateError.append(",借款人:");
                validateError.append(contract.getCustomerName());
                validateError.append(" ");
                validateError.append( (String) map.get("validateError"));
                return ResultModel.fail(validateError.toString());
            }
        }

        return ResultModel.ok();
    }
}
