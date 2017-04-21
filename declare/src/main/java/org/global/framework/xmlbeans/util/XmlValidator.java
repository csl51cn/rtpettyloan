//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.global.framework.xmlbeans.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.global.framework.xmlbeans.bean.XmlMsgCfg;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class XmlValidator {
    private static final Logger log = Logger.getLogger(XmlValidator.class);

    public XmlValidator() {
    }

    public static void validateFieldOfElement(XmlMsgCfg cfg, Element e, String filename) throws DataCheckException {
        String type = cfg.getType();
        String isReq = cfg.getRequired();
        String fmt = cfg.getFormat();
        String len = cfg.getLength();
        if(!"Single".equals(type) && !"Group".equals(type)) {
            String txtval = e.getText() != null?e.getText().trim():e.getText();
            if(!"Y".equals(isReq) || txtval != null && !"".equals(txtval)) {
                if("String".equals(type)) {
                    if("Y".equals(isReq)) {
                        validateForString(cfg, filename, txtval, len);
                    } else if(txtval != null && !"".equals(txtval)) {
                        validateForString(cfg, filename, txtval, len);
                    }

                } else if(!"Integer".equals(type) && !"Double".equals(type)) {
                    if("date".equals(type.toLowerCase())) {
                        try {
                            SimpleDateFormat e1;
                            if("Y".equals(isReq)) {
                                e1 = new SimpleDateFormat(fmt);
                                e.setText(e1.format(e1.parse(txtval)));
                            } else if(txtval != null && !"".equals(txtval)) {
                                e1 = new SimpleDateFormat(fmt);
                                e.setText(e1.format(e1.parse(txtval)));
                            }
                        } catch (Exception var9) {
                            log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Date类型，格式必须为“" + fmt + "”");
                            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_870006.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Date类型，格式必须为“" + fmt + "”");
                        }
                    }

                } else {
                    if("Y".equals(isReq)) {
                        validateForNumber(cfg, e, filename, type, fmt, txtval, len);
                    } else if(txtval != null && !"".equals(txtval)) {
                        validateForNumber(cfg, e, filename, type, fmt, txtval, len);
                    }

                }
            } else {
                log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的为必输项！");
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_870006.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的为必输项！");
            }
        }
    }

    private static void validateForNumber(XmlMsgCfg cfg, Element e, String filename, String type, String fmt, String txtval, String len) throws DataCheckException {
        String lenStr;
        if("Double".equals(type)) {
            if(len != null && len.matches("\\[\\d+\\]")) {
                if(fmt != null && !"".equals(fmt.trim()) && fmt.matches("#*\\.#*")) {
                    lenStr = len.substring(len.indexOf("[") + 1, len.indexOf("]"));
                    if(txtval.replaceAll("-", "").replaceAll("\\.", "").length() > Integer.parseInt(lenStr)) {
                        log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]必须小于或等于系统定义的长度" + lenStr + "");
                        throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_870006.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]必须小于或等于系统定义的长度" + lenStr + "");
                    } else if(!txtval.matches("\\d+(\\.\\d+)?") && !txtval.matches("-\\d+(\\.\\d+)?")) {
                        log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]必须为整数或小数类型");
                        throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_870006.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]必须为整数或小数类型");
                    } else {
                        DecimalFormat var11 = new DecimalFormat(fmt.replaceAll("#", "0"));
                        e.setText(StringUtils.isBlank(txtval)?"":var11.format(Double.parseDouble(txtval.replaceAll(",", ""))));
                    }
                } else {
                    log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Double类型，Double类型必须定义format属性，格式为“#*.#*”");
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Double类型，Double类型必须定义format属性，格式为“#*.#*”");
                }
            } else {
                log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Double类型，length属性配置格式必须遵循正则表达式为\"\\[\\d+\\]\"");
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Double类型，length属性配置格式必须遵循正则表达式为\"\\[\\d+\\]\"");
            }
        } else {
            if("Integer".equals(type)) {
                if(len == null || !len.matches("\\[\\d+(\\|\\d+)*\\]")) {
                    log.error(filename + ".xml配置文件下String类型的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Integer类型，length属性配置格式必须遵循正则表达式为\"\\[\\d+(\\|\\d+)*\\]\"");
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为Integer类型，length属性配置格式必须遵循正则表达式为\"\\[\\d+(\\|\\d+)*\\]\"");
                }

                lenStr = len.substring(len.indexOf("[") + 1, len.indexOf("]"));
                String[] lens = lenStr.split("\\|");
                boolean b = false;

                for(int i = 0; i < lens.length; ++i) {
                    if(txtval.length() == Integer.parseInt(lens[i])) {
                        b = true;
                        break;
                    }
                }

                if(!b) {
                    log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]与系统定义的长度[" + lenStr + "]不相符！");
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_870006.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]与系统定义的长度[" + lenStr + "]不相符！");
                }
            }

        }
    }

    private static void validateForString(XmlMsgCfg cfg, String filename, String txtval, String len) throws DataCheckException {
        if(len == null || !len.matches("\\[\\d+(\\,?\\d+)?\\]") && !len.matches("\\[\\d+(\\|\\d+)*\\]")) {
            log.error(filename + ".xml配置文件下String类型的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为String类型，length属性配置格式必须遵循正则表达式为\"[\\d+(,?\\d+)?]\" 或 \"\\[\\d+(\\|\\d+)*\\]\"");
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]为String类型，length属性配置格式必须遵循正则表达式为\"[\\d+(,?\\d+)?]\" 或 \"\\[\\d+(\\|\\d+)*\\]\"");
        } else {
            String lenStr = len.substring(len.indexOf("[") + 1, len.indexOf("]"));
            String[] lens = new String[]{lenStr};
            if(lenStr.contains(",")) {
                lens = lenStr.split(",");
            } else if(lenStr.contains("|")) {
                lens = lenStr.split("\\|");
            }

            if(lens.length == 1 && Integer.parseInt(lens[0]) != txtval.length()) {
                log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]与系统定义的长度[" + lenStr + "]不相符！");
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_870006.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]与系统定义的长度[" + lenStr + "]不相符！");
            } else if(lens.length == 2 && (txtval.length() < Integer.parseInt(lens[0]) || txtval.length() > Integer.parseInt(lens[1]))) {
                log.error(filename + ".xml配置文件下的字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]与系统定义的长度[" + lenStr + "]不相符！");
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_870006.getCode(), "字段项[" + cfg.getText() + "(" + cfg.getFieldName() + ")]的内容长度[" + txtval.length() + "]与系统定义的长度[" + lenStr + "]不相符！");
            }
        }
    }
}
