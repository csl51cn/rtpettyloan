package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.RealTimeDeclareService;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实时网签管理Service
 */
@Service
public class RealTimeDeclareServiceImpl implements RealTimeDeclareService {



    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;

    public void sendPettyLoanContract(String ids) throws Exception {
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            Integer dateId = new Integer(id);
            PettyLoanContract contract = pettyLoanContractDao.findContractByDateId(dateId);
            jrbBizInfoDeclareManager.realTimeDeclare(contract);
        }
    }
}
