package com.global.fems.message.service.impl;

import com.global.fems.business.domain.*;
import com.global.fems.business.enums.DataTypeEnum;
import com.global.fems.message.util.OrgCode;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.DateTimeUtil;
import com.pactera.fems.message.jrb.domain.JRBReqBatchFileMsg;
import com.pactera.fems.message.jrb.domain.JRBReqHeader;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.business.request.*;
import com.pactera.fems.message.jrb.service.JRBBizInfoDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报文申报管理类,组装报文信息,调用相应的报文发送方法,发送报文
 */
@Component
public class JRBBizInfoDeclareManager {

    @Autowired
    private SysCommonService sysCommonService;

    @Autowired
    private JRBBizInfoDeclareService jrbBizInfoDeclareService;


    /**
     * 实时网签
     * 组装请求报文头
     *
     * @param contract 从表DC_PETTY_LOAN_CONTRACT中查询的合同信息
     * @return
     * @throws Exception
     */
    public Map realTimeDeclare(PettyLoanContract contract) throws Exception {

        //组装请求报文头
        JRBReqHeaderMsg headerMsg = new JRBReqHeaderMsg();
        //设置服务代码
        headerMsg.setServiceCode("SVR_PTLN");
        //设置交易码
        headerMsg.setTranCode("PTLN001");
        //设置交易模式
        headerMsg.setTranMode("ONLINE");
        //设置组织机构代码
        headerMsg.setBranchId((OrgCode.getOrgCode()));

        //设置交易日期
        headerMsg.setTranDate(DateTimeUtil.getNowDateTime("yyyyMMdd"));
        //设置交易时间
        headerMsg.setTranTimestamp(DateTimeUtil.getNowDateTime("HHmmssSSS"));
        //设置用户语言
        headerMsg.setUserLang("CHINESE");
        //设置渠道流水号
        headerMsg.setSeqNo(sysCommonService.getSeqNo("wfl_taskinfo"));

        //设置模块标识
        headerMsg.setModuleId("CL");
        //设置报文类型
        headerMsg.setMessageType("1200");
        //设置报文代码
        headerMsg.setMessageCode("0001");

        Map map = null;
        if ("530002".equals(contract.getLoanCate())) {
            //委托贷款
            map = jrbBizInfoDeclareService.doRealTimeDeclareEntrustedLoan(contract, headerMsg);
        } else {
            //自营贷款
            map = jrbBizInfoDeclareService.doRealTimeDeclare(contract, headerMsg);
        }

        return map;
    }


    /**
     * 组装批量文件gettx信息,调用jrbBizInfoDeclareService的方法发送打SFTP服务器上
     *
     * @param list
     * @return
     */
    public Map<String, String> sendContractInfoBatchFile(List<ContractInfoCycleNode> list) throws Exception {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setBatchNo(sysCommonService.getSeqNo("wfl_taskinfo"));
        Map<String, String> map = null;
        if ("530002".equals(list.get(0).getLoanCate())) {
            //委托贷款
            map = jrbBizInfoDeclareService.doSendEntrustedContractInfoBatchFile(list, contractInfo);
        } else {//自营贷款
            map = jrbBizInfoDeclareService.doSendContractInfoBatchFile(list, contractInfo);
        }
        map.put("batchNo", contractInfo.getBatchNo());
        map.put("recordCount", contractInfo.getRecordCount());
        map.put("dataType", contractInfo.getDataType());
        return map;
    }


    /**
     * 批量文件保存到SFTP服务器后,组装实时报文得到报文头
     *
     * @param fileName
     * @return
     */
    public Map packageMsgHeader(String fileName) throws Exception {
        //组装请求报文头
        JRBReqHeaderMsg headerMsg = new JRBReqHeaderMsg();
        String orgCode = OrgCode.getOrgCode();
        //通过文件名获得交易码,报文代码
        //分割文件名,结果按顺序为组织机构代码,时间,数据类型,序号
        int orgCodeIndex = fileName.indexOf(orgCode);

        String[] info = fileName.substring(orgCodeIndex + orgCode.length() + 1).split("-");
        String valueByType = DataTypeEnum.getValueByType(info[1]);

        String[] tranCodeAndMessageCode = valueByType.split("-");

        //设置交易码
        headerMsg.setTranCode(tranCodeAndMessageCode[0]);
        //设置报文代码
        headerMsg.setMessageCode(tranCodeAndMessageCode[1]);

        //设置渠道流水号
        StringBuilder seqNo = new StringBuilder(info[0]);
        String num = String.format("%08d", Integer.parseInt(info[2].replaceAll("\\D", "")));
        seqNo.append(num);
        headerMsg.setSeqNo(seqNo.toString());
        setHeaderMsgFieldValue(headerMsg);

        //设置文件路径
        headerMsg.setFilePath("/" + fileName);

        JRBReqBatchFileMsg jrbReqBatchFileMsg = new JRBReqBatchFileMsg();
        jrbReqBatchFileMsg.setHeader(new JRBReqHeader(headerMsg));
        return jrbBizInfoDeclareService.doContractInfoDeclare(jrbReqBatchFileMsg);
    }

    /**
     * 查询上报结果:组装实时报文得到报文头
     *
     * @param fileName 文件名
     * @param dataType 数据类型
     * @return
     */
    public Map packageMsgHeader(String fileName, String dataType) throws Exception {
        JRBReqHeaderMsg headerMsg = new JRBReqHeaderMsg();
        setHeaderMsgFieldValue(headerMsg);
        headerMsg.setServiceCode("SVR_PTLN");
        //设置交易码
        headerMsg.setTranCode("PTLN199");
        //设置报文代码
        headerMsg.setMessageCode("0199");
        //设置流水号
        headerMsg.setSeqNo(sysCommonService.getSeqNo("wfl_taskinfo"));

        QueryDeclared queryDeclared = new QueryDeclared();
        queryDeclared.setFileName(fileName);
        queryDeclared.setDataType(dataType);

        return jrbBizInfoDeclareService.doQueryDeclared(queryDeclared, headerMsg);
    }

    /**
     * 设置批量文件的通用的报文头信息
     *
     * @param headerMsg
     */
    private void setHeaderMsgFieldValue(JRBReqHeaderMsg headerMsg) {
        //设置服务代码
        headerMsg.setServiceCode("SVR_FILE");
        //设置交易模式
        headerMsg.setTranMode("ONLINE");
        //设置组织机构代码
        headerMsg.setBranchId((OrgCode.getOrgCode()));
        //设置交易日期
        headerMsg.setTranDate(DateTimeUtil.getNowDateTime("yyyyMMdd"));
        //设置交易时间
        headerMsg.setTranTimestamp(DateTimeUtil.getNowDateTime("HHmmssSSS"));
        //设置用户语言
        headerMsg.setUserLang("CHINESE");
        //设置模块标识
        headerMsg.setModuleId("CL");
        //设置报文类型
        headerMsg.setMessageType("1220");
    }

    /**
     * 组装批量文件gettx信息,调用jrbBizInfoDeclareService的方法发送打SFTP服务器上.贷款发放信息
     *
     * @param list
     * @return
     */
    public Map<String, String> sendContractIssueInfoBatchFile(ArrayList<ContractIssueInfo> list) throws Exception {
        ContractIssueInfoUpload contractIssueInfoUpload = new ContractIssueInfoUpload();
        contractIssueInfoUpload.setBatchNo(sysCommonService.getSeqNo("wfl_taskinfo"));
        Map<String, String> map = jrbBizInfoDeclareService.doSendContractInfoIssueBatchFile(list, contractIssueInfoUpload);
        map.put("batchNo", contractIssueInfoUpload.getBatchNo());
        map.put("recordCount", contractIssueInfoUpload.getRecordCount());
        map.put("dataType", contractIssueInfoUpload.getDataType());
        return map;
    }

    /**
     * 组装批量文件gettx信息,调用jrbBizInfoDeclareService的方法发送打SFTP服务器上.还款计划信息
     *
     * @param list
     * @return
     */
    public Map<String, String> sendPayPlanInfoBatchFile(ArrayList<PayPlanInfo> list) throws Exception {
        PayPlanInfoUpload payPlanInfoUpload = new PayPlanInfoUpload();
        payPlanInfoUpload.setBatchNo(sysCommonService.getSeqNo("wfl_taskinfo"));
        Map<String, String> map = jrbBizInfoDeclareService.doSendPayPlanInfoBatchFile(list, payPlanInfoUpload);
        map.put("batchNo", payPlanInfoUpload.getBatchNo());
        map.put("recordCount", payPlanInfoUpload.getRecordCount());
        map.put("dataType", payPlanInfoUpload.getDataType());
        return map;
    }


    /**
     * 组装批量文件gettx信息,调用jrbBizInfoDeclareService的方法发送打SFTP服务器上.贷款回收信息
     *
     * @param list
     * @return
     * @throws Exception
     */
    public Map<String, String> sendRepayInfoBatchFile(ArrayList<RepayInfo> list) throws Exception {
        RepayInfoUpload repayInfoUpload = new RepayInfoUpload();
        repayInfoUpload.setBatchNo(sysCommonService.getSeqNo("wfl_taskinfo"));
        Map<String, String> map = jrbBizInfoDeclareService.doSendRepayInfoBatchFile(list, repayInfoUpload);
        map.put("batchNo", repayInfoUpload.getBatchNo());
        map.put("recordCount", repayInfoUpload.getRecordCount());
        map.put("dataType", repayInfoUpload.getDataType());
        return map;
    }


    /**
     * 组装批量文件gettx信息,调用jrbBizInfoDeclareService的方法发送打SFTP服务器上.贷款回收信息
     *
     * @param list
     * @return
     * @throws Exception
     */
    public Map<String, String> sendQuotaInfoBatchFile(ArrayList<QuotaInfo> list) throws Exception {
        QuotaInfoUpload quotaInfo = new QuotaInfoUpload();
        quotaInfo.setBatchNo(sysCommonService.getSeqNo("wfl_taskinfo"));
        Map<String, String> map = jrbBizInfoDeclareService.doSendQuotaInfoBatchFile(list, quotaInfo);
        map.put("batchNo", quotaInfo.getBatchNo());
        map.put("recordCount", quotaInfo.getRecordCount());
        map.put("dataType", quotaInfo.getDataType());
        return map;
    }


    public Map<String, String> sendPettyLoanContractBatchFile(List<PettyLoanContract> list) throws Exception {

        PettyLoanContractInfoUpload pettyLoanContractInfo = new PettyLoanContractInfoUpload();
        pettyLoanContractInfo.setBatchNo(sysCommonService.getSeqNo("wfl_taskinfo"));
        Map<String, String> map = jrbBizInfoDeclareService.doSendPettyLoanContractBatchFile(list, pettyLoanContractInfo);
        map.put("batchNo", pettyLoanContractInfo.getBatchNo());
        map.put("recordCount", pettyLoanContractInfo.getRecordCount());
        map.put("dataType", pettyLoanContractInfo.getDataType());
        return map;
    }
}
