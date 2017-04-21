//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.global.framework.xmlbeans.util;

import com.thoughtworks.xstream.core.util.Primitives;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class PropertyUtils {
    public PropertyUtils() {
    }

    public static void bean2Map(Object obj, Map map) throws Exception {
        Field[] fs = getDeclaredFields(obj.getClass(), new ArrayList());

        for(int i = 0; i < fs.length; ++i) {
            Field f = fs[i];
            Object value = getFiledValue(obj, f);
            if(!List.class.isAssignableFrom(f.getType())) {
                if(!f.getType().isPrimitive() && Primitives.unbox(f.getType()) == null && !String.class.equals(f.getType())) {
                    if(Object.class.isAssignableFrom(f.getType())) {
                        HashMap var11 = new HashMap();
                        if(value != null) {
                            bean2Map(value, var11);
                        }

                        map.put(f.getName(), var11);
                    }
                } else {
                    map.put(f.getName(), value);
                }
            } else {
                Object m = (List)value;
                if(m == null) {
                    m = new ArrayList();
                }

                ArrayList ret = new ArrayList();

                for(int j = 0; j < ((List)m).size(); ++j) {
                    Object o = ((List)m).get(j);
                    HashMap m1 = new HashMap();
                    bean2Map(o, m1);
                    ret.add(m1);
                }

                map.put(f.getName(), ret);
            }
        }

    }

    private static Object getFiledValue(Object obj, Field f) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String getMethodName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
        Method getMethod = obj.getClass().getMethod(getMethodName, new Class[0]);
        Object value = getMethod.invoke(obj, new Object[0]);
        return value;
    }

    public static Field getDeclaredField(Class clazz, String fieldname) {
        if(clazz != null && !clazz.isInterface()) {
            Field f = null;

            try {
                f = clazz.getDeclaredField(fieldname);
            } catch (Exception var4) {
                f = getDeclaredField(clazz.getSuperclass(), fieldname);
            }

            if(f == null && !clazz.equals(Object.class)) {
                f = getDeclaredField(clazz.getSuperclass(), fieldname);
            }

            return f;
        } else {
            return null;
        }
    }

    public static Method getDeclaredMethod(Class clazz, String methName, Class[] types) {
        if(clazz != null && !clazz.isInterface()) {
            Method m = null;

            try {
                m = clazz.getMethod(methName, types);
            } catch (Exception var5) {
                m = getDeclaredMethod(clazz.getSuperclass(), methName, types);
            }

            if(m == null && !clazz.equals(Object.class)) {
                m = getDeclaredMethod(clazz.getSuperclass(), methName, types);
            }

            return m;
        } else {
            return null;
        }
    }

    public static Field[] getDeclaredFields(Class clazz, List list) throws DataCheckException {
        try {
            if(clazz != null && !clazz.isInterface()) {
                Field[] e = clazz.getDeclaredFields();
                if(e != null && e.length > 0) {
                    list.addAll(Arrays.asList(e));

                    try {
                        Field e1 = clazz.getDeclaredField("serialVersionUID");
                        list.remove(e1);
                    } catch (Exception var4) {
                        ;
                    }
                }

                if(!clazz.isInterface() && !clazz.equals(Object.class)) {
                    getDeclaredFields(clazz.getSuperclass(), list);
                }

                e = new Field[list.size()];
                return (Field[])((Field[])list.toArray(e));
            } else {
                return null;
            }
        } catch (Exception var5) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "获取实体类[" + clazz + "]的属性失败:" + var5.getMessage());
        }
    }

    public static void copyBean2Map(Object srcBean, Map destMap) throws Exception {
        Class type = srcBean.getClass();
        Field[] fields = type.getDeclaredFields();

        for(int i = 0; i < fields.length; ++i) {
            String fieldName = fields[i].getName();
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method readMethod = null;
            Object result = null;

            try {
                readMethod = type.getMethod(methodName, new Class[0]);
                result = readMethod.invoke(srcBean, new Object[0]);
            } catch (NoSuchMethodException var10) {
                ;
            }

            if(result != null) {
                destMap.put(fieldName, result);
            } else {
                destMap.put(fieldName, "");
            }
        }

    }
}
