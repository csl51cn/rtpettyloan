package com.global.fems.business.service.impl;

import com.global.fems.business.enums.ReturnMsgCodeEnum;
import com.global.fems.business.service.QueryDeclareService;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.param.domain.ResultModel;
import com.pactera.fems.message.jrb.domain.JRBRET;
import com.pactera.fems.message.jrb.domain.JRBRespHeaderMsg;
import com.pactera.fems.message.jrb.domain.JRBRespMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 查询上报结果管理Service接口实现类
 */
@Service
public class QueryDeclareServiceImpl implements QueryDeclareService {
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;


    @Override
    public ResultModel queryDeclared(String batchNo, String dataType) throws Exception {
        Map map = jrbBizInfoDeclareManager.packageMsgHeader(batchNo, dataType);
        JRBRespMsg respMsg = (JRBRespMsg) map.get("respMsg");
        if (respMsg != null) {
            JRBRespHeaderMsg msg = respMsg.getHeader().getMsg();
            JRBRET ret = msg.getRet();
            String retCode = ret.getRetCode();
            if ("000000".equals(retCode)) {
                return ResultModel.ok(ret.getRetMsg());
            } else {
                return ResultModel.fail("查询失败 " + (ReturnMsgCodeEnum.getValueByCode(retCode) == null ? ret.getRetMsg() : ReturnMsgCodeEnum.getValueByCode(retCode)));
            }
        } else {
            return ResultModel.fail("查询失败:查询报文已发送,未获得响应");
        }
    }
}
