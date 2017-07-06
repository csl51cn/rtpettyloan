package com.global.fems.client;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * socket通信
 */
public class SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private static Socket socket;


    public SocketClient() {
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = SocketClient.class.getResourceAsStream("/resource.properties");
            properties.load(resourceAsStream);
            String ip = (String) properties.get("SOCKET_IP");
            int port = Integer.parseInt((String) properties.get("SOCKET_PORT"));
            socket = new Socket(ip, port);


        } catch (IOException e) {
            logger.error("SocketClient:空参构造,连接失败", e);
        }

    }


    public static String sendMsg(String xml) {

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder info = new StringBuilder();
        try {
            outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");//将输出流包装成打印流
            outputStreamWriter.write(xml);
            outputStreamWriter.flush();
            socket.shutdownOutput();//关闭输出流

            inputStream = socket.getInputStream();//获取一个字节输入流，接收服务端的信息
            int n = 2048;
            byte buffer[] = new byte[n];

            while ((inputStream.read(buffer, 0, n) != -1)) {
                info.append(new String(buffer, "UTF-8"));
            }

            System.out.println("客户端接收服务端发送信息：" + info.toString());

        } catch (IOException e) {
            logger.error("SocketClient: sendMsg()", e);
        } finally {
            //关闭相对应的资源
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(outputStream);

            try {
                socket.close();
            } catch (IOException e) {
                logger.error("SocketClient: sendMsg(),socket关闭时发生读写异常", e);
            }

        }
        return info.toString();

    }

    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        //String s = client.sendMsg("00000999<?xml version='1.0' encoding='UTF-8'?><transaction><header><msg><SERVICE_CODE>SVR_PTLN</SERVICE_CODE><TRAN_CODE>PTLN001</TRAN_CODE><TRAN_TYPE></TRAN_TYPE><TRAN_MODE>ONLINE</TRAN_MODE><BRANCH_ID>91500000584252884K</BRANCH_ID><TRAN_DATE>20170526</TRAN_DATE><TRAN_TIMESTAMP>131531851</TRAN_TIMESTAMP><SERVER_ID></SERVER_ID><WS_ID></WS_ID><USER_LANG>CHINESE</USER_LANG><SEQ_NO>2017052600000017</SEQ_NO><SOURCE_BRANCH_NO></SOURCE_BRANCH_NO><DEST_BRANCH_NO></DEST_BRANCH_NO><MODULE_ID>CL</MODULE_ID><MESSAGE_TYPE>1200</MESSAGE_TYPE><MESSAGE_CODE>0001</MESSAGE_CODE><FILE_PATH></FILE_PATH></msg></header><body><gettx><CONTRACT_NO>JK991700004</CONTRACT_NO><LOAN_CATE>530001</LOAN_CATE><CUSTOMER_TYPE>480001</CUSTOMER_TYPE><CUSTOMER_NAME>闄堥</CUSTOMER_NAME><CERTIFICATE_TYPE>150001</CERTIFICATE_TYPE><CERTIFICATE_NO>510221196807044910</CERTIFICATE_NO><CONTRACT_AMOUNT>150000.00</CONTRACT_AMOUNT><INT_RATE>17.00000000</INT_RATE><CONTRACT_SIGN_DATE>20170105</CONTRACT_SIGN_DATE></gettx></body></transaction>");
        //String s = client.sendMsg("00000816<?xml version=\"1.0\" encoding=\"UTF-8\"?><transaction><header><msg><SERVICE_CODE>SVR_PTLN</SERVICE_CODE><TRAN_CODE>PTLN001</TRAN_CODE><TRAN_TYPE></TRAN_TYPE><TRAN_MODE>ONLINE</TRAN_MODE><BRANCH_ID>67842312-7</BRANCH_ID><TRAN_DATE>20140930</TRAN_DATE><TRAN_TIMESTAMP>124514201</TRAN_TIMESTAMP><SERVER_ID>********</SERVER_ID><WS_ID>127.0.0.1</WS_ID><USER_LANG> CHINESE </USER_LANG><SEQ_NO>************</SEQ_NO><SOURCE_BRANCH_NO>******</SOURCE_BRANCH_NO><DEST_BRANCH_NO>********</DEST_BRANCH_NO><MODULE_ID>CL</MODULE_ID><MESSAGE_TYPE>1200</MESSAGE_TYPE><MESSAGE_CODE>0001</MESSAGE_CODE><FILE_PATH></FILE_PATH></msg></header><body><GetTx><CONTRACT_NO>12345</CONTRACT_NO><LOAN_CATE>*****</LOAN_CATE><CON_FEE>25.54</CON_FEE><CUSTOMER_TYPE>*****</CUSTOMER_TYPE><CUSTOMER_NAME>*****</CUSTOMER_NAME></GetTx></body></transaction>");
        // String s  = client.sendMsg("00000529<?xml version=\"1.0\" encoding=\"UTF-8\"?><transaction><header><msg><service_code>svr_file</service_code><tran_code>ptln102</tran_code><tran_mode>online</tran_mode><branch_id>91500000584252884K</branch_id><tran_date>20170608</tran_date><tran_timestamp>153708223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017060800000003</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0102</message_code><file_path>/91500000584252884k-20170608-CONTRACT_INFO-03.xml</file_path></msg></header></transaction>");
        //String s  = client.sendMsg("00000528<?xml version=\"1.0\" encoding=\"UTF-8\"?><transaction><header><msg><service_code>svr_file</service_code><tran_code>ptln105</tran_code><tran_mode>online</tran_mode><branch_id>91500000584252884K</branch_id><tran_date>20170626</tran_date><tran_timestamp>170508223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017062600000009</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0105</message_code><file_path>/91500000584252884K-20170626-PAYPLAN_INFO-09.xml</file_path></msg></header></transaction>");
        // String s  = client.sendMsg("00000510<?xml version=\"1.0\" encoding=\"UTF-8\"?><transaction><header><msg><service_code>SVR_FILE</service_code><tran_code>PTLN105</tran_code><tran_mode>ONLINE</tran_mode><branch_id>58425288-4</branch_id><tran_date>20170627</tran_date><tran_timestamp>091408223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017062700000003</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0105</message_code><file_path>/58425288-4-20170627-ISSUE_INFO-03.xml</file_path></msg></header></transaction>");
        //String s  = client.sendMsg("00000528<?xml version=\"1.0\" encoding=\"UTF-8\"?><transaction><header><msg><service_code>SVR_FILE</service_code><tran_code>PTLN105</tran_code><tran_mode>ONLINE</tran_mode><branch_id>91500000584252884K</branch_id><tran_date>20170626</tran_date><tran_timestamp>094808223</tran_timestamp><user_lang>CHINESE</user_lang><seq_no>2017062600000014</seq_no><module_id>CL</module_id><message_type>1220</message_type><message_code>0105</message_code><file_path>/91500000584252884K-20170626-PAYPLAN_INFO-12.xml</file_path></msg></header></transaction>");
        String s = client.sendMsg("00000557<?xml version='1.0' encoding='UTF-8'?><transaction><header><msg><SERVICE_CODE>SVR_FILE</SERVICE_CODE><TRAN_CODE>PTLN199</TRAN_CODE><TRAN_MODE>ONLINE</TRAN_MODE><BRANCH_ID>91500000584252884K</BRANCH_ID><TRAN_DATE>20170627</TRAN_DATE><TRAN_TIMESTAMP>140614201</TRAN_TIMESTAMP><USER_LANG>CHINESE</USER_LANG><SEQ_NO>2017062700000015</SEQ_NO><MODULE_ID>CL</MODULE_ID><MESSAGE_TYPE>1220</MESSAGE_TYPE><MESSAGE_CODE>0199</MESSAGE_CODE></msg></header><body><GetTx><BATCH_NO>2017062600000012</BATCH_NO><DATA_TYPE>PAYPLAN_INFO</DATA_TYPE></GetTx></body></transaction>");
        System.out.println(s);
    }
}