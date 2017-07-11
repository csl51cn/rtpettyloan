package com.global.fems.business.Strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送批量报文文件工厂类
 */
public class SendBatchFileStrategyFactory {
    private Map<String,SendBatchFileStrategy> sendBatchFileStrategyMap  = new HashMap<String,SendBatchFileStrategy>();

    public Map<String, SendBatchFileStrategy> getSendBatchFileStrategyMap() {
        return sendBatchFileStrategyMap;
    }

    public void setSendBatchFileStrategyMap(Map<String, SendBatchFileStrategy> sendBatchFileStrategyMap) {
        this.sendBatchFileStrategyMap = sendBatchFileStrategyMap;
    }

}
