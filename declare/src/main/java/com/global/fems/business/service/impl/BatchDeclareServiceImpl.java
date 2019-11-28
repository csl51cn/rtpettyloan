package com.global.fems.business.service.impl;

import com.global.fems.business.dao.DeclareResultDao;
import com.global.fems.business.domain.DeclareResult;
import com.global.fems.business.enums.DataTypeEnum;
import com.global.fems.business.enums.ReturnMsgCodeEnum;
import com.global.fems.business.service.BatchDeclareService;
import com.global.fems.business.service.QueryDeclareService;
import com.global.fems.business.strategy.SendBatchFileStrategy;
import com.global.fems.business.strategy.SendBatchFileStrategyFactory;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.param.domain.ResultModel;
import com.pactera.fems.message.jrb.domain.JRBRET;
import com.pactera.fems.message.jrb.domain.JRBRespBatchFileMsg;
import com.pactera.fems.message.jrb.domain.JRBRespHeaderMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 批量申报管理Service接口实现类
 */
@Service
public class BatchDeclareServiceImpl implements BatchDeclareService {

    private static final Logger logger = LoggerFactory.getLogger(BatchDeclareServiceImpl.class);
    @Autowired
    private SendBatchFileStrategyFactory sendBatchFileStrategyFactory;

    @Autowired
    private DeclareResultDao declareResultDao;

    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;

    @Autowired
    private QueryDeclareService queryDeclareService;
    /**
     * 根据传入的交易类型获得相应的上传报文的策略
     *
     * @param ids             记录的id
     * @param transactionType
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel sendBatchFile(String ids, String transactionType, String userId) throws Exception {
        Map<String, SendBatchFileStrategy> sendBatchFileStrategyMap = sendBatchFileStrategyFactory.getSendBatchFileStrategyMap();
        SendBatchFileStrategy sendBatchFileStrategy = sendBatchFileStrategyMap.get(transactionType);
        ResultModel resultModel = sendBatchFileStrategy.sendBatchFile(ids, userId);
        return resultModel;
    }

    /**
     * 批量申报重新发送通知报文
     *
     * @param id     上报结果记录id
     * @param userId 操作人id
     * @return
     */
    @Override
    public ResultModel reReportFilePath(String id, String userId) {
        DeclareResult declareResult = declareResultDao.findDeclareResultById(id);
        //发送实时报文,指明文件位置
        Map contractInfoDeclareResult = null;
        try {
            contractInfoDeclareResult = jrbBizInfoDeclareManager.packageMsgHeader(declareResult.getRemoteFilePath());
            JRBRespBatchFileMsg respMsg = (JRBRespBatchFileMsg) contractInfoDeclareResult.get("respMsg");
            if (respMsg != null) {
                JRBRespHeaderMsg msg = respMsg.getHeader().getMsg();
                JRBRET ret = msg.getRet();
                String retCode = ret.getRetCode();
                if ("000000".equals(retCode)) {
                    //申报成功,更新数据库
                    String transactionType = DataTypeEnum.getSimpleValueByType(declareResult.getDataType());
                    SendBatchFileStrategy sendBatchFileStrategy = sendBatchFileStrategyFactory.getSendBatchFileStrategyMap().get(transactionType);
                    sendBatchFileStrategy.updateStatus(declareResult.getBatchNo(), 1);
                    declareResult.setLastReReportDate(new Date());
                    declareResultDao.saveOrUpdate(declareResult);
                    queryDeclareService.queryDeclared(id,userId);
                    return ResultModel.ok("重新发送通知报文成功");
                } else {
                    return ResultModel.fail("重新发送通知报文失败:" + ReturnMsgCodeEnum.getValueByCode(retCode));
                }
            } else {
                return ResultModel.fail("重新发送通知报文异常,未获得响应");
            }
        } catch (Exception e) {
            logger.error("重新发送通知报文异常", e);
            return ResultModel.fail("重新发送通知报文异常");
        }

    }

}
