package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.QuotaInfoDao;
import com.global.fems.business.domain.QuotaInfo;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 授信额度信息管理Dao接口实现类
 *
 * @author senlin.deng
 * @date 2018/4/3 16:36
 */
@Repository
public class QuotaInfoDaoImpl extends BaseDaoSupport implements QuotaInfoDao {


    @Override
    public PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) {
        String sql = "SELECT " +
                " a.Date_Id, " +
                " a.合同编号 as contract_no_query, " +
                " e.real_quota_no as contract_no, " +
                " ISNULL( " +
                "  CASE a.授信主体类型 " +
                "  WHEN 1 THEN " +
                "   d.客户名称 " +
                "  WHEN 2 THEN " +
                "   c.中文客户名称 " +
                "  END, " +
                "  '' " +
                " ) AS customer_name, " +
                " a.循环授信额度 as contract_amount, " +
                " a.授信金额 as used_amount, " +
                " (a.循环授信额度 - a.授信金额) as remain_amount, " +
                " b.content as contract_sign_date, " +
                " b.content as contract_begin_date, " +
                " DATEADD(m, a.循环授信期限, b.content) as contract_end_date, " +
                " '740001' as is_circle " +
                "FROM " +
                " Data_WorkInfo a " +
                "LEFT JOIN WorkData_Date b ON b.date_id = a.Date_Id " +
                "AND b.Flow_NO IN ( " +
                " SELECT " +
                "  Flow_No " +
                " FROM " +
                "  WorkFlowConstruction " +
                " WHERE " +
                "  Flow_Title = '现场签约' " +
                ") " +
                "AND b.GoBackId = 0 " +
                "AND b.form_arrno = 24 " +
                "LEFT JOIN Data_CompanyInfo c ON a.授信主体编号 = c.Id " +
                "LEFT JOIN Data_MemberInfo d ON a.授信主体编号 = d.ID " +
                "LEFT JOIN DC_PETTY_LOAN_CONTRACT e on e.dateid = a.Date_Id and e.islast='Y' " +
                "WHERE " +
                " a.是否为循环授信贷款 = 870 and " +
                " a.合同编号 = ? ";

        pageBean.setSort("a.id");
        return super.findForPage(sql, new Object[]{contractNo}, pageBean, QuotaInfo.class);

    }

    @Override
    public QuotaInfo findQuotaInfoByDateId(String dateId) throws DAOException {
        String sql = "SELECT DISTINCT " +
                " a.Date_Id, " +
                " a.合同编号 AS contract_no_query, " +
                " f.real_quota_no AS contract_no, " +
                " ISNULL( " +
                "  CASE a.授信主体类型 " +
                "  WHEN 1 THEN " +
                "   480001 " +
                "  WHEN 2 THEN " +
                "   480002 " +
                "  END, " +
                "  '' " +
                " ) AS customer_type, " +
                " ISNULL( " +
                "  CASE a.授信主体类型 " +
                "  WHEN 1 THEN " +
                "   d.客户名称 " +
                "  WHEN 2 THEN " +
                "   c.中文客户名称 " +
                "  END, " +
                "  '' " +
                " ) AS customer_name, " +
                " ISNULL( " +
                "  CASE a.授信主体类型 " +
                "  WHEN 1 THEN " +
                "   150001 " +
                "  WHEN 2 THEN " +
                "   150002 " +
                "  END, " +
                "  '' " +
                " ) AS certificate_type, " +
                " Upper(ISNULL(  " +
                "    CASE a.授信主体类型  " +
                "    WHEN 1 THEN  " +
                "     d.身份证号码  " +
                "    WHEN 2 THEN  " +
                "      c.组织机构代码证号  " +
                "    END,  " +
                "    ''  " +
                "  )) AS certificate_no,  " +
                " ISNULL( " +
                " " +
                "  CASE WHEN e.num > 0 or a.产品类别=1760 or a.产品类别=2066 THEN " +
                "   '240002' " +
                "  ELSE " +
                "  CASE WHEN (a.普通担保人 IS NULL " +
                "   OR len(a.普通担保人) = 0 " +
                "  ) THEN " +
                "   '240001' " +
                "  ELSE " +
                "   '240004' " +
                "  END " +
                "  END, " +
                "  '' " +
                " ) AS guar_type, " +
                " a.循环授信额度 AS contract_amount, " +
                " a.授信金额 AS used_amount, " +
                " ( " +
                "  a.循环授信额度 - a.授信金额 " +
                " ) AS remain_amount, " +
                " b.content AS contract_sign_date, " +
                " b.content AS contract_begin_date, " +
                " DATEADD( " +
                "  m, " +
                "  a.循环授信期限, " +
                "  b.content " +
                " ) AS contract_end_date, " +
                " '740001' AS is_circle " +
                "FROM " +
                " Data_WorkInfo a " +
                "LEFT JOIN WorkData_Date b ON b.date_id = a.Date_Id " +
                "AND b.Flow_NO IN ( " +
                " SELECT " +
                "  Flow_No " +
                " FROM " +
                "  WorkFlowConstruction " +
                " WHERE " +
                "  Flow_Title = '现场签约' " +
                ") " +
                "AND b.GoBackId = 0 " +
                "AND b.form_arrno = 24 " +
                "LEFT JOIN Data_CompanyInfo c ON a.授信主体编号 = c.Id " +
                "LEFT JOIN Data_MemberInfo d ON a.授信主体编号 = d.ID " +
                "LEFT JOIN ( " +
                " SELECT " +
                "  date_id, " +
                "  COUNT (*) AS num " +
                " FROM " +
                "  app_V抵押房产清单 " +
                " GROUP BY " +
                "  date_id " +
                ") AS e ON e.date_id = a.date_id " +
                "LEFT JOIN DC_PETTY_LOAN_CONTRACT f on f.dateid = a.Date_Id and f.islast='Y' " +
                "WHERE " +
                " a.Date_Id = ?";
        return super.findForObjectBySql(sql, new Object[]{dateId}, QuotaInfo.class);
    }

    @Override
    public List<QuotaInfo> findQuotaoInfoListByDateId(String dateId) throws DAOException {
        String sql = "select * from DC_QUOTA_INFO where date_id = ?";
        return (List<QuotaInfo>) super.findForListBySql(sql, new Object[]{dateId}, QuotaInfo.class);

    }

    @Override
    public void batchUpdateQuotaoInfo(List<QuotaInfo> list, boolean isUpdateValueNullField) throws DAOException {
        super.batchUpdate(list, isUpdateValueNullField);
    }

    @Override
    public void saveOrUpdate(QuotaInfo quotaInfo) throws DAOException {
        super.saveOrUpdate(quotaInfo);
    }

    @Override
    public void batchSaveQuotaInfo(List<QuotaInfo> list) throws DAOException {
        super.batchInsert(list);
    }

    @Override
    public PageBean findQuotaInfoByDateFromBizSys(String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT " +
                " a.Date_Id, " +
                " a.合同编号 as contract_no_query, " +
                " e.real_quota_no as contract_no, " +
                " ISNULL( " +
                "  CASE a.授信主体类型 " +
                "  WHEN 1 THEN " +
                "   d.客户名称 " +
                "  WHEN 2 THEN " +
                "   c.中文客户名称 " +
                "  END, " +
                "  '' " +
                " ) AS customer_name, " +
                " a.循环授信额度 as contract_amount, " +
                " a.授信金额 as used_amount, " +
                " (a.循环授信额度 - a.授信金额) as remain_amount, " +
                " b.content as contract_sign_date, " +
                " b.content as contract_begin_date, " +
                " DATEADD(m, a.循环授信期限, b.content) as contract_end_date, " +
                " '740001' as is_circle " +
                "FROM " +
                " Data_WorkInfo a " +
                "LEFT JOIN WorkData_Date b ON b.date_id = a.Date_Id " +
                "AND b.Flow_NO IN ( " +
                " SELECT " +
                "  Flow_No " +
                " FROM " +
                "  WorkFlowConstruction " +
                " WHERE " +
                "  Flow_Title = '现场签约' " +
                ") " +
                "AND b.GoBackId = 0 " +
                "AND b.form_arrno = 24 " +
                "LEFT JOIN Data_CompanyInfo c ON a.授信主体编号 = c.Id " +
                "LEFT JOIN Data_MemberInfo d ON a.授信主体编号 = d.ID " +
                "LEFT JOIN DC_PETTY_LOAN_CONTRACT e on e.dateid = a.Date_Id and e.islast='Y' " +
                "WHERE " +
                " a.是否为循环授信贷款 = 870  " +
                " AND  a.是否放款=485  ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(signStartDate) && StringUtils.isNotEmpty(signEndDate)) {
            sql.append(" AND b.content >= ? AND b.content <= ?");
            list.add(signStartDate);
            signEndDate = signEndDate + " 23:59:59";
            list.add(signEndDate);
        }
        pageBean.setSort("a.id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, QuotaInfo.class);
    }

    @Override
    public PageBean findQuotaBriefInfoByContractNo(String contractNoQuery, PageBean pageBean) throws DAOException {
        String sql = "SELECT id, date_id,contract_no,contract_no_query,customer_name,contract_amount,contract_begin_date,contract_end_date," +
                "contract_sign_date,used_amount,remain_amount,is_send,is_last,report_type FROM DC_QUOTA_INFO WHERE contract_no_query =? ";
        pageBean.setSort("id");
        return super.findForPage(sql, new Object[]{contractNoQuery}, pageBean, QuotaInfo.class);
    }

    @Override
    public PageBean findQuotaInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id, date_id,contract_no,contract_no_query,customer_name,contract_amount,contract_begin_date,contract_end_date," +
                "contract_sign_date,used_amount,remain_amount,is_send,is_last,report_type FROM DC_QUOTA_INFO WHERE  1 = 1 ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotBlank(sendStatusCode)) {
            sql.append(" AND  is_send = ? ");
            list.add(sendStatusCode);
        }
        if (StringUtils.isNotEmpty(signStartDate) && StringUtils.isNotEmpty(signEndDate)) {
            sql.append(" AND contract_sign_date >= ? AND contract_sign_date <= ?");
            list.add(signStartDate);
            list.add(signEndDate);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, QuotaInfo.class);
    }

    @Override
    public QuotaInfo findQuotaInfoById(String id) throws DAOException {
        String sql = "Select * from DC_QUOTA_INFO where id = ? ";
        return super.findForObjectBySql(sql, new Object[]{id}, QuotaInfo.class);
    }

    @Override
    public List<QuotaInfo> findQuotaInfoListByDateId(String dateId) throws DAOException {
        String sql = "select * from DC_QUOTA_INFO where date_id = ?";
        List<QuotaInfo> quotaInfoList = (List<QuotaInfo>) super.findForListBySql(sql, new Object[]{dateId}, QuotaInfo.class);
        return quotaInfoList;
    }

    @Override
    public PageBean findLastQuotaInfoBySendStatus(String sendStatusCode, String startDate, String endDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id, date_id,contract_no,contract_no_query,customer_name,contract_amount,contract_begin_date,contract_end_date," +
                "contract_sign_date,used_amount,remain_amount,is_send,is_last,report_type,batch_no FROM DC_QUOTA_INFO WHERE  1 = 1 ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotBlank(sendStatusCode)) {
            sql.append(" AND is_send = ? ");
            list.add(sendStatusCode);
        }

        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND contract_sign_date >= ? AND contract_sign_date <= ?");
            list.add(startDate);
            list.add(endDate);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, QuotaInfo.class);
    }

    /**
     * 根据batchNo查询记录
     *
     * @param batchNo 批次号
     * @return
     */
    @Override
    public List<QuotaInfo> findByBatchNo(String batchNo) {
        String sql = "select * from DC_QUOTA_INFO where batch_no = ?";
        return (List<QuotaInfo>) super.findForListBySql(sql, new Object[]{batchNo}, QuotaInfo.class);

    }

    /**
     * 根据交易类型和上报结果查询记录数
     *
     * @param dateId     dateId
     * @param reportType 交易类型
     * @param result     上报结果
     * @return 满足条件的记录数
     */
    @Override
    public Long findCountByDateIdAndReportTypeAndResult(String dateId, String reportType, String result) {
        String sql = "SELECT COUNT(*) FROM DC_QUOTA_INFO a LEFT JOIN DC_DECLARE_RESULT b ON a.batch_no = b.batch_no WHERE  a.date_id =? and a.report_type = ? AND b.declare_result = ? ";
        return super.findForLongBySql(sql, new Object[]{dateId, reportType, result});
    }

}
