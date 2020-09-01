package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.ContractIssueInfo;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 实时网签小额贷款合同管理Dao接口实现类
 */
@Repository("PettyLoanContractDao")
public class PettyLoanContractDaoImpl extends BaseDaoSupport implements PettyLoanContractDao {
    private static final Logger logger = LoggerFactory.getLogger(PettyLoanContractDaoImpl.class);



    /**
     * 保存或更新贷款合同
     *
     * @param contract
     */
    @Override
    public void saveOrUpdate(PettyLoanContract contract) throws DAOException {
        super.saveOrUpdate(contract);

    }


    /**
     * 根据指定的签约时间段从表“Data_WorkInfo”分页查询业务数据，如2017-4-10~2017-5-10查询的就是这4月到5月一个月内签约的合同
     *
     * @param startDate 起始时间
     * @param endDate   截至时间
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException {
        StringBuilder sql = new StringBuilder("SELECT  " +
                "  w.date_id AS dateid,  " +
                "  w.业务编号 AS businessNum,  " +
                "  w.合同编号 AS contractno,  " +
                " (case  w.是否为循环授信贷款  " +
                " WHEN  870 THEN " +
                "   w.循环授信额度   " +
                " ELSE  " +
                "    w.授信金额 " +
                " END)  AS contractamount, " +
                "  d.content AS contractsigndate,  " +
                "  ISNULL(  " +
                "    CASE w.授信主体类型  " +
                "    WHEN 1 THEN  " +
                "      m.客户名称  " +
                "    WHEN 2 THEN  " +
                "      c.中文客户名称  " +
                "    END,  " +
                "    ''  " +
                "  ) AS customername , " +
                " (CASE  w.是否为循环授信贷款 " +
                " WHEN  870 THEN " +
                "   '740001' " +
                " ELSE  " +
                "   '740002' " +
                " END ) AS is_real_quota_loan, " +
                " ISNULL(replace(w.循环授信合同编号,' ',''), '') as real_quota_no " +
                " FROM  " +
                "  Data_WorkInfo w  " +
                " LEFT JOIN Data_CompanyInfo c ON w.授信主体编号 = c.Id  " +
                " LEFT JOIN Data_MemberInfo m ON w.授信主体编号 = m.ID  " +
                " LEFT JOIN WorkData_Date d ON d.date_id = w.Date_Id  " +
                " WHERE  " +
                "  d.Flow_NO IN (  " +
                "    SELECT  " +
                "      Flow_No  " +
                "    FROM  " +
                "      WorkFlowConstruction  " +
                "    WHERE  " +
                "      Flow_Title = '现场签约'  " +
                "  )  " +
                " AND d.form_arrno = 24  " +
                " AND d.GoBackId = 0  " +
                " AND w.是否放款=485 ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND d.content >= ? AND d.content <= ?");
            list.add(startDate);
            endDate = endDate + " 23:59:59";
            list.add(endDate);
        }
        pageBean.setSort("w.date_id");
        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, PettyLoanContract.class);
        return forPage;
    }


    /**
     * 根据id从表DC_PETTY_LOAN_CONTRACT查询合同记录
     *
     * @param id
     * @return
     */
    @Override
    public PettyLoanContract findPettyLoanContractById(String id) throws BaseException {
        String sql = "select * from DC_PETTY_LOAN_CONTRACT where id = ? ";

        List list = super.getJdbcTemplate().query(sql, new Object[]{id}, new BeanPropertyRowMapper(PettyLoanContract.class));
        if (list != null && list.size() > 0) {
            return (PettyLoanContract) list.get(0);
        }
        return null;
    }


    /**
     * 根据申报状态从表DC_PETTY_LOAN_CONTRACT查询小额贷款合同记录
     *
     * @param sendStatus 0表示未申报，1表示已申报
     * @param contractNo 合同编号
     * @param startDate  签约时间起始时间
     * @param endDate    签约时间终止时间
     * @param pageBean
     */
    @Override
    public PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String contractNo, String startDate, String endDate, PageBean pageBean) throws BaseException {
        StringBuilder sql = new StringBuilder("SELECT id ,dateid ,contractno,customername,contractamount,contractsigndate,sendstatus,netsignno,islast,is_real_quota_loan,real_quota_no FROM DC_PETTY_LOAN_CONTRACT WHERE 1=1  ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(sendStatus.toString())) {
            sql.append(" AND sendStatus = ? ");
            list.add(sendStatus);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND contractsigndate >= ? ");
            list.add(startDate);

        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND contractsigndate <= ?");
            endDate = endDate + " 23:59:59";
            list.add(endDate);
        }

        if (StringUtils.isNotEmpty(contractNo)) {
            sql.append(" AND  contractno = ? ");
            list.add(contractNo.trim());
        }
        pageBean.setSort("id");
        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, PettyLoanContract.class);
        return forPage;
    }

    /**
     * 根据业务数据date_id从表Data_WorkInfo查询小额贷款合同数据
     *
     * @param dateId 业务数据id
     * @return
     * @throws BaseException
     */
    @Override
    public PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException {
        StringBuilder sql = new StringBuilder(
                "SELECT  " +
                        "  w.date_id AS dateid ," +
                        " w.合同编号 AS contractno, " +
                        " (case  w.是否为循环授信贷款  " +
                        " WHEN  870 THEN " +
                        "   w.循环授信额度   " +
                        " ELSE  " +
                        "    w.授信金额 " +
                        " END)  AS contractamount, " +
                        "  d.content AS contractsigndate,  " +
                        " ISNULL( " +
                        "  CASE dic.Word " +
                        "  WHEN '付易贷' THEN " +
                        "   w.利率 * 30 " +
                        "  ELSE " +
                        "   w.利率 " +
                        "  END, " +
                        "  NULL " +
                        " ) AS intrate, " +
                        "  ISNULL(  " +
                        "    CASE w.授信主体类型  " +
                        "    WHEN 1 THEN  " +
                        "      m.身份证号码  " +
                        "    WHEN 2 THEN  " +
                        "      c.组织机构代码证号  " +
                        "    END,  " +
                        "    ''  " +
                        "  ) AS certificateno,  " +
                        "  ISNULL(  " +
                        "    CASE w.授信主体类型  " +
                        "    WHEN 1 THEN  " +
                        "      m.客户名称  " +
                        "    WHEN 2 THEN  " +
                        "      c.中文客户名称  " +
                        "    END,  " +
                        "    ''  " +
                        "  ) AS customername,  " +
                        "  ISNULL(  " +
                        "    CASE w.授信主体类型  " +
                        "    WHEN 1 THEN  " +
                        "      480001  " +
                        "    WHEN 2 THEN  " +
                        "      480002  " +
                        "    END,  " +
                        "    ''  " +
                        "  ) AS customertype,  " +
                        "  ISNULL(  " +
                        "    CASE w.授信主体类型  " +
                        "    WHEN 1 THEN  " +
                        "      150001  " +
                        "    WHEN 2 THEN  " +
                        "      150002  " +
                        "    END,  " +
                        "    ''  " +
                        "  ) AS certificatetype,  " +
                        " (CASE  w.是否为循环授信贷款 " +
                        " WHEN  870 THEN " +
                        "   '740001' " +
                        " ELSE  " +
                        "   '740002' " +
                        " END ) AS is_real_quota_loan, " +
                        " ISNULL(replace(w.循环授信合同编号,' ',''), '') as real_quota_no " +
                        " FROM  " +
                        "  Data_WorkInfo w  " +
                        " LEFT JOIN Data_CompanyInfo c ON w.授信主体编号 = c.Id  " +
                        " LEFT JOIN Data_MemberInfo m ON w.授信主体编号 = m.ID  " +
                        " LEFT JOIN WorkData_Date d ON d.date_id = w.Date_Id " +
                        "Left Join Dictionary As dic On w.产品类别 = dic.Id " +
                        " WHERE  d.Flow_NO IN( " +
                        " SELECT  " +
                        "      Flow_No  " +
                        "    FROM  " +
                        "      WorkFlowConstruction  " +
                        "    WHERE  " +
                        "      Flow_Title = '现场签约'  " +
                        "  ) AND d.form_arrno = 24 AND  d.GoBackId = 0 AND " +
                        " w.date_id = ? ");
        logger.debug("Executing SQL query [{}], params: [{}]", sql, dateId);
        List list = super.getJdbcTemplate().query(sql.toString(), new Object[]{dateId}, new BeanPropertyRowMapper(PettyLoanContract.class));
        if (list != null && list.size() > 0) {
            return (PettyLoanContract) list.get(0);
        }
        return null;

    }

    /**
     * 批量插入合同记录
     *
     * @param list
     */
    @Override
    public void batchSavePettyLoanContract(List<PettyLoanContract> list) {
        super.batchInsert(list);
    }


    /**
     * 根据dateid从表DC_PETTY_LOAN_CONTRACT查询合同数据
     *
     * @param dateId
     * @return
     */
    @Override
    public PettyLoanContract findContractByDateId(Integer dateId) {
        String sql = "select * from DC_PETTY_LOAN_CONTRACT where dateid = ?  ";
        logger.debug("Executing SQL query [{}], params: [{}]", sql, dateId);
        List list = super.getJdbcTemplate().query(sql.toString(), new Object[]{dateId}, new BeanPropertyRowMapper(PettyLoanContract.class));
        if (list != null && list.size() > 0) {
            return (PettyLoanContract) list.get(0);
        }
        return null;
    }

    /**
     * 根据合同编号从DC_PETTY_LOAN_CONTRACT中查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @Override
    public PageBean findPettyLoanContractByContractNo(String contractNo, PageBean pageBean) throws BaseException {
        String sql = "select * from DC_PETTY_LOAN_CONTRACT where contractno = ?";
        pageBean.setSort("id");
        PageBean forPage = super.findForPage(sql, new Object[]{contractNo}, pageBean, PettyLoanContract.class);
        return forPage;
    }

    /**
     * 根据合同编号从业务系统查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @Override
    public PageBean findPettyLoanContractByContractNoFromBizSys(String contractNo, PageBean pageBean) throws BaseException {
        StringBuilder sql = new StringBuilder("SELECT " +
                " w.date_id AS dateid, " +
                " w.业务编号 AS businessNum, " +
                " w.合同编号 AS contractno, " +
                " (case  w.是否为循环授信贷款  " +
                " WHEN  870 THEN " +
                "   w.循环授信额度   " +
                " ELSE  " +
                "    w.授信金额 " +
                " END )  AS contractamount, " +
                " d.content AS contractsigndate, " +
                " ISNULL( " +
                "  CASE w.授信主体类型 " +
                "  WHEN 1 THEN " +
                "   m.客户名称 " +
                "  WHEN 2 THEN " +
                "   c.中文客户名称 " +
                "  END, " +
                "  '' " +
                " ) AS customername, " +
                " (CASE  w.是否为循环授信贷款 " +
                " WHEN  870 THEN " +
                "   '740001' " +
                " ELSE  " +
                "   '740002' " +
                " END ) AS is_real_quota_loan, " +
                " ISNULL(replace(w.循环授信合同编号,' ',''), '') as real_quota_no " +
                "FROM " +
                " Data_WorkInfo w " +
                "LEFT JOIN Data_CompanyInfo c ON w.授信主体编号 = c.Id " +
                "LEFT JOIN Data_MemberInfo m ON w.授信主体编号 = m.ID " +
                "LEFT JOIN WorkData_Date d ON d.date_id = w.Date_Id " +
                "WHERE " +
                " d.Flow_NO IN ( " +
                "  SELECT " +
                "   Flow_No " +
                "  FROM " +
                "   WorkFlowConstruction " +
                "  WHERE " +
                "   Flow_Title = '现场签约' " +
                " ) " +
                "AND d.form_arrno = 24 " +
                "AND d.GoBackId = 0 " +
                "AND w.合同编号 = ?");
        pageBean.setSort("w.date_id");
        PageBean forPage = super.findForPage(sql.toString(), new Object[]{contractNo}, pageBean, PettyLoanContract.class);
        return forPage;
    }

    /**
     * 根据合同编号查询dateId
     *
     * @param contractNo
     * @return
     * @throws BaseException
     */
    @Override
    public int findPettyLoanContractDateIdByContractNo(String contractNo) throws BaseException {
        String sql = "select date_id  from Data_WorkInfo where  合同编号 = ?";
        return super.findForIntBySql(sql, new Object[]{contractNo});
    }

    /**
     * 根据合同编号和发送状态从DC_PETTY_LOAN_CONTRACT查询合同部分信息
     *
     * @param contractNo
     * @param sendStatus
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractByContractNoFromRealTimeContract(String contractNo, String sendStatus, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id , dateid , contractno , customername , contractamount , contractsigndate , netsignno , islast ,is_real_quota_loan,real_quota_no FROM DC_PETTY_LOAN_CONTRACT WHERE islast = 'Y' AND contractno = ?  ");
        ArrayList list = new ArrayList();
        list.add(contractNo);
        if (sendStatus != null && StringUtils.isNotBlank(sendStatus.toString())) {
            sql.append(" AND sendstatus = ? ");
            list.add(sendStatus);
        }
        pageBean.setSort("id");
        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, PettyLoanContract.class);
        return forPage;
    }

    /**
     * 根据dateId查询合同信息集合
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public List<PettyLoanContract> findContractListByDateId(String dateId) throws DAOException {
        String sql = "select * from DC_PETTY_LOAN_CONTRACT where dateid = ?";
        List<PettyLoanContract> contractInfoCycleNodeList = (List<PettyLoanContract>) super.findForListBySql(sql, new Object[]{dateId}, PettyLoanContract.class);
        return contractInfoCycleNodeList;
    }

    /**
     * 根据申报状态从表DC_PETTY_LOAN_CONTRACT查询最新的小额贷款合同记录
     *
     * @param sendStatus
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastPettyLoanContractBySendStatus(Integer sendStatus, String startDate, String endDate, PageBean pageBean) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT id ,dateid ,contractno,customername,contractamount,contractsigndate,sendstatus,netsignno,islast,is_real_quota_loan,real_quota_no FROM DC_PETTY_LOAN_CONTRACT WHERE 1=1  AND islast = 'Y' ");
        List<Object> list = new ArrayList<Object>();
        if (sendStatus != null && StringUtils.isNotBlank(sendStatus.toString())) {

            sql.append(" AND sendStatus = ? ");
            list.add(sendStatus);
        }

        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND contractsigndate >= ? AND contractsigndate <= ?");
            list.add(startDate);
            endDate = endDate + " 23:59:59";
            list.add(endDate);
        }
        pageBean.setSort("id");
        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, PettyLoanContract.class);
        return forPage;
    }

    /**
     * 查询授信期限单位
     *
     * @param dateId
     * @return
     */
    @Override
    public String findTermUnit(Integer dateId) throws DAOException {
        String sql = "SELECT b.Word FROM Data_WorkInfo a LEFT JOIN Dictionary b ON a.[授信期限单位] = b.Id WHERE  Date_Id =  ?";
        logger.debug("Executing SQL query [{}], params: [{}]", sql, new Object[]{dateId});
        return (String) super.getJdbcTemplate().queryForObject(sql, new Object[]{dateId}, String.class);
    }

    /**
     * 查询产品类型
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public String findProductType(Integer dateId) throws DAOException {
        String sql = "SELECT dic.Word FROM Data_WorkInfo a LEFT JOIN  Dictionary As dic On a.产品类别 = dic.Id WHERE Date_Id = ? ";
        logger.debug("Executing SQL query [{}], params: [{}]", sql, new Object[]{dateId});
        return (String) super.getJdbcTemplate().queryForObject(sql, new Object[]{dateId}, String.class);
    }


    /**
     * 查询循环授信提款次数
     *
     * @param dateId                    当前业务dateId
     * @param revolvingCreditContractNo 循环授信合同编号
     * @return 返回
     */
    @Override
    public int findBusinessCount(Integer dateId, String revolvingCreditContractNo) {
        String sql = "SELECT count(*) FROM Data_WorkInfo WHERE  是否放款 = 485 AND Date_Id <= ? AND replace(循环授信合同编号,' ','') = ?  ";
        logger.debug("Executing SQL query [{}], params: [{}]", sql, new Object[]{dateId, revolvingCreditContractNo});
        return (Integer) super.getJdbcTemplate().queryForObject(sql, new Object[]{dateId, revolvingCreditContractNo}, Integer.class);
    }


    @Override
    public List<PettyLoanContract> findByBatchNo(String batchNo) {
        String sql = "SELECT * FROM DC_CONTRACT_ISSUE_INFO where batch_no = ?";
        return (List<PettyLoanContract>) super.findForListBySql(sql, new Object[]{batchNo}, ContractIssueInfo.class);
    }

    @Override
    public void batchUpdateInfo(List<PettyLoanContract> list, boolean isUpdateValueNullField) {
        super.batchUpdate(list, isUpdateValueNullField);
    }


    /**
     * 查询循环授信
     * @param dateIds
     * @return
     */
    @Override
    public List<PettyLoanContract> findRealQuotaContractListByWorkInfoId(List<String> dateIds) {
        String sql = "SELECT  " +
                "  w.date_id AS dateid ," +
                " w.合同编号 AS contractno, " +
                " (case  w.是否为循环授信贷款  " +
                " WHEN  870 THEN " +
                "   w.循环授信额度   " +
                " ELSE  " +
                "    w.授信金额 " +
                " END)  AS contractamount, " +
                "  d.content AS contractsigndate,  " +
                " ISNULL( " +
                "  CASE dic.Word " +
                "  WHEN '付易贷' THEN " +
                "   w.利率 * 30 " +
                "  ELSE " +
                "   w.利率 " +
                "  END, " +
                "  NULL " +
                " ) AS intrate, " +
                "  ISNULL(  " +
                "    CASE w.授信主体类型  " +
                "    WHEN 1 THEN  " +
                "      m.身份证号码  " +
                "    WHEN 2 THEN  " +
                "      c.组织机构代码证号  " +
                "    END,  " +
                "    ''  " +
                "  ) AS certificateno,  " +
                "  ISNULL(  " +
                "    CASE w.授信主体类型  " +
                "    WHEN 1 THEN  " +
                "      m.客户名称  " +
                "    WHEN 2 THEN  " +
                "      c.中文客户名称  " +
                "    END,  " +
                "    ''  " +
                "  ) AS customername,  " +
                "  ISNULL(  " +
                "    CASE w.授信主体类型  " +
                "    WHEN 1 THEN  " +
                "      480001  " +
                "    WHEN 2 THEN  " +
                "      480002  " +
                "    END,  " +
                "    ''  " +
                "  ) AS customertype,  " +
                "  ISNULL(  " +
                "    CASE w.授信主体类型  " +
                "    WHEN 1 THEN  " +
                "      150001  " +
                "    WHEN 2 THEN  " +
                "      150002  " +
                "    END,  " +
                "    ''  " +
                "  ) AS certificatetype,  " +
                " (CASE  w.是否为循环授信贷款 " +
                " WHEN  870 THEN " +
                "   '740001' " +
                " ELSE  " +
                "   '740002' " +
                " END ) AS is_real_quota_loan, " +
                " ISNULL(replace(w.循环授信合同编号,' ',''), '') as real_quota_no " +
                " FROM  " +
                "  Data_WorkInfo w  " +
                " LEFT JOIN Data_CompanyInfo c ON w.授信主体编号 = c.Id  " +
                " LEFT JOIN Data_MemberInfo m ON w.授信主体编号 = m.ID  " +
                " LEFT JOIN WorkData_Date d ON d.date_id = w.Date_Id " +
                "Left Join Dictionary As dic On w.产品类别 = dic.Id " +
                " WHERE w.是否为循环授信贷款 = 870  AND w.是否放款=485 and  d.Flow_NO IN( " +
                " SELECT  " +
                "      Flow_No  " +
                "    FROM  " +
                "      WorkFlowConstruction  " +
                "    WHERE  " +
                "      Flow_Title = '现场签约'  " +
                "  ) AND d.form_arrno = 24 AND  d.GoBackId = 0 AND " +
                "  w.date_id in" +getInClauseStr(dateIds);
        logger.debug("Executing SQL query [{}], params: [{}]", sql, dateIds);
        return super.getJdbcTemplate().query(sql, dateIds.toArray(), new BeanPropertyRowMapper(PettyLoanContract.class));
    }
    /**
     * 将记录设置为已上报
     *
     * @param dateIds dateIds ,格式dateId1,dateId2,dateId3
     */
    @Override
    public void updateSent(List<String> dateIds) {
        String sql = "update DC_PETTY_LOAN_CONTRACT set sendStatus = 1 where dateid in  " + getInClauseStr(dateIds);
        logger.debug("Executing SQL query [{}], params: [{}]", sql, dateIds);
        super.updateBySql(sql, dateIds.toArray());
    }


}
