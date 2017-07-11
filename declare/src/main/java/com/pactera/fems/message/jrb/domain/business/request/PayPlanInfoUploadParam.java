package com.pactera.fems.message.jrb.domain.business.request;

import com.global.framework.util.SysUtils;
import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 还款计划信息批量文件上传实体类
 */
public class PayPlanInfoUploadParam extends SerialBean {
      private String reportType; //上报类型
      private String orgCode;//组织机构代码
      private String contractNo;//合同编号
      private String dueBillNo;//发放编号
      private String counter;//还款期数
      private String repayDate;//应还日期
      private String repayPriAmt;//应还本金
      private String repayIntAmt;//应还利息
      private String startDate;//起息日期
      private String endDate;//止息日期

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getDueBillNo() {
        return dueBillNo;
    }

    public void setDueBillNo(String dueBillNo) {
        this.dueBillNo = dueBillNo;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        String date = SysUtils.formatDateStrToString(repayDate, "yyyyMMdd");
        this.repayDate = date;
    }

    public String getRepayPriAmt() {
        return repayPriAmt;
    }

    public void setRepayPriAmt(String repayPriAmt) {
        this.repayPriAmt = repayPriAmt;
    }

    public String getRepayIntAmt() {
        return repayIntAmt;
    }

    public void setRepayIntAmt(String repayIntAmt) {
        this.repayIntAmt = repayIntAmt;
    }

    public String getStartDate() {

        return startDate;
    }

    public void setStartDate(String startDate) {
        String date = SysUtils.formatDateStrToString(startDate, "yyyyMMdd");
        this.startDate = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        String date = SysUtils.formatDateStrToString(endDate, "yyyyMMdd");
        this.endDate = date;
    }
}
