package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.DeclareResultDao;
import com.global.fems.business.domain.DeclareResult;
import com.global.fems.business.enums.MessageTypeEnum;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Senlin.Deng
 * @Description:  批量文件申报结果DAO
 * @date: Created in 2018/9/3 16:16
 * @Modified By:
 */
@Repository
public class DeclareResultDaoImpl extends BaseDaoSupport implements DeclareResultDao {


    /**
     * 保存或更新批量文件申报结果
     *
     * @param declareResult 批量文件申报结果
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(DeclareResult declareResult) throws DAOException {
        super.saveOrUpdate(declareResult);
    }

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
    @Override
    public PageBean findRawDeclareData(String batchNo, String transactionType, String startDate, String endDate, PageBean pageBean) {

        StringBuilder sql = new StringBuilder("select a.id,a.batch_no,a.record_count,a.data_type,a.declare_result,a.declare_result_code,a.gmt_create,a.gmt_modified,b.username as create_id, c.username as modified_id from  DC_DECLARE_RESULT a LEFT JOIN DC_SYS_USER b on a.create_id = b.userid  LEFT JOIN DC_SYS_USER c on a.modified_id = c.userid  where 1=1");
        List<Object> list = new ArrayList<Object>();

        if (StringUtils.isNotBlank(batchNo)) {
            sql.append(" AND batch_no = ? ");
            list.add(batchNo);
        }

        if (!StringUtils.equals("all",transactionType)){
            String messageTypeDesc = MessageTypeEnum.findDescByCode(transactionType);
            sql.append("AND  data_type  = ?");
            list.add(messageTypeDesc);
        }



        if (StringUtils.isNotBlank(startDate)) {
            sql.append(" AND gmt_create >= ? ");
            list.add(startDate);
        }
        if (StringUtils.isNotBlank(endDate) ) {
            sql.append("  AND gmt_create <= ?");
            list.add(endDate);
        }

        pageBean.setSort("id");
        pageBean.setOrder("desc");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, DeclareResult.class);
    }

    /**
     * 通过id查询记录
     *
     * @param id 主键
     * @return
     * @throws DAOException
     */
    @Override
    public DeclareResult findDeclareResultById(String id) throws DAOException {
        String sql = "SELECT * FROM  DC_DECLARE_RESULT WHERE id = ?";
        return super.findForObjectBySql(sql, new Object[]{id}, DeclareResult.class);
    }
}
