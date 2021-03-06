package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.PayPlanInfoDao;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.domain.PayPlanInfo;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 还款计划信息管理dao接口实现类
 */
@Repository
public class PayPlanInfoDaoImpl extends BaseDaoSupport implements PayPlanInfoDao {

    /**
     * 根据dateId查询还款计划
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public List<PayPlanInfo> findPayPlanInfoListByDateId(String dateId) throws DAOException {
        String sql = "SELECT * FROM DC_PAYPLAN_INFO WHERE date_id =  ?";
        return (List<PayPlanInfo>) super.findForListBySql(sql, new Object[]{dateId}, PayPlanInfo.class);
    }

    /**
     * 根据DateId从还款计划表查询还款计划信息
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public List<PayPlanInfo> findPayPlanInfoListByDateIdFromBizSys(String dateId) throws DAOException {
        String sql = "SELECT a.date_id,a.sign_date,a.contract_no,a.due_bill_no,(CASE c.还款方式 WHEN 1835 THEN  b.计划期数 + 1 WHEN 818 THEN b.计划期数 + 1 ELSE b.计划期数 END )AS counter,b.计划还款日 AS repay_date,b.计划应还本金 AS repay_pri_amt,b.计划应还利息 AS repay_int_amt,(CASE c.还款方式  WHEN 1835 THEN  b.计划还款日 WHEN 818 THEN b.计划还款日 WHEN 820 THEN(CASE dic.Word WHEN '付易贷' THEN (CASE d.Word WHEN '日' THEN DATEADD(d, - 1, b.计划还款日) WHEN '周' THEN DATEADD(ww, - 1, b.计划还款日) WHEN '月' THEN DATEADD(m, - 1, b.计划还款日) END )ELSE DATEADD(m, - 1, b.计划还款日)END) " +
                " ELSE DATEADD(m, -1, b.计划还款日) END ) AS start_date,( CASE c.还款方式 WHEN 1835 THEN  DATEADD(d,-1,DATEADD(m, 1, b.计划还款日)) WHEN 818 THEN DATEADD( d,-1,DATEADD(m,1,b.计划还款日)) ELSE DATEADD(d, - 1, b.计划还款日) END  ) AS end_date ,  (CASE  c.是否为循环授信贷款   WHEN  870 THEN   '740001'   ELSE  '740002' END ) AS is_real_quota_loan,  ISNULL(c.循环授信合同编号, '') as real_quota_no FROM DC_CONTRACT_ISSUE_INFO a LEFT JOIN Date_还款计划表 b ON a.date_id = b.Date_Id LEFT JOIN Data_WorkInfo c ON a.date_id = c.Date_Id  LEFT JOIN Dictionary d ON c.授信期限单位 = d.Id Left Join Dictionary As dic On c.产品类别 = dic.Id  WHERE a.is_last ='Y' and  b.还款计划类别 = 1212 AND a.date_id = ? ORDER BY counter ";
        return (List<PayPlanInfo>) super.findForListBySql(sql, new Object[]{dateId}, PayPlanInfo.class);
    }

    /**
     * 批量保存
     *
     * @param list
     * @throws DAOException
     */
    @Override
    public void batchSaveContract(List<PayPlanInfo> list) throws DAOException {
        super.batchInsert(list);
    }

    /**
     * 从DC_CONTRACT_ISSUE_INFO表中根据合同编号查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,due_bill_no,contract_no,customer_name,dd_amt AS contract_amount,sign_date AS contract_sign_date,loan_cate,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_ISSUE_INFO WHERE is_last = 'Y' AND contract_no = ? ");
        pageBean.setSort("id");
        PageBean forPage = super.findForPage(sql.toString(), new Object[]{contractNo}, pageBean, ContractInfoCycleNode.class);
        return forPage;
    }

    /**
     * 从DC_PAYPLAN_INFO表中根据签约时间和发送状态查询还款计划信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findPayPlanInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,due_bill_no,contract_no,counter,total_counter,repay_date,repay_pri_amt,repay_int_amt,start_date,end_date,is_send,is_last,report_type,is_real_quota_loan,real_quota_no FROM DC_PAYPLAN_INFO WHERE 1 = 1 ");
        List<Object> list = new ArrayList<Object>();
        if (sendStatusCode != null && StringUtils.isNotBlank(sendStatusCode)) {
            sql.append(" AND is_send = ? ");
            list.add(sendStatusCode);
        }

        if (StringUtils.isNotEmpty(signStartDate) && StringUtils.isNotEmpty(signEndDate)) {
            sql.append(" AND sign_date >= ? AND sign_date <= ?");
            list.add(signStartDate);
            list.add(signEndDate);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, PayPlanInfo.class);

    }

    /**
     * 批量更新
     *
     * @param list
     * @param isUpdateValueNullField
     * @throws DAOException
     */
    @Override
    public void batchUpdateContract(List<PayPlanInfo> list, boolean isUpdateValueNullField) throws DAOException {
        super.batchUpdate(list, isUpdateValueNullField);
    }

    /**
     * 保存或更新记录
     *
     * @param payPlanInfo
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(PayPlanInfo payPlanInfo) throws DAOException {
        super.saveOrUpdate(payPlanInfo);
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public PayPlanInfo findPayPlanInfoById(String id) throws DAOException {
        String sql = "SELECT * FROM  DC_PAYPLAN_INFO WHERE id = ?";
        return super.findForObjectBySql(sql, new Object[]{id}, PayPlanInfo.class);
    }

    /**
     * 根据dateId和期数查询记录
     *
     * @param dateId
     * @param counter
     * @return
     * @throws DAOException
     */
    @Override
    public List<PayPlanInfo> findPayPlanInfoListByDateIdAndCounter(Integer dateId, String counter) throws DAOException {
        String sql = "SELECT * FROM  DC_PAYPLAN_INFO WHERE date_id = ? AND counter = ?";
        return (List<PayPlanInfo>) super.findForListBySql(sql, new Object[]{dateId, counter}, PayPlanInfo.class);
    }

    /**
     * 从DC_PAYPLAN_INFO表中根据签约时间和发送状态查询最新还款计划信息,申报时使用
     *
     * @param sendStatusCode
     * @param contractNo
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastPayPlanInfoBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,batch_no,due_bill_no,contract_no,counter,total_counter,repay_date,repay_pri_amt,repay_int_amt,start_date,end_date,is_send,is_last,report_type,is_real_quota_loan,real_quota_no FROM DC_PAYPLAN_INFO WHERE is_last = 'Y' ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotBlank(sendStatusCode)) {
            sql.append(" AND is_send = ? ");
            list.add(sendStatusCode);
        }

        if (StringUtils.isNotEmpty(signStartDate)) {
            sql.append(" AND sign_date >= ? ");
            list.add(signStartDate);
        }
        if (StringUtils.isNotEmpty(signEndDate)) {
            sql.append("  AND sign_date <= ?");
            list.add(signEndDate);
        }
        if (StringUtils.isNotEmpty(contractNo)) {
            sql.append("  AND contract_no = ?");
            list.add(contractNo);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, PayPlanInfo.class);

    }

    /**
     * 根据合同编号从contractNo查询还款计划信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findPayPlanBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        String sql = "SELECT id,date_id,due_bill_no,contract_no,counter,total_counter,repay_date,repay_pri_amt,repay_int_amt,start_date,end_date,is_send,is_last,report_type,is_real_quota_loan,real_quota_no  FROM DC_PAYPLAN_INFO  WHERE contract_no = ?";
        pageBean.setSort("id");
        return super.findForPage(sql, new Object[]{contractNo}, pageBean, PayPlanInfo.class);
    }

    /**
     * 根据batchNo查询记录
     *
     * @param batchNo 批次号
     * @return
     */
    @Override
    public List<PayPlanInfo> findByBatchNo(String batchNo) {
        String sql = "SELECT * FROM  DC_PAYPLAN_INFO WHERE batch_no = ?  ";
        return (List<PayPlanInfo>) super.findForListBySql(sql, new Object[]{batchNo}, PayPlanInfo.class);
    }

    /**
     * 根据date_id,期数,交易类型和上报结果查询记录数
     *
     * @param dateId     dateId
     * @param counter    期数
     * @param reportType 交易类型
     * @param result     上报结果
     * @return 满足条件的记录数
     */
    @Override
    public Long findCountByDateIdAndCounterAndReportTypeAndResult(String dateId, String counter, String reportType, String result) {
        String sql = "SELECT COUNT(*) FROM DC_PAYPLAN_INFO a LEFT JOIN DC_DECLARE_RESULT b ON a.batch_no = b.batch_no WHERE  a.date_id =?  and  a.counter =?  and a.report_type = ? AND b.declare_result = ? ";
        return super.findForLongBySql(sql, new Object[]{dateId, counter, reportType, result});
    }

}
