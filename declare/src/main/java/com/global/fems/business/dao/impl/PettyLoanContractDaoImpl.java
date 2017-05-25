package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.BaseDaoSupport;
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
    public void saveOrUpdate(PettyLoanContract contract) throws BaseException {
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
    public PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException {
        StringBuilder sql = new StringBuilder("SELECT " +
                "w.date_id AS dateid ," +
                "w.业务编号 AS businessNum," +
                "w.合同编号 AS contractno, " +
                "w.授信金额 AS contractamount, " +
                "w.签约时间 AS contractsigndate, " +
                "ISNULL( " +
                "CASE w.授信主体类型 " +
                "WHEN 1 THEN " +
                "m.客户名称 " +
                "WHEN 2 THEN " +
                "c.中文客户名称 " +
                "END, " +
                "'' " +
                ") AS customername " +
                "FROM " +
                "Data_WorkInfo w " +
                "LEFT JOIN Data_CompanyInfo c ON w.授信主体编号 = c.Id " +
                "LEFT JOIN Data_MemberInfo m ON w.授信主体编号 = m.ID " +
                "WHERE 1 = 1 ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND 签约时间 >= ? AND 签约时间 <= ?");
            list.add(startDate);
            list.add(endDate);
        }
        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, PettyLoanContract.class);
        return forPage;
    }


    /**
     * 根据id从表DC_PETTY_LOAN_CONTRACT查询合同记录
     *
     * @param id
     * @return
     */
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
     * @param sendStatus      0表示未申报，1表示已申报
     * @param startDate     签约时间起始时间
     * @param endDate    签约时间终止时间
     * @param pageBean
     */
    public PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String startDate, String endDate, PageBean pageBean) throws BaseException {
        StringBuilder sql = new StringBuilder("SELECT id ,dateid ,contractno,customername,contractamount,contractsigndate,sendstatus FROM DC_PETTY_LOAN_CONTRACT WHERE 1=1 ");
        List<Object> list = new ArrayList<Object>();
        if (sendStatus != null && StringUtils.isNotBlank(sendStatus.toString())) {

            sql.append(" AND sendStatus = ? ");
            list.add(sendStatus);
        }

        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND contractsigndate >= ? AND contractsigndate <= ?");
            list.add(startDate);
            list.add(endDate);
        }

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
    public PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException {
        StringBuilder sql = new StringBuilder(
                    "SELECT  " +
                        "  w.date_id AS dateid ," +
                        "  w.合同编号 AS contractno,  " +
                        "  w.授信金额 AS contractamount,  " +
                        "  w.签约时间 AS contractsigndate,  " +
                        "  w.利率 AS intrate,  " +
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
                        "  ) AS certificateType  " +
                        "FROM  " +
                        "  Data_WorkInfo w  " +
                        "LEFT JOIN Data_CompanyInfo c ON w.授信主体编号 = c.Id  " +
                        "LEFT JOIN Data_MemberInfo m ON w.授信主体编号 = m.ID  " +
                        "WHERE w.date_id = ? ");
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
    public void batchSavePettyLoanContract(List<PettyLoanContract> list) {
        super.batchInsert(list);
    }


    /**
     * 根据dateid从表DC_PETTY_LOAN_CONTRACT查询合同数据
     *
     * @param dateId
     * @return
     */
    public PettyLoanContract findContractByDateId(Integer dateId) {
        String sql = "select * from DC_PETTY_LOAN_CONTRACT where dateid = ?  ";
        logger.debug("Executing SQL query [{}], params: [{}]", sql, dateId);
        List list = super.getJdbcTemplate().query(sql.toString(), new Object[]{dateId}, new BeanPropertyRowMapper(PettyLoanContract.class));
        if (list != null && list.size() > 0) {
            return (PettyLoanContract) list.get(0);
        }
        return null;
    }
}
