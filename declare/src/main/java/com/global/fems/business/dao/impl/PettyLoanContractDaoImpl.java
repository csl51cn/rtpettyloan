package com.global.fems.business.dao.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 小额贷款合同管理Dao
 */
@Repository("PettyLoanContractDao")
public class PettyLoanContractDaoImpl extends BaseDaoSupport implements PettyLoanContractDao {


    /**
     * 保存贷款合同
     *
     * @param contract 合同实体类
     * @throws BaseException
     */
    public void insert(PettyLoanContract contract) throws BaseException {
        super.insert(contract);
    }

    /**
     * 根据起止时间分页查询贷款合同
     *
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    public PageBean findForPage(String startDate, String endDate, PageBean pageBean) {

        String countSql = "SELECT count(0) FROM [DC_PETTY_LOAN_CONTRACT] WHERE contractsigndate >= ? and contractsigndate <= ?";
        //查询总记录数
        Long totalRows = super.getJdbcTemplate().queryForObject(countSql, new Object[]{startDate, endDate}, Long.class);
        //查询记录
        List dataList = null;
        if (totalRows.longValue() > 0L) {
            pageBean.setTotalRows(totalRows);
            Integer startIndex = Integer.valueOf((pageBean.getPage().intValue() - 1) * pageBean.getRows().intValue());
            String sql = "SELECT * FROM " +
                    "[DC_PETTY_LOAN_CONTRACT] WHERE contractsigndate >= ? and contractsigndate <= ?  ORDER BY contractsigndate OFFSET ? row fetch next ? rows only";

            dataList = super.getJdbcTemplate().query(sql, new Object[]{startDate, endDate, pageBean.getPage(), pageBean.getRows()}, new BeanPropertyRowMapper(PettyLoanContract.class));
        }
        pageBean.setDataList(dataList);

        return pageBean;


    }

    /**
     * 根据id查询合同记录
     * @param id
     * @return
     */
    public PettyLoanContract findpettyLoanContractById(String id) {
        String sql = "select * from DC_PETTY_LOAN_CONTRACT where id = ? ";
        List list = super.getJdbcTemplate().query(sql, new Object[]{id}, new BeanPropertyRowMapper(PettyLoanContract.class));
        if (list != null && list.size() > 0) {
            return (PettyLoanContract) list.get(0);
        }
        return null;
    }
}
