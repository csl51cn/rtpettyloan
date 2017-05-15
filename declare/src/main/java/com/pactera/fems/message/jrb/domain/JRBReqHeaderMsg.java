package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;


public class JRBReqHeaderMsg extends SerialBean {
    private String SERVICE_CODE; //服务代码:实时：SVR_PTLN ,文件：SVR_FILE
    private String TRAN_CODE;//交易码
    private String TRAN_TYPE;//交易类型 可选 保留域,默认为空
    private String TRAN_MODE;//交易模式,实时:ONLINE ,文件:ASYNC
    private String BRANCH_ID;//组织机构代码
    private String TRAN_DATE;//交易日期:YYYYMMDD
    private String TRAN_TIMESTAMP;//交易时间:HHMMSSNNN
    private String SERVER_ID;//服务器标识 可选 保留域，默认为空
    private String WS_ID;//终端标识 可选  小贷公司上报系统服务器地址
    private String USER_LANG;//用户语言,中文：CHINESE,英文：AMERICAN/ENGLISH
    private String SEQ_NO;//渠道流水号 小贷公司保证自身流水号不重复
    private String SOURCE_BRANCH_NO;//	源节点编号 可选 保留域，默认为空
    private String DEST_BRANCH_NO;//目标节点编号 可选 保留域，默认为空
    private String MODULE_ID;//模块标识	 CL
    private String MESSAGE_TYPE;//报文类型
    private String MESSAGE_CODE;//报文代码
    private String FILE_PATH;//	文件路径	文件报文使用 文件名规则：组织机构代码-YYYYMMDD-数据类型-XX.xml

    public String getSERVICE_CODE() {
        return SERVICE_CODE;
    }

    public void setSERVICE_CODE(String SERVICE_CODE) {
        this.SERVICE_CODE = SERVICE_CODE;
    }

    public String getTRAN_CODE() {
        return TRAN_CODE;
    }

    public void setTRAN_CODE(String TRAN_CODE) {
        this.TRAN_CODE = TRAN_CODE;
    }

    public String getTRAN_TYPE() {
        return TRAN_TYPE;
    }

    public void setTRAN_TYPE(String TRAN_TYPE) {
        this.TRAN_TYPE = TRAN_TYPE;
    }

    public String getTRAN_MODE() {
        return TRAN_MODE;
    }

    public void setTRAN_MODE(String TRAN_MODE) {
        this.TRAN_MODE = TRAN_MODE;
    }

    public String getBRANCH_ID() {
        return BRANCH_ID;
    }

    public void setBRANCH_ID(String BRANCH_ID) {
        this.BRANCH_ID = BRANCH_ID;
    }

    public String getTRAN_DATE() {
        return TRAN_DATE;
    }

    public void setTRAN_DATE(String TRAN_DATE) {
        this.TRAN_DATE = TRAN_DATE;
    }

    public String getTRAN_TIMESTAMP() {
        return TRAN_TIMESTAMP;
    }

    public void setTRAN_TIMESTAMP(String TRAN_TIMESTAMP) {
        this.TRAN_TIMESTAMP = TRAN_TIMESTAMP;
    }

    public String getSERVER_ID() {
        return SERVER_ID;
    }

    public void setSERVER_ID(String SERVER_ID) {
        this.SERVER_ID = SERVER_ID;
    }

    public String getWS_ID() {
        return WS_ID;
    }

    public void setWS_ID(String WS_ID) {
        this.WS_ID = WS_ID;
    }

    public String getUSER_LANG() {
        return USER_LANG;
    }

    public void setUSER_LANG(String USER_LANG) {
        this.USER_LANG = USER_LANG;
    }

    public String getSEQ_NO() {
        return SEQ_NO;
    }

    public void setSEQ_NO(String SEQ_NO) {
        this.SEQ_NO = SEQ_NO;
    }

    public String getSOURCE_BRANCH_NO() {
        return SOURCE_BRANCH_NO;
    }

    public void setSOURCE_BRANCH_NO(String SOURCE_BRANCH_NO) {
        this.SOURCE_BRANCH_NO = SOURCE_BRANCH_NO;
    }

    public String getDEST_BRANCH_NO() {
        return DEST_BRANCH_NO;
    }

    public void setDEST_BRANCH_NO(String DEST_BRANCH_NO) {
        this.DEST_BRANCH_NO = DEST_BRANCH_NO;
    }

    public String getMODULE_ID() {
        return MODULE_ID;
    }

    public void setMODULE_ID(String MODULE_ID) {
        this.MODULE_ID = MODULE_ID;
    }

    public String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    public void setMESSAGE_TYPE(String MESSAGE_TYPE) {
        this.MESSAGE_TYPE = MESSAGE_TYPE;
    }

    public String getMESSAGE_CODE() {
        return MESSAGE_CODE;
    }

    public void setMESSAGE_CODE(String MESSAGE_CODE) {
        this.MESSAGE_CODE = MESSAGE_CODE;
    }

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }
}
