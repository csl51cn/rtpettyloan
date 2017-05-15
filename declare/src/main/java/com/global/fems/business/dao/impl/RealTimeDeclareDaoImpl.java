package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.RealTimeDeclareDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.Entity;
import com.global.framework.util.DateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 实时网签管理Dao
 */
@Repository
public class RealTimeDeclareDaoImpl extends BaseDaoSupport implements RealTimeDeclareDao  {

    /**
     * 根据签约时间查询未申报合同记录
     * @param startDate
     * @param endDate
     * @return
     */
    public List<PettyLoanContract> findContractBySignDate(String startDate, String endDate) {
        StringBuilder sql =  new StringBuilder("select * from DC_PETTY_LOAN_CONTRACT where 1 = 1  AND sendStatus = 0");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND contractsigndate >= ? AND contractsigndate <= ?");
            list.add(startDate);
            list.add(endDate);
        }
        List<PettyLoanContract> contractList = (List<PettyLoanContract>) super.findForListBySql(sql.toString(), list.toArray(), PettyLoanContract.class);
        return contractList;
    }
}
