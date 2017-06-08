package com.pactera.fems.message.jrb.support;

import com.thoughtworks.xstream.core.util.Primitives;
import org.apache.commons.lang.StringUtils;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.global.framework.xmlbeans.util.PropertyUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JRBGetTxValidator {


    /**
     * 校验自营贷款数据
     *
     * @param data
     * @throws DataCheckException
     */
    public static void validateLoanContract(Map data) throws DataCheckException {
        NullViladator nv = new NullViladator();
        String loanCate = (String) nv.PopMap(data, "loanCate", "贷款类别");
        String customerType = (String) nv.PopMap(data, "customerType", "借款人类别");
        String customerName = (String) nv.PopMap(data, "customerName", "借款人名称");
        String certificateType = (String) nv.PopMap(data, "certificateType", "证件类型");
        String certificateNo = (String) nv.PopMap(data, "certificateNo", "证件号码");
        Double contractAmount = (Double) nv.PopMap(data, "contractAmount", "合同金额");
        Double intRate = (Double) nv.PopMap(data, "intRate", "月利率");
        Date contractSignDate = (Date) nv.PopMap(data, "contractSignDate", "签约日期");
        nv.hasNull();
        validateCertificateType(certificateType, certificateNo);

        if (!"530001".equals(loanCate)) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "贷款类型错误,应为自营贷款！");
        }

        if (!"480001".equals(customerType) && !"480002".equals(customerType)) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "借款人类型错误,应为个人或企业！");
        }

        if ((StringUtils.isBlank(customerName)) || (!customerName.matches("(([一-龥]|[a-z]|[A-Z]|\\.\\-\\·)+\\s*)+"))) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "姓名格式要求：姓名不能为空并且只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格");
        }

        if (contractAmount == null || contractAmount <= 0.0) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "合同金额必须大于0");
        }

        if (intRate == null || intRate <= 0.0) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "月利率必须大于0");
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = 0L;
        long contractSignDateTime = 0L;

        try {
            currentTime = sdf.parse(sdf.format(new Date())).getTime();
            contractSignDateTime = contractSignDate.getTime();
        } catch (ParseException e) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "日期转换出错：" + e.getMessage());
        }
        if (currentTime < contractSignDateTime) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 合同签订时间不能大于当前日期 ");
        }

    }

    /**
     * 校验委托贷款数据
     *
     * @param data
     * @throws DataCheckException
     */
    public static void validateEntrustedLoan(Map data) throws DataCheckException {
        NullViladator nv = new NullViladator();
        String conCustomerType = (String) nv.PopMap(data, "conCustomerType", "委托人类别");
        String conCustomerName = (String) nv.PopMap(data, "conCustomerName", "委托人");
        String conCertificateType = (String) nv.PopMap(data, "conCertificateType", "委托人证件类型");
        String conCertificateNo = (String) nv.PopMap(data, "conCertificateNo", "委托人证件号码");
        Double conFee = (Double) nv.PopMap(data, "conFee", "委托代理费");
        nv.synJuge();

        if (!"480001".equals(conCustomerType) && !"480002".equals(conCustomerType)) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "委托人类别错误,应为个人或企业！");
        }
        if ((StringUtils.isBlank(conCustomerName)) || (!conCustomerName.matches("(([一-龥]|[a-z]|[A-Z]|\\.\\-\\·)+\\s*)+"))) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "委托人姓名有误:格式要求：姓名不能为空并且只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格");
        }

        validateCertificateType(conCertificateType, conCertificateNo);

        if (conFee == null || conFee <= 0.0) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "合同金额必须大于0");
        }


        String loanCate = (String) nv.PopMap(data, "loanCate", "贷款类别");
        String customerType = (String) nv.PopMap(data, "customerType", "借款人类别");
        String customerName = (String) nv.PopMap(data, "customerName", "借款人名称");
        String certificateType = (String) nv.PopMap(data, "certificateType", "证件类型");
        String certificateNo = (String) nv.PopMap(data, "certificateNo", "证件号码");
        Double contractAmount = (Double) nv.PopMap(data, "contractAmount", "合同金额");
        Double intRate = (Double) nv.PopMap(data, "intRate", "月利率");
        Date contractSignDate = (Date) nv.PopMap(data, "contractSignDate", "签约日期");
        nv.hasNull();
        validateCertificateType(certificateType, certificateNo);

        if (!"530002".equals(loanCate)) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "贷款类型错误,应为委托贷款！");
        }

        if (!"480001".equals(customerType) && !"480002".equals(customerType)) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "借款人类别错误,应为个人或企业！");
        }

        if ((StringUtils.isBlank(customerName)) || (!customerName.matches("(([一-龥]|[a-z]|[A-Z]|\\.\\-\\·)+\\s*)+"))) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "借款人姓名有误:格式要求：姓名不能为空并且只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格");
        }

        if (contractAmount == null || contractAmount <= 0.0) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "合同金额必须大于0");
        }

        if (intRate == null || intRate <= 0.0) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "月利率必须大于0");
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = 0L;
        long contractSignDateTime = 0L;

        try {
            currentTime = sdf.parse(sdf.format(new Date())).getTime();
            contractSignDateTime = contractSignDate.getTime();
        } catch (ParseException e) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "日期转换出错：" + e.getMessage());
        }
        if (currentTime < contractSignDateTime) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 合同签订时间不能大于当前日期 ");
        }


    }

    public static void validateCertificateType(String certificateType, String certificateNo)
            throws DataCheckException {
        if (!certificateType.matches("\\d+")) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件类型代码 必须是 数字");
        }
        int idtypeCode = Integer.valueOf(certificateType).intValue();
        switch (idtypeCode) {
            case 150001:
                if ((certificateNo == null) || (!certificateNo.matches("[0-9]{17}([A-Z]|[0-9]){1}"))) {
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为身份证：证件号码不能为空并且长度18位，由全部数字或数字加最末一位大写英文字符组成。");
                }

                break;
            case 150003:
                if ((certificateNo == null) || (!certificateNo.matches("([A-Z]|[0-9]|[一-龥])+"))) {
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为中国护照：证件号码不能为空并且只允许输入大写字母和数字、中文。");
                }
                break;
        }
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

    public static void setFeild(Object obj, Map data,String excludeField)
            throws DataCheckException {
        try {
            Field[] fs = PropertyUtils.getDeclaredFields(obj.getClass(), new ArrayList());
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                if (!f.getName().equals("serialVersionUID") && !f.getName().equals(excludeField)) {
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
