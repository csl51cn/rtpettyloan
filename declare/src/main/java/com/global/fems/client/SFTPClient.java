package com.global.fems.client;

import com.global.fems.client.utils.SFTPChannel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * 向SFTP服务器上传文件
 */
public class SFTPClient {
    private static Logger logger = LoggerFactory.getLogger(SFTPClient.class);

    /**
     * @param src 文件流文件
     * @param dst 目标文件名
     */
    public static void put(InputStream src, String dst) throws Exception {
        SFTPChannel sftpChannel = new SFTPChannel();
        ChannelSftp channel = null;
        try {
            channel = sftpChannel.getChannel();
            if (channel == null){
                throw new Exception("SFTP创建连接失败");
            }
            channel.put(src, dst);
        }catch (JSchException e){
            logger.error("",e);
            throw e;
        } catch (SftpException e) {
            logger.error("",e);
            throw e;
        } finally {
            if(channel != null){
                channel.quit();
            }
            try {
                sftpChannel.closeChannel();
            } catch (Exception e) {
                logger.error("SFTPClient:put(),关闭Channel异常", e);
            }
        }
    }

}
