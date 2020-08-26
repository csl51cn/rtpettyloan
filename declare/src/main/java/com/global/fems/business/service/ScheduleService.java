package com.global.fems.business.service;

import com.alibaba.fastjson.JSONObject;
import com.global.fems.business.common.SftpErrorResult;
import com.global.fems.business.domain.DeclareResult;
import com.global.fems.client.SFTPClient;
import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author: Senlin.Deng
 * @Description:
 * @date: Created in 2020/8/25 17:13
 * @Modified By:
 */
@Service
public class ScheduleService {

    @Autowired
    private QueryDeclareService queryDeclareService;
    private static final String PATH = "/error_msg/";

    /**
     * 获取sftp错误文件更新报送结果
     */
    @Scheduled(cron = "0 0 9 * * ? ")
    public void getErrorResult() throws Exception {
        Vector<ChannelSftp.LsEntry> objects = (Vector<ChannelSftp.LsEntry>) SFTPClient.listFiles(PATH);
        if (objects.size() > 0) {
            List<SftpErrorResult> resultErrors = objects.stream().parallel().map(o -> {
                String remoteFilePath = PATH + o.getFilename();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    SFTPClient.get(remoteFilePath, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return JSONObject.parseObject(new String(outputStream.toByteArray(), StandardCharsets.UTF_8), SftpErrorResult.class);
            }).collect(Collectors.toList());

            resultErrors.forEach(resultError -> {
                DeclareResult declareResult = queryDeclareService.findByRemoteFilePath(resultError.getBatchFile());
                declareResult.setContractNo(resultError.getContractNo());
                declareResult.setErrorMsg(resultError.getErrorMsg());
                queryDeclareService.update(declareResult);
            });
        }

    }

}
