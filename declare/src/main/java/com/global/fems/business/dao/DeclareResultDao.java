package com.global.fems.business.dao;

import com.global.fems.business.domain.DeclareResult;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;

/**
 * @author: Senlin.Deng
 * @Description: 批量文件申报结果DAO
 * @date: Created in 2018/9/3 16:16
 * @Modified By:
 */
public interface DeclareResultDao {
    /**
     * 保存或更新批量文件申报结果
     *
     * @param declareResult 批量文件申报结果
     * @throws DAOException
     */
    void saveOrUpdate(DeclareResult declareResult) throws DAOException;

    /**
     * 查询本地数据库中的数据
     *
     * @param batchNo         批次号
     * @param transactionType 报文类型
     * @param startDate       开始日期
     * @param endDate         结束日期
     * @param pageBean        分页对象
     * @return
     */
    PageBean findRawDeclareData(String batchNo, String transactionType, String startDate, String endDate, PageBean pageBean) throws DAOException;


    /**
     * 通过id查询记录
     *
     * @param id 主键
     * @return
     * @throws DAOException
     */
    DeclareResult findDeclareResultById(String id) throws DAOException;
}
