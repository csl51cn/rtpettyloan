package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.Entity;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.util.DateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 小额贷款合同管理Dao接口实现类
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
     * 根据放款时间的指定时间段从视图“已放款客户表”分页查询业务数据
     *
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    public PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException {
       // SELECT id, 合同编号 as contractno, 客户名称 as customername,  批核金额 as contractamount,签约时间 as contractsigndate FROM Data_WorkInfo
        StringBuilder sql = new StringBuilder("SELECT Date_Id AS id, 合同编号 AS contractno, 业务号 AS  businessNum," +
                " 客户名称 AS customername, 放款金额 AS contractamount, 放款日期 AS contractsigndate FROM 已放款客户表 " +
                "WHERE 1 = 1");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND 放款日期 >= ? AND 放款日期 <= ?");
            list.add(startDate);
            list.add(endDate);
        }
        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, PettyLoanContract.class);
        return forPage;


    }



    /**
     * 分页查询
     *
     * @param sql
     * @param pageBean
     * @param clazz    返回结果封装的类的Class对象
     * @param args     sql 中的参数
     * @return
     */
    private PageBean findForPage(String sql, PageBean pageBean, Class<? extends Entity> clazz, List<Object> args) {
        Boolean flag = true;//判断是否需要加上默认按合同签订时间排序
        if (StringUtils.isNotBlank(pageBean.getSort())) {
            sql = sql + " ORDER BY " + pageBean.getSort() + " " + pageBean.getOrder();
            flag = false;
        }
        logger.debug("Executing SQL query [{}], params: [{}]", sql, args);
        try {
            //查询总记录数
            Long totalRows = getTotalRows(sql.toString(), args.toArray());
            //查询记录
            if (totalRows.longValue() > 0L) {
                pageBean.setTotalRows(totalRows);
                Integer startIndex = Integer.valueOf((pageBean.getPage().intValue() - 1) * pageBean.getRows().intValue());
                if (flag) {
                    sql = sql + " ORDER BY contractsigndate  OFFSET ? row fetch next ? rows only";
                } else {
                    sql = sql + " OFFSET ? row fetch next ? rows only";
                }
                args.add(startIndex);
                args.add(pageBean.getRows());
                logger.debug("Executing SQL query [{}], params: [{}]", sql, args);
                List dataList = super.getJdbcTemplate().query(sql, args.toArray(), new BeanPropertyRowMapper(clazz));
                pageBean.setDataList(dataList);
            }

            return pageBean;

        } catch (DataAccessException e) {
            logger.error("Executing SQL query error: " + e.getMessage(), e);
            throw new DAOException("Executing SQL query error: " + e.getMessage(), e);
        }
    }

    /**
     * 查询记录数
     *
     * @param sql
     * @param args
     * @return
     */
    private Long getTotalRows(String sql, Object... args) {
        StringBuilder e = new StringBuilder(sql.length() + 32);
        e.append("SELECT count(0) FROM (").append(sql).append(") as temp");
        return super.getJdbcTemplate().queryForObject(e.toString(), args, Long.class);
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
     * @param insertStartDate
     * @param insertEndDate
     * @param pageBean
     */
    public PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String insertStartDate, String insertEndDate, PageBean pageBean) throws BaseException {
        StringBuilder sql = new StringBuilder("SELECT id,contractno,customername,contractamount,contractsigndate,sendstatus FROM DC_PETTY_LOAN_CONTRACT WHERE 1=1 ");
        List<Object> list = new ArrayList<Object>();
        if (sendStatus != null && StringUtils.isNotBlank(sendStatus.toString())) {

            sql.append(" AND sendStatus = ? ");
            list.add(sendStatus);
        }

        if (StringUtils.isNotEmpty(insertStartDate) && StringUtils.isNotEmpty(insertEndDate)) {
            sql.append(" AND insertdate BETWEEN ? AND ?");
            list.add(insertStartDate);
            //传入截至时间为yyyy-MM-dd格式字符串，数据库中时间格式是datetime，将截至时间+1天，避免当天数据查询不到
            insertEndDate = DateTimeUtil.dayAdd(insertEndDate,1);
            list.add(insertEndDate);
        }

        PageBean forPage = super.findForPage(sql.toString(), list.toArray(), pageBean, PettyLoanContract.class);
        return forPage;
    }

    /**
     * 根据业务数据date_id查询小额贷款合同
     * @param id  业务数据id
     * @return
     * @throws BaseException
     */
    public PettyLoanContract findPettyLoanContractByWorkInfoId(String id) throws BaseException {
        String sql = "SELECT w.合同编号 AS contractno,y.客户名称 AS customername," +
                "y.放款金额 AS contractamount,w.签约时间 AS contractsigndate," +
                "w.利率 AS intrate," +
                "ISNULL(" +
                "CASE w.授信主体类型 " +
                "WHEN 1 THEN " +
                " m.身份证号码 " +
                "WHEN 2 THEN " +
                " c.组织机构代码证号 " +
                " END, " +
                " '' " +
                " ) AS certificateno, " +
                "  ISNULL( " +
                "  CASE w.授信主体类型 " +
                "  WHEN 1 THEN " +
                "    480001 " +
                "  WHEN 2 THEN " +
                "    480002 " +
                "  END, " +
                "  '' " +
                " ) AS customertype " +",  " +
                "  ISNULL(  " +
                "    CASE w.授信主体类型  " +
                "    WHEN 1 THEN  " +
                "      150001  " +
                "    WHEN 2 THEN  " +
                "      150002  " +
                "    END,  " +
                "    ''  " +
                "  ) AS certificateType " +
                "FROM " +
                " Data_WorkInfo w " +
                "LEFT JOIN Data_CompanyInfo c ON w.授信主体编号= c.Id " +
                "LEFT JOIN Data_MemberInfo m ON w.授信主体编号 = m.ID " +
                "LEFT JOIN [已放款客户表] y ON w.date_id = y.date_id " +
                "WHERE w.date_id = ? " ;
        logger.debug("Executing SQL query [{}], params: [{}]", sql, id);
        List list = super.getJdbcTemplate().query(sql, new Object[]{id}, new BeanPropertyRowMapper(PettyLoanContract.class));
        if (list != null && list.size() > 0) {
            return (PettyLoanContract) list.get(0);
        }
        return null;

    }


}
