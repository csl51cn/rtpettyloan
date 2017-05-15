package com.pactera.fems.message.jrb.domain;

import com.thoughtworks.xstream.core.util.Primitives;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.global.framework.xmlbeans.util.PropertyUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by senlin.deng on 2017/5/12.
 */
public class JRBGetTxValidator {

//
//    public static void setFeild(Object obj, Map data)
//            throws DataCheckException {
//        try {
//            Field[] fs = PropertyUtils.getDeclaredFields(obj.getClass(), new ArrayList());
//
//            for (int i = 0; i < fs.length; i++) {
//                Field f = fs[i];
//                if (!f.getName().equals("serialVersionUID")) {
//                    f.setAccessible(true);
//                    f.set(obj, getVal(obj, data, i, f));
//                }
//            }
//        } catch (Exception e) {
//            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
//        }
//    }
//
//    private static Object getVal(Object obj, Map data, int i, Field f)
//            throws DataCheckException {
//        if (Collection.class.isAssignableFrom(f.getType()))
//            return dealCollection(obj, data, i, f);
//        if ((f.getType().isPrimitive()) || (Primitives.unbox(f.getType()) != null)) {
//            return dealPrimitive(data, f);
//        }
//        if (String.class.equals(f.getType()))
//            return getMapStringVal(data, f.getName());
//        if (Object.class.isAssignableFrom(f.getType())) {
//            return dealSingleObject(data, f);
//        }
//        return null;
//    }
//
//    private static List dealCollection(Object obj, Map data, int i, Field f)
//     throws DataCheckException
//   {
//     try
//     {
//      List list = (List)data.get(f.getName());
//      list = list == null ? new ArrayList() : list;
//      List feldlst = new ArrayList();
//      Class entityClass = getEntityClass(obj.getClass());
//      for (int j = 0; j < list.size(); j++) {
//       Object listEnTity = entityClass.newInstance();
//        Map sub = (Map)list.get(j);
//        setFeild(listEnTity, sub == null ? new HashMap() : sub);
//        feldlst.add(listEnTity);
//       }
//      return feldlst;
//     } catch (Exception e) {
//       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
//     }
//   }
}
