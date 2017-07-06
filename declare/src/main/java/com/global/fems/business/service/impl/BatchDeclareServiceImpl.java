package com.global.fems.business.service.impl;

import com.global.fems.business.Strategy.SendBatchFileStrategy;
import com.global.fems.business.Strategy.SendBatchFileStrategyFactory;
import com.global.fems.business.service.BatchDeclareService;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 批量申报管理Service接口实现类
 */
@Service
public class BatchDeclareServiceImpl implements BatchDeclareService {

    @Autowired
    private SendBatchFileStrategyFactory sendBatchFileStrategyFactory;

    /**
     * 根据传入的交易类型获得相应的上传报文的策略
     *
     * @param ids 记录的id
     * @param transactionType
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel sendBatchFile(String ids, String transactionType) throws Exception {
        Map<String, SendBatchFileStrategy> sendBatchFileStrategyMap = sendBatchFileStrategyFactory.getSendBatchFileStrategyMap();
        SendBatchFileStrategy sendBatchFileStrategy = sendBatchFileStrategyMap.get(transactionType);
        ResultModel resultModel = sendBatchFileStrategy.sendBatchFile(ids);
        return resultModel;
    }
}
