package com.global.fems.business.controller;

import com.global.framework.dbutils.support.PageBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author senlin.deng
 * @date 2018/4/4 14:31
 */
public class BaseController {

    protected Map<String, Object> pageBean2Map(PageBean pageBean) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageBean.getTotalRows());
        map.put("rows", pageBean.getDataList());
        return map;
    }
}
