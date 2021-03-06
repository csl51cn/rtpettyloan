package com.global.fems.client.utils;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Sftp通道工具类
 * 参考http://www.cnblogs.com/longyg/archive/2012/06/25/2556576.html
 */

public class SFTPChannel {
    private Session session = null;
    private Channel channel = null;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Integer timeout;

    private static final Logger LOG = LoggerFactory.getLogger(SFTPChannel.class);

    public SFTPChannel() {
        Properties properties = new Properties();
        InputStream resourceAsStream = SFTPChannel.class.getResourceAsStream("/resource.properties");
        try {
            properties.load(resourceAsStream);
            host = (String) properties.get("SFTP_HOST");
            port = Integer.parseInt((String)properties.get("SFTP_PORT")) ;
            username = (String) properties.get("SFTP_USERNAME");
            password = (String) properties.get("SFTP_PASSWORD");
            timeout = Integer.parseInt((String ) properties.get("SFTP_TIMEOUT"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(resourceAsStream);
        }
    }

    /**
     * 创建通道
     *
     * @return
     * @throws JSchException
     */
    public ChannelSftp getChannel() throws JSchException {


        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(username, host, port); // 根据用户名，主机ip，端口获取一个Session对象

        if (password != null) {
            session.setPassword(password); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置超时时间
        System.out.println(session.getClientVersion());
        session.connect(); // 通过Session建立链接

        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        LOG.debug("Connected successfully to ftpHost = " + host + ",as ftpUserName = " + username
                + ", returning: " + channel);
        return (ChannelSftp) channel;
    }

    /**
     * 关闭通道
     *
     * @throws Exception
     */
    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}
