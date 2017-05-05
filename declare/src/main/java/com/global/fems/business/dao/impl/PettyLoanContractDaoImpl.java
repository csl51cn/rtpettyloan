package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.Entity;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.util.DateTimeUtil;
import org.apache.commons.lang.StringUtils;
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


    /**
     * 保存或更新贷款合同
     *
     * @param contract
     */
    public void saveOrUpdate(PettyLoanContract contract) throws BaseException {
        super.saveOrUpdate(contract);

    }


    /**
     * 根据起止时间分页查询贷款合同
     *
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    public PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException {

        StringBuilder sql = new StringBuilder("SELECT id,contractno,customername,contractamount,contractsigndate FROM DC_PETTY_LOAN_CONTRACT WHERE 1=1 ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND contractsigndate >= ? AND contractsigndate <= ?");
            list.add(startDate);
            list.add(endDate);
        }
        PageBean forPage = findForPage(sql.toString(), pageBean, PettyLoanContract.class, list);
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

        //查询总记录数
        Long totalRows = getTotalRows(sql.toString(), args.toArray());
        //查询记录
        List dataList = null;
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
            dataList = super.getJdbcTemplate().query(sql, args.toArray(), new BeanPropertyRowMapper(clazz));
        }
        pageBean.setDataList(dataList);

        return pageBean;
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
     * 根据id查询合同记录
     *
     * @param id
     * @return
     */
    public PettyLoanContract findpettyLoanContractById(String id) throws BaseException {
        String sql = "select * from DC_PETTY_LOAN_CONTRACT where id = ? ";
        List list = super.getJdbcTemplate().query(sql, new Object[]{id}, new BeanPropertyRowMapper(PettyLoanContract.class));
        if (list != null && list.size() > 0) {
            return (PettyLoanContract) list.get(0);
        }
        return null;
    }


    /**
     * 根据申报状态查询小额贷款合同记录
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
            //传入截至时间为yyyy-MM-dd格式字符串，将截至时间+1天，避免当天数据查询不到
            Date strToDate = DateTimeUtil.getStrToDate(insertEndDate, "yyyy-MM-dd");
            Calendar endDate = Calendar.getInstance();
            endDate.setTime(strToDate);
            endDate.add(endDate.DATE, 1);
            insertEndDate = DateTimeUtil.getDateToStr(endDate.getTime(),"yyyy-MM-dd");
            list.add(insertEndDate);
        }

        PageBean forPage = findForPage(sql.toString(), pageBean, PettyLoanContract.class, list);

        return forPage;
    }


}
