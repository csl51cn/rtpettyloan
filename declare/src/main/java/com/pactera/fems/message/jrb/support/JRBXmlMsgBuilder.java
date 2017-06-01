package com.pactera.fems.message.jrb.support;


import com.pactera.fems.message.jrb.domain.*;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Bean2XmlHandler;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRBXmlMsgBuilder {

    /**
     * 实时类生成xml
     * @param getTx
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    public String buildXml(JRBGetTx getTx, JRBReqHeaderMsg headerMsg) throws DataCheckException {

        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTx.getClass().getName());
        String reqXmlMapping = (String) map.get("reqXmlMapping");


        JRBReqMsg transaction = new JRBReqMsg();
        transaction.setHeader(new JRBReqHeader(headerMsg));
        transaction.setBody(new JRBReqBody(getTx));
        Bean2XmlHandler bean2XmlHandler = new Bean2XmlHandler();

        //java对象转xml,保存xml到本地
        String xml = bean2XmlHandler.toXml(transaction, reqXmlMapping, "UTF-8");
        return formatXml(xml);
    }


    /**
     * 格式化xml,去除回车换行符和缩进符,返回的xml为一行
     * @param xml
     * @return
     */
    private static String formatXml(String xml) {
        StringBuilder xmlToReturn = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?>");
        xmlToReturn.append(xml);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xmlToReturn);
        String finalresult = m.replaceAll("");
        int len = 0;
        String str = null;
        try {
            int length = finalresult.length();
            //len = finalresult.getBytes("GBK").length;
            len=finalresult.getBytes().length;
            str = new String(finalresult.getBytes(), "GBK");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String length = String.format("%08d", len);
        //清空StringBuilder
        xmlToReturn.setLength(0);
        xmlToReturn.append(length).append(finalresult);
        return xmlToReturn.toString();
    }

    public static void main(String[] args) {
        String  xml = "<transaction><header><msg><SERVICE_CODE>SVR_PTLN</SERVICE_CODE><TRAN_CODE>PTLN001</TRAN_CODE><TRAN_TYPE></TRAN_TYPE><TRAN_MODE>ONLINE</TRAN_MODE><BRANCH_ID>91500000584252884K</BRANCH_ID><TRAN_DATE>20170526</TRAN_DATE><TRAN_TIMESTAMP>135132134</TRAN_TIMESTAMP><SERVER_ID></SERVER_ID><WS_ID></WS_ID><USER_LANG>CHINESE</USER_LANG><SEQ_NO>2017052600000020</SEQ_NO><SOURCE_BRANCH_NO></SOURCE_BRANCH_NO><DEST_BRANCH_NO></DEST_BRANCH_NO><MODULE_ID>CL</MODULE_ID><MESSAGE_TYPE>1200</MESSAGE_TYPE><MESSAGE_CODE>0001</MESSAGE_CODE><FILE_PATH></FILE_PATH></msg></header><body><gettx><CONTRACT_NO>JK991700001</CONTRACT_NO><LOAN_CATE>530001</LOAN_CATE><CUSTOMER_TYPE>480001</CUSTOMER_TYPE><CUSTOMER_NAME>苏玉刚</CUSTOMER_NAME><CERTIFICATE_TYPE>150001</CERTIFICATE_TYPE><CERTIFICATE_NO>510228197911162852</CERTIFICATE_NO><CONTRACT_AMOUNT>40000.00</CONTRACT_AMOUNT><INT_RATE>17.00000000</INT_RATE><CONTRACT_SIGN_DATE>20170104</CONTRACT_SIGN_DATE></gettx></body></transaction>";
        String s = formatXml(xml);

        System.out.println(s);

    }
    /**
     * 批量上传生成xml
     * @param getTxList
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    public String buildXml(List<JRBGetTx> getTxList ,JRBReqHeaderMsg headerMsg)throws DataCheckException{
        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTxList.get(0).getClass().getName());
        String reqXmlMapping = (String) map.get("reqXmlMapping");

        return null;
    }


}
