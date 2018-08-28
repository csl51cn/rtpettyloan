package com.global.fems.business.dao.impl;


import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.domain.CoCustomerCycleNode;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款合同信息管理Dao接口实现类
 */
@Repository
public class ContractInfoDaoImpl extends BaseDaoSupport implements ContractInfoDao {

    /**
     * 保存或更新合同信息
     *
     * @param contractInfoCycleNode
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws DAOException {
        super.saveOrUpdate(contractInfoCycleNode);
    }

    /**
     * 根据dateId查询合同信息集合
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public List<ContractInfoCycleNode> findContractListByDateId(String dateId) throws DAOException {
        String sql = "select * from DC_CONTRACT_INFO where date_id = ?";
        List<ContractInfoCycleNode> contractInfoCycleNodeList = (List<ContractInfoCycleNode>) super.findForListBySql(sql, new Object[]{dateId}, ContractInfoCycleNode.class);
        return contractInfoCycleNodeList;
    }

    /**
     * 根据dateId联合查询合同信息
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public ContractInfoCycleNode findContractByDateIdFromBizSys(String dateId) throws DAOException {
        String sql = "SELECT " +
                " c.Date_Id AS date_id, " +
                " a.contractno AS contract_no, " +
                " a.loancate AS loan_cate, " +
                " a.customername AS customer_name, " +
                " a.customertype AS customer_type, " +
                " a.certificatetype AS certificate_type, " +
                " a.certificateno AS certificate_no, " +
                " a.concustomertype AS con_customer_type, " +
                " a.concustomername AS con_customer_name, " +
                " a.concertificatetype AS con_certificate_type, " +
                " a.concertificateno AS  con_certificate_no, " +
                " a.confee AS con_fee, " +
                " a.netsignno AS net_sign_no, " +
                " ISNULL( " +
                "  CASE WHEN d.num > 0 then " +
                "   '240002' " +
                "  else  case when (c.普通担保人 is null or len(c.普通担保人) =0) then"+
                "    '240001'  "+
                "  else " +
                "      '240004'"+
                "  END"+
                "  END, " +
                "  '' " +
                " ) AS guar_type, " +
                " ISNULL( " +
                "  CASE c.授信主体类型 " +
                "  WHEN 1 THEN " +
                "   '410001' " +
                "  WHEN 2 THEN " +
                "   '410004' " +
                "  END, " +
                "  '' " +
                " ) AS loan_object, " +
                " ISNULL( " +
                "  CASE dic.Word " +
                "  WHEN '商易贷' THEN " +
                "   '280002' " +
                "  WHEN '优易贷' THEN " +
                "   '280002' " +
                "  WHEN '展业贷' THEN " +
                "   '280002' " +
                "  WHEN '付易贷' THEN " +
                "   '280002' " +
                "  ELSE  " +
                "   '280003' " +
                "  END, " +
                "  '' " +
                " ) AS loan_object_size, " +
                " a.contractsigndate AS contract_sign_date, " +
                " (case c.是否为循环授信贷款  when 870 then a.contractsigndate else   c.放款日期 end)AS contract_begin_date, " +
                " (case c.是否为循环授信贷款  when 870 then DATEADD(m, c.循环授信期限, a.contractsigndate) else b.到期日期 end) AS contract_end_date, " +
                " a.contractamount AS contract_amount, " +
                " b.余额 AS outstanding, " +
                " a.intrate AS int_rate, " +
                " ISNULL( " +
                "  CASE dic.Word " +
                "  WHEN '质房贷' THEN " +
                "   a.intrate * 1.5 " +
                "  WHEN '付易贷' THEN " +
                "   a.intrate * 1.5 " +
                "  WHEN '车盈贷A' THEN " +
                "   a.intrate * 1.5 " +
                "  WHEN '车盈贷B' THEN " +
                "   a.intrate * 1.5 " +
                "  WHEN '车盈贷C' THEN " +
                "   a.intrate * 1.5 " +
                "  ELSE " +
                "   20 " +
                "  END, " +
                "  NULL " +
                " ) AS pri_plty_rate, " +
                " c.共同借款人详细 AS co_customer_name1, " +
                " (CASE  c.是否为循环授信贷款 " +
                " WHEN  870 THEN " +
                "   '740001' " +
                " ELSE  " +
                "   '740002' " +
                " END ) AS is_real_quota_loan, " +
                " ISNULL(c.循环授信合同编号, '') as real_quota_no " +
                "FROM " +
                " DC_PETTY_LOAN_CONTRACT a " +
                "LEFT JOIN 已放款客户表 b ON a.dateid = b.Date_Id " +
                "LEFT JOIN Data_WorkInfo c ON a.dateid = c.Date_Id " +
                "Left Join Dictionary As dic On c.产品类别 = dic.Id " +
                "Left Join (select date_id,count(*) as num from app_V抵押房产清单 group by date_id ) As d On d.date_id = a.dateid " +
                "WHERE " +
                "a.islast = 'Y' AND a.dateid = ?";

        return super.findForObjectBySql(sql, new Object[]{dateId}, ContractInfoCycleNode.class);
    }

    /**
     * 根据身份证号查询共借人信息
     *
     * @param code
     * @return
     * @throws DAOException
     */
    @Override
    public CoCustomerCycleNode findCoCustomerInfoFromDataMemberInfo(String code) throws DAOException {
        String sql = "SELECT 客户名称 as coCustomerName,手机 AS coTelephone FROM Data_MemberInfo where 身份证号码 = ?";
        return super.findForObjectBySql(sql, new Object[]{code}, CoCustomerCycleNode.class);
    }

    /**
     * 根据组织机构代码查询共借人信息
     *
     * @param code
     * @return
     * @throws DAOException
     */
    @Override
    public CoCustomerCycleNode findCoCustomerInfoFromDataCompanyInfo(String code) throws DAOException {
        String sql = "SELECT 中文客户名称 as coCustomerName,公司联系电话 AS coTelephone FROM Data_CompanyInfo where 组织机构代码证号 = ?";
        return super.findForObjectBySql(sql, new Object[]{code}, CoCustomerCycleNode.class);
    }

    /**
     * 批量保存合同信息
     *
     * @param list
     * @throws DAOException
     */
    @Override
    public void batchSaveContract(List<ContractInfoCycleNode> list) throws DAOException {
        super.batchInsert(list);
    }

    /**
     * 根据合同编号从DC_CONTRACT_INFO中查询合同简略信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        String sql = "SELECT id,date_id,contract_no,customer_name,contract_amount,contract_sign_date,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_INFO WHERE contract_no = ?";
        pageBean.setSort("id");
        return super.findForPage(sql, new Object[]{contractNo}, pageBean, ContractInfoCycleNode.class);

    }

    /**
     * 根据申报状态和签约时间段查询合同信息
     *
     * @param sendStatus
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBySendStatus(String sendStatus, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("select id,date_id,batch_no,contract_no,customer_name,contract_amount,contract_sign_date,loan_cate,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_INFO WHERE 1 = 1 ");
        List<Object> list = new ArrayList<Object>();
        if (sendStatus != null && StringUtils.isNotBlank(sendStatus)) {

            sql.append(" AND is_send = ? ");
            list.add(sendStatus);
        }

        if (StringUtils.isNotEmpty(signStartDate) && StringUtils.isNotEmpty(signEndDate)) {
            sql.append(" AND contract_sign_date >= ? AND contract_sign_date <= ?");
            list.add(signStartDate);
            list.add(signEndDate);
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, ContractInfoCycleNode.class);

    }

    /**
     * 根据记录id从DC_CONTRACT_INFO查询合同详细信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public ContractInfoCycleNode findContractById(String id) throws DAOException {
        String sql = "select * from  DC_CONTRACT_INFO where id = ?";
        return super.findForObjectBySql(sql, new Object[]{id}, ContractInfoCycleNode.class);
    }

    /**
     * 批量更新
     *
     * @param list
     * @param isUpdateValueNullField
     * @throws DAOException
     */
    @Override
    public void batchUpdateContract(List<ContractInfoCycleNode> list, boolean isUpdateValueNullField) throws DAOException {
        super.batchUpdate(list, isUpdateValueNullField);
    }

    /**
     * 根据申报状态和签约时间段查询最新版本合同信息
     *
     * @param sendStatus
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastContractBySendStatus(String sendStatus, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("select id,date_id,contract_no,customer_name,contract_amount,contract_sign_date,loan_cate,is_send,is_last,report_type,net_sign_no,is_real_quota_loan,real_quota_no FROM DC_CONTRACT_INFO WHERE 1 = 1  AND is_last = 'Y' ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotBlank(sendStatus)) {

            sql.append(" AND is_send = ? ");
            list.add(sendStatus);
        }
        if (StringUtils.isNotEmpty(signStartDate)) {
            sql.append(" AND contract_sign_date >= ?");
            list.add(signStartDate);
        }
        if (StringUtils.isNotEmpty(signEndDate)){
            sql.append(" AND contract_sign_date <= ?");
            list.add(signEndDate);
        }

        if (StringUtils.isNotEmpty(contractNo)){
            sql.append(" AND  contract_no = ? ");
            list.add(contractNo.trim());
        }
        pageBean.setSort("id");
        return super.findForPage(sql.toString(), list.toArray(), pageBean, ContractInfoCycleNode.class);
    }

}
