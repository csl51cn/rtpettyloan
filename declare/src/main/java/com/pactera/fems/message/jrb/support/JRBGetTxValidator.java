package com.pactera.fems.message.jrb.support;

import com.thoughtworks.xstream.core.util.Primitives;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.global.framework.xmlbeans.util.PropertyUtils;

import java.lang.reflect.Field;
import java.util.*;

public class JRBGetTxValidator {


    /**校验自营贷款数据
     *
     * @param data
     * @throws DataCheckException
     */
    public  static  void validateLoanContract(Map data) throws DataCheckException{
        NullViladator nv = new NullViladator();
        Map getTx = (Map) nv.PopMap(data, "getTx", "getTx");
        nv.hasNull();
        String contractNo = (String)nv.PopMap(getTx, "contractNo", "合同编号");
        String loanCate = (String)nv.PopMap(getTx, "loanCate", "贷款类别");
        String customerType = (String)nv.PopMap(getTx, "customerType", "借款人类别");
        String customerName = (String)nv.PopMap(getTx, "customerName", "借款人名称");
        String certificateType = (String)nv.PopMap(getTx, "certificateType", "证件类型");
        String certificateNo = (String)nv.PopMap(getTx, "certificateNo", "证件号码");
        String contractAmount = (String)nv.PopMap(getTx, "contractAmount", "合同金额");
        String intRate = (String)nv.PopMap(getTx, "intRate", "月利率");
        String contractSignDate = (String)nv.PopMap(getTx, "contractSignDate", "签约日期");
        nv.hasNull();




    }



    public static void setFeild(Object obj, Map data)
            throws DataCheckException {
        try {
            Field[] fs = PropertyUtils.getDeclaredFields(obj.getClass(), new ArrayList());

            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                if (!f.getName().equals("serialVersionUID")) {
                    f.setAccessible(true);
                    f.set(obj, getVal(obj, data, i, f));
                }
            }
        } catch (Exception e) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
        }
    }

    private static Object getVal(Object obj, Map data, int i, Field f)
            throws DataCheckException {
        if (Collection.class.isAssignableFrom(f.getType()))
            return dealCollection(obj, data, i, f);
        if ((f.getType().isPrimitive()) || (Primitives.unbox(f.getType()) != null)) {
            return dealPrimitive(data, f);
        }
        if (String.class.equals(f.getType()))
            return getMapStringVal(data, f.getName());
        if (Object.class.isAssignableFrom(f.getType())) {
            return dealSingleObject(data, f);
        }
        return null;
    }

    private static List dealCollection(Object obj, Map data, int i, Field f)
            throws DataCheckException {
        try {
            List list = (List) data.get(f.getName());
            list = list == null ? new ArrayList() : list;
            List feldlst = new ArrayList();
            Class entityClass = getEntityClass(obj.getClass());
            for (int j = 0; j < list.size(); j++) {
                Object listEnTity = entityClass.newInstance();
                Map sub = (Map) list.get(j);
                setFeild(listEnTity, sub == null ? new HashMap() : sub);
                feldlst.add(listEnTity);
            }
            return feldlst;
        } catch (Exception e) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
        }
    }


    private static Object dealPrimitive(Map data, Field f)
            throws DataCheckException {
        try {
            Class boxClass = f.getType();
            if (!f.getType().isPrimitive()) {
                boxClass = Primitives.unbox(f.getType());
            }
            String val = getMapStringVal(data, f.getName());
            return boxClass.getMethod("valueOf", new Class[]{String.class}).invoke(null, new Object[]{val});
        } catch (Exception e) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
        }
    }

    private static String getMapStringVal(Map m, String key) {
        Object obj = m.get(key);
        return obj == null ? "" : String.valueOf(obj);
    }

    private static Object dealSingleObject(Map data, Field f)
            throws DataCheckException {
        try {
            Object entity = f.getType().newInstance();
            Map sub = (Map) data.get(f.getName());
            setFeild(entity, sub == null ? new HashMap() : sub);
            return entity;
        } catch (Exception e) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
        }
    }

    private static Class getEntityClass(Class entityClass)
            throws DataCheckException {
        try {
            return Class.forName(entityClass.getName() + "Row");
        } catch (ClassNotFoundException e) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
        }
    }
}
