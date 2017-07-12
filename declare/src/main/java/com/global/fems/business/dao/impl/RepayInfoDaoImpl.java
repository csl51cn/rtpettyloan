package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.RepayInfoDao;
import com.global.fems.business.domain.RepayInfo;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 贷款回收信息管理dao
 */
@Repository
public class RepayInfoDaoImpl extends BaseDaoSupport implements RepayInfoDao {
    private Logger logger = LoggerFactory.getLogger(RepayInfoDaoImpl.class);

    /**
     * 根据合同编号查询还款信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findRepayInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) throws DAOException {
        String sql = "SELECT  b.Id AS id ,a.合同编号 AS contract_no, a.Date_Id AS date_id, ISNULL(CASE a.授信主体类型 WHEN 1 THEN d.客户名称 WHEN 2 THEN c.中文客户名称 END,'') AS customer_name," +
                "(CASE a.还款方式 WHEN 1835 THEN b.还款期数+1 WHEN 818 THEN b.还款期数+1 ELSE b.还款期数 END) AS counter,b.还款日期 AS repay_date,(CASE b.是否逾期 WHEN 0 THEN b.实还本金 WHEN 1 THEN 0 END) AS repay_pri_amt," +
                "(CASE b.是否逾期 WHEN 0 THEN b.实还利息 WHEN 1 THEN 0 END) AS repay_int_amt,(CASE a.还款方式 WHEN 1835 THEN e.计划还款日 WHEN 818 THEN e.计划还款日 ELSE DATEADD(m, - 1, e.计划还款日) END ) AS start_date," +
                "(CASE a.还款方式 WHEN 1835 THEN DATEADD(d ,-1,DATEADD(m, 1, e.计划还款日)) WHEN 818 THEN DATEADD(d,-1, DATEADD(m, 1, e.计划还款日)) ELSE DATEADD(d, - 1, e.计划还款日) END) AS end_date," +
                "(CASE b.是否逾期 WHEN 0 THEN 550001 WHEN 1 THEN 550002 ELSE 55001 END) AS receipt_type, b.逾期天数 AS delayDays FROM Data_WorkInfo a LEFT JOIN Date_还款登记表 b ON a.Date_Id = b.Date_Id " +
                "LEFT JOIN Data_CompanyInfo c ON a.授信主体编号 = c.Id LEFT JOIN Data_MemberInfo d ON a.授信主体编号 = d.ID LEFT JOIN Date_还款计划表 e ON a.date_id = e.Date_Id WHERE  b.还款计划类别 = 1212 " +
                "AND e.还款计划类别 = 1212 AND b.还款期数 = e.计划期数 AND  还款总额 > 0  AND a.合同编号 = ?  ";
        pageBean.setSort("a.Date_Id");
        return super.findForPage(sql, new Object[]{contractNo}, pageBean, RepayInfo.class);
    }

    /**
     * 根据还款日期查询还款信息
     *
     * @param repayStartDate
     * @param repayEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findRepayInfoByRepayDateFromBizSys(String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException {
        String sql = "SELECT  b.Id AS id , a.合同编号 AS contract_no , a.Date_Id AS date_id, ISNULL(CASE a.授信主体类型 WHEN 1 THEN d.客户名称 WHEN 2 THEN c.中文客户名称 END,'') AS customer_name," +
                "(CASE a.还款方式 WHEN 1835 THEN b.还款期数+1 WHEN 818 THEN b.还款期数+1 ELSE b.还款期数 END) AS counter,b.还款日期 AS repay_date,(CASE b.是否逾期 WHEN 0 THEN b.实还利息 WHEN 1 THEN 0 END) AS repay_int_amt, " +
                " (CASE b.是否逾期 WHEN 0 THEN b.实还本金 WHEN 1 THEN 0 END) AS repay_pri_amt,(CASE a.还款方式 WHEN 1835 THEN e.计划还款日 WHEN 818 THEN e.计划还款日 ELSE DATEADD(m, - 1, e.计划还款日) END ) AS start_date," +
                "(CASE a.还款方式 WHEN 1835 THEN DATEADD(d ,-1,DATEADD(m, 1, e.计划还款日)) WHEN 818 THEN DATEADD(d,-1, DATEADD(m, 1, e.计划还款日)) ELSE DATEADD(d, - 1, e.计划还款日) END) AS end_date," +
                "(CASE b.是否逾期 WHEN 0 THEN 550001 WHEN 1 THEN 550002 ELSE 55001 END) AS receipt_type, b.逾期天数 AS delayDays FROM Data_WorkInfo a LEFT JOIN Date_还款登记表 b ON a.Date_Id = b.Date_Id " +
                "LEFT JOIN Data_CompanyInfo c ON a.授信主体编号 = c.Id LEFT JOIN Data_MemberInfo d ON a.授信主体编号 = d.ID LEFT JOIN Date_还款计划表 e ON a.date_id = e.Date_Id WHERE  b.还款计划类别 = 1212 " +
                "AND e.还款计划类别 = 1212 AND b.还款期数 = e.计划期数 AND  还款总额 > 0  AND b.还款日期 >= ? AND b.还款日期 <= ?";
        pageBean.setSort("a.Date_Id");
        return super.findForPage(sql, new Object[]{repayStartDate, repayEndDate}, pageBean, RepayInfo.class);
    }

    /**
     * 根据Date_还款登记表的id查询还款信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public RepayInfo findRepayInfoByIdFromBizSys(String id) throws DAOException {
        String sql = "SELECT a.Date_Id AS date_id,a.合同编号 AS contract_no,a.合同编号 AS due_bill_no, b.还款日期 AS repay_date,(CASE a.还款方式 WHEN 1835 THEN b.还款期数 + 1 WHEN 818 THEN " +
                " b.还款期数 + 1 ELSE b.还款期数 END ) AS counter,ISNULL(CASE a.授信主体类型 WHEN 1 THEN 480001 WHEN 2 THEN 480002 END,'') AS customer_type,ISNULL(CASE a.授信主体类型 " +
                " WHEN 1 THEN d.客户名称 WHEN 2 THEN c.中文客户名称 END,'') AS customer_name,ISNULL(CASE a.授信主体类型 WHEN 1 THEN 150001 WHEN 2 THEN 150002 END,'') AS certificate_type, " +
                " ISNULL(CASE a.授信主体类型 WHEN 1 THEN d.身份证号码 WHEN 2 THEN c.组织机构代码证号 END,'') AS certificate_no,(CASE b.是否逾期 WHEN 0 THEN b.实还本金 WHEN 1 THEN 0 END ) " +
                " AS repay_pri_amt,(CASE b.是否逾期 WHEN 0 THEN b.实还利息 WHEN 1 THEN 0 END) AS repay_int_amt,b.实还罚息 AS delay_fee,(CASE a.还款方式 WHEN 1835 THEN e.计划还款日 WHEN 818 THEN " +
                " e.计划还款日 ELSE DATEADD(m, - 1, e.计划还款日)END) AS start_date,(CASE a.还款方式 WHEN 1835 THEN DATEADD(d ,-1,DATEADD(m, 1, e.计划还款日))WHEN 818 THEN DATEADD(d ,- 1, " +
                " DATEADD(m, 1, e.计划还款日))ELSE DATEADD(d, - 1, e.计划还款日) END) AS end_date,(CASE b.是否逾期 WHEN 0 THEN 550001 WHEN 1 THEN 550002 ELSE 55001 END) AS receipt_type, " +
                " (CASE b.是否逾期 WHEN 0 THEN 0 WHEN 1 THEN b.实还本金 ELSE 0 END ) AS delay_amt,(CASE b.是否逾期 WHEN 0 THEN 0 WHEN 1 THEN b.实还利息 ELSE 0 END) AS delay_interest,ISNULL( " +
                " CASE a.产品类型名称 WHEN '质房贷' THEN a.利率 * 1.5 ELSE 20 END, NULL) AS pri_plty_rate,b.逾期天数 AS delay_days FROM Date_还款登记表 b LEFT JOIN Data_WorkInfo a ON a.Date_Id = b.Date_Id LEFT JOIN " +
                " Data_CompanyInfo c ON a.授信主体编号 = c.Id LEFT JOIN Data_MemberInfo d ON a.授信主体编号 = d.ID LEFT JOIN Date_还款计划表 e ON a.Date_Id = e.Date_Id WHERE b.还款计划类别 = 1212 " +
                " AND e.还款计划类别 = 1212 AND b.还款期数 = e.计划期数 AND b.Id = ? ";
        return super.findForObjectBySql(sql, new Object[]{id}, RepayInfo.class);
    }

    /**
     * 根据date_id查询还款信息集合
     *
     * @param dateId
     * @param counter
     * @param repayDate
     * @return
     * @throws DAOException
     */
    @Override
    public List<RepayInfo> findRepayInfoListByDateIdAndCounter(Integer dateId, String counter, String repayDate) throws DAOException {
        String sql = "SELECT * FROM DC_REPAY_INFO WHERE date_id = ? AND counter = ? AND repay_date = ?";
        List<RepayInfo> RepayInfoList = (List<RepayInfo>) super.findForListBySql(sql, new Object[]{dateId, counter, repayDate}, RepayInfo.class);
        return RepayInfoList;
    }

    /**
     * 批量保存
     *
     * @param list
     * @throws DAOException
     */
    @Override
    public void batchSaveContract(ArrayList<RepayInfo> list) throws DAOException {
        super.batchInsert(list);
    }

    /**
     * 保存或更新
     *
     * @param repayInfo
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(RepayInfo repayInfo) throws DAOException {
        super.saveOrUpdate(repayInfo);
    }

    /**
     * 批量更新
     *
     * @param list
     * @param isUpdateValueNullField
     */
    @Override
    public void batchUpdateRepayInfo(List<RepayInfo> list, boolean isUpdateValueNullField) {
        super.batchUpdate(list, isUpdateValueNullField);
    }

    /**
     * 根据合同号从DC_REPAY_INFO查询简略的还款信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        String sql = "SELECT id,date_id,contract_no,customer_name,counter,repay_date,repay_pri_amt,repay_int_amt,receipt_type,delay_days,is_last,is_send,report_type FROM DC_REPAY_INFO WHERE contract_no = ?";
        pageBean.setSort("id");
        return super.findForPage(sql, new Object[]{contractNo}, pageBean, RepayInfo.class);
    }

    /**
     * 根据DC_REPAY_INFO主键从DC_REPAY_INFO查询合同信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public RepayInfo findRepayInfoById(String id) throws DAOException {
        String sql = "SELECT * FROM DC_REPAY_INFO WHERE id = ?";
        return super.findForObjectBySql(sql, new Object[]{id}, RepayInfo.class);
    }

    /**
     * 根据还款日期和发送状态查询还款信息
     *
     * @param sendStatusCode
     * @param repayStartDate
     * @param repayEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findRepayInfoBySendStatus(String sendStatusCode, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,contract_no,customer_name,counter,repay_date,repay_pri_amt,repay_int_amt,receipt_type,delay_days,is_last,is_send,report_type FROM DC_REPAY_INFO WHERE 1=1");
        ArrayList list = new ArrayList();
        if (sendStatusCode != null && StringUtils.isNotBlank(sendStatusCode)) {
            sql.append(" AND is_send = ? ");
            list.add(sendStatusCode);
        }

        if (StringUtils.isNotEmpty(repayStartDate) && StringUtils.isNotEmpty(repayEndDate)) {
            sql.append(" AND repay_date >= ? AND repay_date <= ?");
            list.add(repayStartDate);
            list.add(repayEndDate);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, RepayInfo.class);
    }

    /**
     * 查询总期数
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public int findTotalCounter(Integer dateId) throws DAOException {
        String sql = "SELECT CASE b.还款方式 WHEN 1835 THEN MAX (a.计划期数) + 1 WHEN 818 THEN MAX (a.计划期数) + 1 ELSE MAX (a.计划期数) END FROM Date_还款计划表 a LEFT JOIN Data_WorkInfo b ON a.Date_Id = b.Date_Id WHERE a.date_id = ? GROUP BY b.还款方式";
        return super.findForIntBySql(sql, new Object[]{dateId});
    }

    /**
     * 根据发送状态和还款日期查询最新的还款信息
     *
     * @param sendStatusCode
     * @param repayStartDate
     * @param repayEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastRepayInfoSendStatus(String sendStatusCode, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,batch_no,contract_no,customer_name,counter,repay_date,repay_pri_amt,repay_int_amt,receipt_type,delay_days,is_last,is_send,report_type FROM DC_REPAY_INFO WHERE is_last='Y'  AND 1=1");
        ArrayList list = new ArrayList();
        if (sendStatusCode != null && StringUtils.isNotBlank(sendStatusCode)) {
            sql.append(" AND is_send = ? ");
            list.add(sendStatusCode);
        }

        if (StringUtils.isNotEmpty(repayStartDate) && StringUtils.isNotEmpty(repayEndDate)) {
            sql.append(" AND repay_date >= ? AND repay_date <= ?");
            list.add(repayStartDate);
            list.add(repayEndDate);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, RepayInfo.class);
    }

    /**
     * 查询违约金--本息
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public Float findPenaltyPrincipalInterest(Integer dateId) throws DAOException {
        try {
            String sql = "select  ISNULL(Sum(实还费用Two),0) From Date_还款登记表 Where 还款计划类别=1212  AND Date_Id = ?  Group by Date_Id";
            logger.debug("Executing SQL query [{}], params: [{}]", sql, dateId);
            return super.getJdbcTemplate().queryForObject(sql, new Object[]{dateId}, Float.class);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("RepayInfoDaoImpl:findPenaltyPrincipalInterest() " + e.getLocalizedMessage());
            return 0F;
        }
    }

    /**
     * 查询违约金--服务费
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public Float findPenaltyServiceFee(Integer dateId) throws DAOException {
        try {
            String sql = "select ISNULL(Sum(实还费用Two),0) From Date_还款登记表 Where 还款计划类别=1214  AND Date_Id = ?  Group by Date_Id";
            logger.debug("Executing SQL query [{}], params: [{}]", sql, dateId);
            return super.getJdbcTemplate().queryForObject(sql, new Object[]{dateId}, Float.class);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("RepayInfoDaoImpl:findPenaltyServiceFee() " + e.getLocalizedMessage());
            return 0F;
        }
    }

    /**
     * 根据dateId 和期数从Date_还款登记表查询实还本金和实还利息,将同期
     *
     * @param dateId
     * @param counter
     * @param repayDate
     * @return
     * @throws DAOException
     */
    @Override
    public Map findRepayPrincipalInterest(Integer dateId, String counter, String repayDate) throws DAOException {
        String sql = "SELECT SUM (实还本金) AS repay_pri_amt,SUM (实还利息) AS repay_int_amt FROM Date_还款登记表 WHERE 还款计划类别 = 1212 AND Date_Id = ? AND 还款期数 = ? AND 还款日期 = ? ";
        Map<String, Object> map = super.getJdbcTemplate().queryForMap(sql, new Object[]{dateId, counter, repayDate});
        return map;
    }


}