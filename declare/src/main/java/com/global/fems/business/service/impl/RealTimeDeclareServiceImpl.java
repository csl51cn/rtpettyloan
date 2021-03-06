package com.global.fems.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.enums.ReturnMsgCodeEnum;
import com.global.fems.business.service.RealTimeDeclareService;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.framework.util.SysUtils;
import com.global.param.domain.ResultModel;
import com.pactera.fems.message.jrb.domain.JRBRET;
import com.pactera.fems.message.jrb.domain.JRBRespHeaderMsg;
import com.pactera.fems.message.jrb.domain.JRBRespMsg;
import com.pactera.fems.message.jrb.domain.business.response.RealTimeContractRtrTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 实时网签申报管理Service
 */
@Service
public class RealTimeDeclareServiceImpl implements RealTimeDeclareService {


    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;

    @Override
    public ResultModel sendPettyLoanContract(String ids) throws Exception {
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            PettyLoanContract contract = pettyLoanContractDao.findPettyLoanContractById(id);
            Map result = jrbBizInfoDeclareManager.realTimeDeclare(contract);
            StringBuilder validateError = new StringBuilder("当前记录申报失败,此条记录之前的记录已申报(如果有).合同信息--合同编号:");
            validateError.append(contract.getContractNo());
            validateError.append(",借款人:");
            validateError.append(contract.getCustomerName());
            validateError.append(" ");
            if (result.get("validateError") != null) {
                //数据校验失败
                //给出合同信息
                validateError.append((String) result.get("validateError"));
                return ResultModel.fail(validateError.toString());

            }
            JRBRespMsg respMsg = (JRBRespMsg) result.get("respMsg");
            if (respMsg != null) {
                JRBRespHeaderMsg msg = respMsg.getHeader().getMsg();
                JRBRET ret = msg.getRet();
                String retCode = ret.getRetCode();
                if ("000000".equals(retCode)) {
                    //申报成功,更新数据库信息
                    //申报状态修改为已申报
                    contract.setSendStatus(1);
                    //设置申报时间
                    String tranDate = msg.getTranDate();
                    String tranTimestamp = msg.getTranTimestamp();
                    Timestamp strToTimestamp = SysUtils.getStrToTimestamp(tranDate + " " + tranTimestamp, "yyyyMMdd HHmmssSSS");
                    contract.setSendDate(strToTimestamp);
                    //设置渠道流水号
                    contract.setSeqNo(msg.getSeqNo());
                    //设置网签编号
                    RealTimeContractRtrTx realTimeContractRtrTx = (RealTimeContractRtrTx) respMsg.getBody().getRtrtx();
                    contract.setNetSignNo(realTimeContractRtrTx.getNetSignId());
                    pettyLoanContractDao.saveOrUpdate(contract);

                } else {
                    return ResultModel.fail(validateError.append(ReturnMsgCodeEnum.getValueByCode(retCode)).toString());
                }
            }else{
                JSONObject respMsgJsonObject = (JSONObject) result.get("respMsgJsonObject");
                JSONObject getTx = respMsgJsonObject.getJSONObject("transaction").getJSONArray("body").getJSONObject(0).getJSONArray("GetTx").getJSONObject(0);
                String systemErrorCode = getTx.getJSONArray("SYS_ERRCODE").getString(0);
                return ResultModel.fail(validateError.append(ReturnMsgCodeEnum.getValueByCode(systemErrorCode)).toString());
            }
        }

        return ResultModel.ok();
    }
}
