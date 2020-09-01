package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.ContractIssueInfoDao;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.domain.ContractIssueInfo;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款放款信息管理Dao接口实现类
 */
@Repository
public class ContractIssueInfoDaoImpl extends BaseDaoSupport implements ContractIssueInfoDao {

    /**
     * 根据合同编号和申报状态从DC_CONTRACT_INFO中查询合同信息
     *
     * @param contractNo
     * @param sendStatus
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findContractByContractNoFromContractInfo(String contractNo, String sendStatus, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,contract_no,customer_name,contract_amount,contract_sign_date,net_sign_no,is_last,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_INFO WHERE is_last = 'Y' AND contract_no = ? ");
        List<Object> list = new ArrayList<Object>();
        list.add(contractNo);
        if (sendStatus != null && StringUtils.isNotBlank(sendStatus)) {
            sql.append(" AND is_send = ? ");
            list.add(sendStatus);
        }
        pageBean.setSort("id");
        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, ContractInfoCycleNode.class);
        return forPage;
    }

    /**
     * 根据dateId查询贷款放款信息
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public ContractIssueInfo findContractByDateId(String dateId) throws DAOException {
        String sql = "SELECT a.contract_no,b.合同编号 AS dueBillNo, a.date_id,a.net_sign_no,a.customer_type,a.customer_name,a.certificate_type,a.certificate_no,b.授信金额 AS dd_amt, " +
                "a.loan_cate,a.int_rate,a.pri_plty_rate,a.contract_sign_date AS sign_date,  convert(nvarchar ,b.放款日期,121)+' 00:00:00.000' AS dd_date,  e.到期日期 AS mature_date," +
                "a.guar_type,a.loan_object,a.loan_object_size,b.认定区域编号 AS [zone],ISNULL(CASE a.loan_object_size WHEN '280002' THEN '260001' WHEN '280003' THEN '260002' ELSE NULL END,'') AS purpose, " +
                "b.还款方式 AS rate_calc_mode,ISNULL (CASE b.授信主体类型 WHEN 1 THEN d.[行业类别] WHEN 2 THEN c.[行业分类] END,'')AS industry, (CASE  b.是否为循环授信贷款   WHEN  870 THEN   '740001'   ELSE  '740002' END ) AS is_real_quota_loan,  ISNULL(a.real_quota_no, '') as real_quota_no FROM " +
                "DC_CONTRACT_INFO a LEFT JOIN Data_WorkInfo b ON a.date_id = b.Date_Id  LEFT JOIN Data_CompanyInfo c ON b.授信主体编号 = c.ID " +
                "LEFT JOIN Data_MemberInfo d ON b.授信主体编号 = d.ID left join ( Select Date_Id, Max(计划还款日) As 到期日期 From Date_还款计划表 Group By Date_Id) e on a.date_id = e.date_id WHERE a.is_last ='Y' AND a.date_id = ? ";
        return super.findForObjectBySql(sql, new Object[]{dateId}, ContractIssueInfo.class);

    }

    /**
     * 批量保存
     *
     * @param list
     * @throws DAOException
     */
    @Override
    public void batchSaveContract(List<ContractIssueInfo> list) throws DAOException {
        super.batchInsert(list);
    }

    /**
     * 根据dateId查询贷款放款信息集合
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public List<ContractIssueInfo> findContractListByDateId(String dateId) throws DAOException {
        String sql = "SELECT * FROM  DC_CONTRACT_ISSUE_INFO WHERE date_id = ?";
        List<ContractIssueInfo> contractIssueInfoList = (List<ContractIssueInfo>) super.findForListBySql(sql, new Object[]{dateId}, ContractIssueInfo.class);
        return contractIssueInfoList;
    }

    /**
     * 保存或更新贷款放款信息
     *
     * @param contractIssueInfo
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(ContractIssueInfo contractIssueInfo) throws DAOException {
        super.saveOrUpdate(contractIssueInfo);
    }

    /**
     * 根据申报状态查询和签约日期查询贷款放款信息
     *
     * @param sendStatus
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractIssueInfoBySendStatus(String sendStatus, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,due_bill_no,contract_no,customer_name,dd_amt,sign_date,loan_cate,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_ISSUE_INFO WHERE 1 = 1 ");
        List<Object> list = new ArrayList<Object>();
        if (sendStatus != null && StringUtils.isNotBlank(sendStatus)) {
            sql.append(" AND is_send = ? ");
            list.add(sendStatus);
        }

        if (StringUtils.isNotEmpty(signStartDate) && StringUtils.isNotEmpty(signEndDate)) {
            sql.append(" AND sign_date >= ? AND sign_date <= ?");
            list.add(signStartDate);
            list.add(signEndDate);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, ContractIssueInfo.class);

    }

    /**
     * 根据id查询贷款合同信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public ContractIssueInfo findContractIssueInfoById(String id) throws DAOException {
        String sql = "SELECT * FROM DC_CONTRACT_ISSUE_INFO where id = ?";
        return super.findForObjectBySql(sql, new Object[]{id}, ContractIssueInfo.class);
    }

    /**
     * 根据id查询贷款合同信息集合
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public List<ContractIssueInfo> findContractIssueInfoListByDateId(String dateId) throws DAOException {
        String sql = "SELECT * FROM DC_CONTRACT_ISSUE_INFO WHERE date_id = ?";
        return (List<ContractIssueInfo>) super.findForListBySql(sql, new Object[]{dateId}, ContractIssueInfo.class);
    }

    /**
     * 批量更新记录
     *
     * @param list
     * @param isUpdateValueNullField
     */
    @Override
    public void batchUpdateContract(List<ContractIssueInfo> list, boolean isUpdateValueNullField) throws DAOException {
        super.batchUpdate(list, isUpdateValueNullField);
    }

    /**
     * 根据合同号从DC_CONTRACT_ISSUE_INFO查询贷款放款信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        String sql = "SELECT id,date_id,due_bill_no,contract_no,customer_name,dd_amt,sign_date,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_ISSUE_INFO WHERE contract_no = ?";
        pageBean.setSort("id");
        return super.findForPage(sql, new Object[]{contractNo}, pageBean, ContractIssueInfo.class);
    }

    /**
     * 根据申报状态查询和签约日期查询贷款放款信息,申报时使用.
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,batch_no,due_bill_no,contract_no,customer_name,dd_amt AS contract_amount,sign_date AS contract_sign_date,loan_cate,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_ISSUE_INFO WHERE  1 = 1 ");
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
        return super.findForPage(sql.toString(), list.toArray(), pageBean, ContractInfoCycleNode.class);
    }

    /**
     * 根据申报状态查询和签约日期查询最新的贷款放款信息,还款计划使用
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
    public PageBean findLastContractBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id,date_id,due_bill_no,contract_no,customer_name,dd_amt AS contract_amount,sign_date AS contract_sign_date,loan_cate,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no,batch_no FROM DC_CONTRACT_ISSUE_INFO WHERE  1 = 1 AND is_last = 'Y' ");
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
            sql.append(" AND sign_date <= ? ");
            list.add(signEndDate);
        }

        if (StringUtils.isNotEmpty(contractNo)) {
            sql.append(" AND contract_no= ? ");
            list.add(contractNo.trim());
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, ContractInfoCycleNode.class);
    }

    /**
     * 根据batchNo查询记录
     *
     * @param batchNo 批次号
     * @return
     */
    @Override
    public List<ContractIssueInfo> findByBatchNo(String batchNo) {
        String sql = "SELECT * FROM DC_CONTRACT_ISSUE_INFO where batch_no = ?";
        return (List<ContractIssueInfo>) super.findForListBySql(sql, new Object[]{batchNo}, ContractIssueInfo.class);
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
        String sql = "SELECT COUNT(*) FROM DC_CONTRACT_ISSUE_INFO a LEFT JOIN DC_DECLARE_RESULT b ON a.batch_no = b.batch_no WHERE  a.date_id =? and a.report_type = ? AND b.declare_result = ? ";
        return super.findForLongBySql(sql, new Object[]{dateId, reportType, result});
    }
}
