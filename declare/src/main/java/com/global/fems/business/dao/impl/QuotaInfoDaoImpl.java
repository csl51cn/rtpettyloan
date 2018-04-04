package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.QuotaInfoDao;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import org.springframework.stereotype.Repository;

/**
 *
 * 授信额度信息管理Dao接口实现类
 * @author senlin.deng
 * @date 2018/4/3 16:36
 */
@Repository
public class QuotaInfoDaoImpl extends BaseDaoSupport implements QuotaInfoDao {



    @Override
    public PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) {
        StringBuilder sql = new StringBuilder("Select");
        return null;
    }
}
