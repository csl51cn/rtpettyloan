package com.pactera.fems.message.jrb.support;


import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


class NullViladator {
    private Map all;
    private Map m;

    protected NullViladator() {
        this.m = new HashMap();
        this.all = new HashMap();
    }

    protected void addToAllMap(Map m, Object key, String alias) {
        Object obj = m.get(key);
        this.all.put(alias + ":" + key, obj);
    }

    protected void clear() {
        this.m.clear();
    }


    protected Object PopMap(Map m, Object key, String alias) {
        Object obj = m.get(key);
        this.m.put(alias + ":" + key, obj);
        this.all.put(alias + ":" + key, obj);
        return obj;
    }


    protected void hasNull()
            throws DataCheckException {
        Iterator it = this.m.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object val = this.m.get(key);
            if ((val == null) || ("".equals(String.valueOf(val)))) {
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), key + " 不能为空！");
            }
        }
        this.m.clear();
    }


    protected void synJuge()
            throws DataCheckException {
        Iterator it = this.m.keySet().iterator();
        boolean hasNull = false;
        while (it.hasNext()) {
            String key = (String) it.next();
            Object val = this.m.get(key);
            if ((val == null) || ("".equals(String.valueOf(val)))) {
                hasNull = true;
            } else if (hasNull) {
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), this.m.keySet() + " 几项中如输入一项，其他项都不能为空！");
            }
        }
        this.m.clear();
    }


    protected void isAllNull()
            throws DataCheckException {
        Iterator it = this.all.keySet().iterator();
        boolean hasNotNull = false;
        while (it.hasNext()) {
            String key = (String) it.next();
            Object val = this.all.get(key);
            if ((val != null) && (!"".equals(String.valueOf(val)))) {
                hasNotNull = true;
            }
        }
        if (!hasNotNull) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 字段不能都为空！");
        }
        this.all.clear();
    }
}