package com.global.param.domain;

import com.global.framework.dbutils.support.Entity;

/**
 * 操作结果
 */
public class ResultModel extends Entity {
    private Boolean sucesss = true;
    private Integer code;
    private String msg;
    private  Object entity;

    public Boolean getSucesss() {
        return sucesss;
    }

    public void setSucesss(Boolean sucesss) {
        this.sucesss = sucesss;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public static  ResultModel ok(){
        return  new ResultModel();
    }

    public  static ResultModel fail(){
        ResultModel model =  new ResultModel();
        model.setSucesss(false);
        return  model;
    }
}
