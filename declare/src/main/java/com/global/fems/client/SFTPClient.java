package com.global.fems.client;

import com.global.fems.client.utils.SFTPChannel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 *向SFTP服务器上传文件
 */
public class SFTPClient {
    private static Logger logger = LoggerFactory.getLogger(SFTPClient.class);

    /**
     *
     * @param src  文件流文件
     * @param dst  目标文件名
     * @param mode  传输模式三种:
     */
    public void put(InputStream src, String dst, int mode)  {
        SFTPChannel sftpChannel = new SFTPChannel();
        ChannelSftp channel = null;
        try {
            channel = sftpChannel.getChannel();
             channel.put(src,dst,mode);
        } catch (JSchException e) {

            logger.error("",e);
        } catch (SftpException e) {

            logger.error("",e);
        }finally {
            channel.quit();
            try {
                sftpChannel.closeChannel();
            } catch (Exception e) {
                logger.error("",e);
            }
        }
    }

}
