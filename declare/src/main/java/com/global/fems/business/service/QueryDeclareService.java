package com.global.fems.business.service;

import com.global.fems.business.domain.DeclareResult;
import com.global.framework.dbutils.support.PageBean;
import com.global.param.domain.ResultModel;

/**
 * 查询上报结果管理Service接口
 */
public interface QueryDeclareService extends Subject {

    /**
     * 向监管平台查询申报状态
     *
     * @param id     记录id
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    ResultModel queryDeclared(String id, String userId) throws Exception;

    /**
     * 查询在本地数据库中的上报结果
     *
     * @param batchNo         批次号
     * @param transactionType 报文类型
     * @param startDate       开始日期
     * @param endDate         结束日期
     * @param pageBean        分页对象
     * @return
     */
    PageBean queryRawDeclareData(String batchNo, String transactionType, String startDate, String endDate, PageBean pageBean);

    /**
     * 通过文件名查询上报结果记录
     *
     * @param batchFileName 文件名
     * @return
     */
    DeclareResult findByRemoteFilePath(String batchFileName);

    /**
     * 更新记录
     * @param declareResult
     */
    void update(DeclareResult declareResult);
}
