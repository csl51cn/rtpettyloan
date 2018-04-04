package com.global.fems.business.service.impl;

import com.global.fems.business.dao.QuotaInfoDao;
import com.global.fems.business.service.QuotaInfoService;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author senlin.deng
 * @date 2018/4/3 16:34
 * 额度信息管理Service接口实现类
 */
@Service
public class QuotaInfoServiceImpl implements QuotaInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuotaInfoServiceImpl.class);
    @Autowired
    private QuotaInfoDao quotaInfoDao;

    @Override
    public PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) throws DAOException {
        pageBean = quotaInfoDao.findQuotaInfoByContractNoFromBizSys(contractNo,pageBean);
        return pageBean;
    }
}
