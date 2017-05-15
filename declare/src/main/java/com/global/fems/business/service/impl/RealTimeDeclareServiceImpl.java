package com.global.fems.business.service.impl;

import com.global.fems.business.dao.RealTimeDeclareDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.RealTimeDeclareService;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.framework.util.SysUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 实时网签管理Service
 */
@Service
public class RealTimeDeclareServiceImpl implements RealTimeDeclareService {

    @Autowired
    private RealTimeDeclareDao realTimeDeclareDao;
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;

    public void sendPettyLoanContract(String startDate, String endDate) throws Exception {
        List<PettyLoanContract> list = realTimeDeclareDao.findContractBySignDate(startDate, endDate);
        if (list != null && list.size() > 0) {
            for (PettyLoanContract contract : list) {
                jrbBizInfoDeclareManager.realTimeDeclare(contract);
            }
        }
    }
}
