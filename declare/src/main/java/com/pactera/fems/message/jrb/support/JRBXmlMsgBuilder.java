package com.pactera.fems.message.jrb.support;


import com.global.fems.message.util.OrgCode;
import com.pactera.fems.message.jrb.domain.*;
import com.pactera.fems.message.jrb.domain.business.request.BatchFileInfo;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.apache.commons.io.IOUtils;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Bean2XmlHandler;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRBXmlMsgBuilder {


    /**
     * 实时类生成xml
     *
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

        //java对象转xml
        String xml = bean2XmlHandler.toXml(transaction, reqXmlMapping, "UTF-8");
        return formatXml(xml);
    }

    /**
     * 批量文件生成xml
     *
     * @param batchFileInfo
     * @return
     * @throws Exception
     */
    public String buildXml(BatchFileInfo batchFileInfo) throws Exception {

        JRBReqBatchFileUploadMsg jrbReqBatchFileUploadMsg = new JRBReqBatchFileUploadMsg();
        Map map = JRBIntfCodeCfgUtil.getCfgCache(batchFileInfo.getClass().getName());
        String reqXmlMapping = (String) map.get("reqXmlMapping");
        jrbReqBatchFileUploadMsg.setBody(new JRBReqBody(batchFileInfo));
        Bean2XmlHandler bean2XmlHandler = new Bean2XmlHandler();
        String xml = bean2XmlHandler.toXml(jrbReqBatchFileUploadMsg, reqXmlMapping, "UTF-8");
        xml = formatXml(xml);
        return xml;
    }


    /**
     * 格式化xml,去除回车换行符和缩进符,返回的xml为一行
     *
     * @param xml
     * @return
     */
    private static String formatXml(String xml) {
        StringBuilder xmlToReturn = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlToReturn.append(xml);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xmlToReturn);
        String finalresult = m.replaceAll("");
        int len = 0;
        String str = null;
        try {
            // str = new String(finalresult.getBytes(), "utf-8");
            len = finalresult.getBytes("UTF-8").length;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String length = String.format("%08d", len);
        //清空StringBuilder
        xmlToReturn.setLength(0);
        xmlToReturn.append(length).append(finalresult);
        return xmlToReturn.toString();
    }


    /**
     * 保存报文文件到本地
     *
     * @param xml
     * @param dataType
     * @param batchNo
     * @return
     */
    public String saveBatchFile(String xml, String dataType, String batchNo) {
        Properties properties = new Properties();
        //设置组织机构代码+日期+数据类型+序号.xml
        StringBuilder fileName = new StringBuilder(OrgCode.getOrgCode());
        fileName.append("-");
        fileName.append(batchNo.substring(0, 8));
        fileName.append("-");
        fileName.append(dataType);
        fileName.append("-");
        fileName.append(batchNo.substring(8, batchNo.length()).replaceAll("^(0+)",""));
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        String path = null;
        try {
            properties.load(this.getClass().getResourceAsStream("/resource.properties"));
            path = (String) properties.get("PATH");
            path = path + fileName.toString() + ".xml";
            fileOutputStream = new FileOutputStream(path);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            int i = xml.indexOf("<");
            outputStreamWriter.write(xml.substring(i));
            outputStreamWriter.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(outputStreamWriter);
        }
        return path;
    }

    public static void main(String[] args)  {
        String xml = "<transaction><header><msg><SERVICE_CODE>SVR_PTLN</SERVICE_CODE><TRAN_CODE>PTLN001</TRAN_CODE><TRAN_TYPE></TRAN_TYPE><TRAN_MODE>ONLINE</TRAN_MODE><BRANCH_ID>91500000584252884K</BRANCH_ID><TRAN_DATE>20170526</TRAN_DATE><TRAN_TIMESTAMP>135132134</TRAN_TIMESTAMP><SERVER_ID></SERVER_ID><WS_ID></WS_ID><USER_LANG>CHINESE</USER_LANG><SEQ_NO>2017052600000020</SEQ_NO><SOURCE_BRANCH_NO></SOURCE_BRANCH_NO><DEST_BRANCH_NO></DEST_BRANCH_NO><MODULE_ID>CL</MODULE_ID><MESSAGE_TYPE>1200</MESSAGE_TYPE><MESSAGE_CODE>0001</MESSAGE_CODE><FILE_PATH></FILE_PATH></msg></header><body><gettx><CONTRACT_NO>JK991700001</CONTRACT_NO><LOAN_CATE>530001</LOAN_CATE><CUSTOMER_TYPE>480001</CUSTOMER_TYPE><CUSTOMER_NAME>苏玉刚</CUSTOMER_NAME><CERTIFICATE_TYPE>150001</CERTIFICATE_TYPE><CERTIFICATE_NO>510228197911162852</CERTIFICATE_NO><CONTRACT_AMOUNT>40000.00</CONTRACT_AMOUNT><INT_RATE>17.00000000</INT_RATE><CONTRACT_SIGN_DATE>20170104</CONTRACT_SIGN_DATE></gettx></body></transaction>";
        String  xml2 = "<transaction><header><msg><service_code>svr_file</service_code><tran_code>ptln102</tran_code><tran_mode>online</tran_mode><branch_id>91500000584252884k</branch_id><tran_date>20170608</tran_date><tran_timestamp>153708223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017060800000003</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0102</message_code><file_path>/91500000584252884k-20170608-contract_info-03.xml</file_path></msg></header></transaction>";
        String  xml3 = "<transaction><header><msg><service_code>svr_file</service_code><tran_code>ptln105</tran_code><tran_mode>online</tran_mode><branch_id>91500000584252884K</branch_id><tran_date>20170626</tran_date><tran_timestamp>170508223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017062600000009</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0105</message_code><file_path>/91500000584252884k-20170608-PAYPLAN_INFO-09.xml</file_path></msg></header></transaction>";
        String xml4 = "<transaction><header><msg><service_code>SVR_FILE</service_code><tran_code>PTLN105</tran_code><tran_mode>ONLINE</tran_mode><branch_id>58425288-4</branch_id><tran_date>20170627</tran_date><tran_timestamp>091408223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017062700000003</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0105</message_code><file_path>/58425288-4-20170627-ISSUE_INFO-03.xml</file_path></msg></header></transaction>";
        String xml5 = "<transaction><header><msg><service_code>SVR_FILE</service_code><tran_code>PTLN105</tran_code><tran_mode>ONLINE</tran_mode><branch_id>91500000584252884K</branch_id><tran_date>20170627</tran_date><tran_timestamp>094808223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017062700000002</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0105</message_code><file_path>/91500000584252884K-20170627-PAYPLAN_INFO-02.xml</file_path></msg></header></transaction>";
        String xml6 = "<transaction><header><msg><service_code>SVR_FILE</service_code><tran_code>PTLN199</tran_code><tran_mode>ONLINE</tran_mode><branch_id>91500000584252884K</branch_id><tran_date>20170627</tran_date><tran_timestamp>133514201</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017062700000015</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0199</message_code><file_path></file_path></msg></header><body><gettx><batch_no>2017062600000012</batch_no><data_type>PAYPLAN_INFO</data_type></gettx></body></transaction>";
        String xml7 = "<transaction><header><msg><SERVICE_CODE>SVR_FILE</SERVICE_CODE><TRAN_CODE>PTLN199</TRAN_CODE><TRAN_MODE>ONLINE</TRAN_MODE><BRANCH_ID>91500000584252884K</BRANCH_ID><TRAN_DATE>20170706</TRAN_DATE><TRAN_TIMESTAMP>141420201</TRAN_TIMESTAMP><USER_LANG>CHINESE</USER_LANG><SEQ_NO>2017070600000012</SEQ_NO><MODULE_ID>CL</MODULE_ID><MESSAGE_TYPE>1220</MESSAGE_TYPE><MESSAGE_CODE>0199</MESSAGE_CODE><FILE_PATH></FILE_PATH></msg></header><body><GetTx><BATCH_NO>2017070500000003</BATCH_NO><DATA_TYPE>REPAY_INFO</DATA_TYPE></GetTx></body></transaction>";
        String s = formatXml(xml7);
        System.out.println(s);

    }

    /**
     * 批量申报实时报文生成xml
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */

    public String buildXml(JRBReqBatchFileMsg headerMsg)throws DataCheckException {
        Map map = JRBIntfCodeCfgUtil.getCfgCache(headerMsg.getClass().getName());
        String reqXmlMapping = (String) map.get("reqXmlMapping");
        //java对象转xml
        Bean2XmlHandler bean2XmlHandler = new Bean2XmlHandler();
        String xml = bean2XmlHandler.toXml(headerMsg, reqXmlMapping, "UTF-8");
        return formatXml(xml);

    }
}
