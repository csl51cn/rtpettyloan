package com.global.fems.business.dto;

/**
 * @author: Senlin.Deng
 * @Description: 实际还款信息
 * @date: Created in 2021/2/3 11:50
 * @Modified By:
 */
public class RepaymentInfo {

    /**
     * 入账日期
     */
    private String repaymentDate;

    /**
     *
     */
    private Integer sequenceNo;


    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
}
