//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.support;

import com.global.framework.dbutils.annotation.ColumnDescribe;
import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.ColumnType.ColumnTypeEnum;
import com.global.framework.dbutils.annotation.EntityDescribe;
import com.global.framework.dbutils.annotation.PrimaryKeyType.PrimaryKeyTypeEnum;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.util.DateTimeUtil;
import com.global.framework.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;

public abstract class BaseDaoSupport {
    private static final Logger logger = LoggerFactory.getLogger(BaseDaoSupport.class);
    private static Map<Class<? extends Entity>, EntityDescribe> dtoDescMap = new HashMap(512);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LobHandler lobHandler;

    public BaseDaoSupport() {
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected <E extends Entity> E insert(E dto) {
        try {
            EntityDescribe e = this.getEntityDescribe(dto.getClass());
            Map errorMsg1 = PropertyUtils.describe(dto);
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createInsertSql(e, errorMsg1);
            Object[] args = sqlHelper.getInsertValues(e, errorMsg1);
            logger.debug("Executing SQL insert [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            this.jdbcTemplate.update(sql, args);
            PropertyUtils.copyProperties(dto, errorMsg1);
            logger.debug("Executing SQL insert as return dto : {}", dto);
            return dto;
        } catch (Exception var7) {
            String errorMsg = var7.getMessage();
            logger.error("Executing SQL insert error:" + errorMsg, var7);
            throw new DAOException("Executing SQL insert error: " + errorMsg, var7);
        }
    }

    protected int insertBySql(String sql, Object[] args) {
        try {
            logger.debug("Executing defined SQL insert [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.update(sql, args);
        } catch (Exception var4) {
            logger.error("Executing defined SQL insert error:" + var4.getMessage(), var4);
            throw new DAOException("Executing defined SQL insert error: " + var4.getMessage(), var4);
        }
    }

    protected final <E extends Entity> int[] batchInsert(List<E> list) {
        try {
            EntityDescribe e = this.getEntityDescribe(((Entity) list.get(0)).getClass());
            Map dtoMap = PropertyUtils.describe(list.get(0));
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createInsertSql(e, dtoMap);
            LinkedList batchArgs = new LinkedList();
            Iterator i$ = list.iterator();

            while (i$.hasNext()) {
                Entity entity = (Entity) i$.next();
                Object[] args = sqlHelper.getInsertValues(e, PropertyUtils.describe(entity));
                batchArgs.add(args);
                logger.debug("Executing SQL batch insert [{}], params: [{}]", sql, args);
            }

            return this.jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (Exception var10) {
            logger.error("Executing SQL batch insert error:" + var10.getMessage(), var10);
            throw new DAOException("Executing SQL batch insert error: " + var10.getMessage(), var10);
        }
    }

    public <E extends Entity> E insertHasBlob(E dto) {
        try {
            final EntityDescribe e = this.getEntityDescribe(dto.getClass());
            Map dtoMap = PropertyUtils.describe(dto);
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createInsertSql(e, dtoMap);
            final Object[] args = sqlHelper.getInsertValues(e, dtoMap);
            logger.debug("Executing SQL insert [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            this.jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(this.lobHandler) {
                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
                    for (int i = 0; i < e.getColumnList().size(); ++i) {
                        ColumnDescribe cd = (ColumnDescribe) e.getColumnList().get(i);
                        if (ColumnTypeEnum.VARCHAR.equals(cd.getColumnType())) {
                            ps.setString(i + 1, (String) args[i]);
                        } else if (ColumnTypeEnum.SHORT.equals(cd.getColumnType())) {
                            ps.setShort(i + 1, Short.valueOf((String) args[i]).shortValue());
                        } else if (ColumnTypeEnum.INTEGER.equals(cd.getColumnType())) {
                            ps.setInt(i + 1, Integer.valueOf((String) args[i]).intValue());
                        } else if (ColumnTypeEnum.LONG.equals(cd.getColumnType())) {
                            ps.setLong(i + 1, Long.valueOf((String) args[i]).longValue());
                        } else if (ColumnTypeEnum.DOUBLE.equals(cd.getColumnType())) {
                            ps.setDouble(i + 1, Double.valueOf((String) args[i]).doubleValue());
                        } else if (ColumnTypeEnum.BigDecimal.equals(cd.getColumnType())) {
                            ps.setBigDecimal(i + 1, new BigDecimal((String) args[i]));
                        } else if (ColumnTypeEnum.DATE.equals(cd.getColumnType())) {
                            ps.setDate(i + 1, DateTimeUtil.getStrToDateTime((String) args[i], (String) null));
                        } else if (ColumnTypeEnum.TIME.equals(cd.getColumnType())) {
                            ps.setTime(i + 1, DateTimeUtil.getStrToTime((String) args[i], (String) null));
                        } else if (ColumnTypeEnum.Timestamp.equals(cd.getColumnType())) {
                            ps.setTimestamp(i + 1, DateTimeUtil.getStrToTimestamp((String) args[i], (String) null));
                        } else if (ColumnTypeEnum.FLOAT.equals(cd.getColumnType())) {
                            ps.setFloat(i + 1, Float.valueOf((String) args[i]).floatValue());
                        } else if (ColumnTypeEnum.CLOB.equals(cd.getColumnType())) {
                            lobCreator.setClobAsString(ps, i + 1, (String) args[i]);
                        } else if (ColumnTypeEnum.BLOB.equals(cd.getColumnType())) {
                            lobCreator.setBlobAsBytes(ps, i + 1, (byte[]) ((byte[]) args[i]));
                        }
                    }

                }
            });
            PropertyUtils.copyProperties(dto, dtoMap);
            return dto;
        } catch (Exception var7) {
            logger.error("Executing SQL update error:" + var7.getMessage(), var7);
            throw new DAOException("Executing SQL update error: " + var7.getMessage(), var7);
        }
    }

    protected <E extends Entity> int updateWithNullField(E dto) {
        try {
            EntityDescribe e = this.getEntityDescribe(dto.getClass());
            Map dtoMap = PropertyUtils.describe(dto);
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createUpdateSql(e, dtoMap, true);
            Object[] args = sqlHelper.getUpdateValues(e, dtoMap, true);
            logger.debug("Executing SQL update [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.update(sql, args);
        } catch (Exception var7) {
            logger.error("Executing SQL update error:" + var7.getMessage(), var7);
            throw new DAOException("Executing SQL update error: " + var7.getMessage(), var7);
        }
    }

    protected <E extends Entity> int update(E dto) {
        try {
            EntityDescribe e = this.getEntityDescribe(dto.getClass());
            Map dtoMap = PropertyUtils.describe(dto);
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createUpdateSql(e, dtoMap, false);
            Object[] args = sqlHelper.getUpdateValues(e, dtoMap, false);
            logger.debug("Executing SQL update [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.update(sql, args);
        } catch (Exception var7) {
            logger.error("Executing SQL update error:" + var7.getMessage(), var7);
            throw new RuntimeException("Executing SQL update error: " + var7.getMessage(), var7);
        }
    }

    protected int updateBySql(String sql, Object[] args) {
        try {
            logger.debug("Executing defined SQL update [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.update(sql, args);
        } catch (Exception var4) {
            logger.error("Executing defined SQL update error:" + var4.getMessage(), var4);
            throw new DAOException("Executing SQL update error: " + var4.getMessage(), var4);
        }
    }

    protected final <E extends Entity> int[] batchUpdate(List<E> list, boolean isUpdateValueNullField) {
        try {
            EntityDescribe e = this.getEntityDescribe(((Entity) list.get(0)).getClass());
            Map dtoMap = PropertyUtils.describe(list.get(0));
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createUpdateSql(e, dtoMap, isUpdateValueNullField);
            LinkedList batchArgs = new LinkedList();
            Iterator i$ = list.iterator();

            while (i$.hasNext()) {
                Entity entity = (Entity) i$.next();
                Object[] args = sqlHelper.getUpdateValues(e, PropertyUtils.describe(entity), isUpdateValueNullField);
                batchArgs.add(args);
                logger.debug("Executing SQL batch update [{}], params: [{}]", sql, args);
            }

            return this.jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (Exception var11) {
            logger.error("Executing SQL batch update error: " + var11.getMessage(), var11);
            throw new DAOException("Executing SQL batch update error: " + var11.getMessage(), var11);
        }
    }

    protected int[] batchUpdateBySql(String sql, List<Object[]> batchArgs) {
        try {
            logger.debug("Executing defined SQL batch update [{}]", sql);
            return this.jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (Exception var4) {
            logger.error("Executing defined SQL batch update error:" + var4.getMessage(), var4);
            throw new DAOException("Executing defined SQL batch update error: " + var4.getMessage(), var4);
        }
    }

    protected <E extends Entity> E saveOrUpdate(E dto) {
        try {
            EntityDescribe e = this.getEntityDescribe(dto.getClass());
            Map dtoMap = PropertyUtils.describe(dto);
            SqlHelper sqlHelper = new SqlHelper();
            TableMapping tm = (TableMapping) e.getClassAnnotation();
            String[] pkNames = sqlHelper.getPrimaryKey(tm);
            if (pkNames == null) {
                throw new DAOException(DAOErrorCode.SQL_UPDATE_NOTPK.getCode(), new String[]{e.getEntityName()});
            } else if (PrimaryKeyTypeEnum.Single.getCode().equals(tm.primaryKeyType()) && StringUtil.isNullOrEmpty(dtoMap.get(pkNames[0]))) {
                return this.insert(dto);
            } else {
                Entity entity = this.findForObject(dto);
                if (entity != null) {
                    this.updateWithNullField(dto);
                } else {
                    this.insert(dto);
                }

                return dto;
            }
        } catch (Exception var8) {
            logger.error("Executing SaveOrUpdate error: " + var8.getMessage(), var8);
            throw new DAOException("Executing SaveOrUpdate error: " + var8.getMessage() + var8);
        }
    }

    protected <E extends Entity> int delete(E dto) {
        try {
            EntityDescribe e = this.getEntityDescribe(dto.getClass());
            Map dtoMap = PropertyUtils.describe(dto);
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createDeleteSql(e, dtoMap);
            Object[] args = sqlHelper.getPKWhereList(e, dtoMap);
            logger.debug("Executing SQL delete [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.update(sql, args);
        } catch (Exception var7) {
            logger.error("Executing SQL delete error: " + var7.getMessage(), var7);
            throw new DAOException("Executing SQL delete error: " + var7.getMessage(), var7);
        }
    }

    protected int delete(Class<? extends Entity> dtoClazz, Object primaryKeyValue) {
        try {
            Map e = PropertyUtils.describe(dtoClazz.newInstance());
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createDeleteSql(this.getEntityDescribe(dtoClazz), e);
            Object[] list = new Object[]{primaryKeyValue};
            logger.debug("Executing SQL delete [{}], params: [{}]", sql, primaryKeyValue);
            return this.jdbcTemplate.update(sql, list);
        } catch (Exception var7) {
            logger.error("Executing SQL delete error: " + var7.getMessage(), var7);
            throw new DAOException("Executing SQL delete error: " + var7.getMessage(), var7);
        }
    }

    protected int delete(String sql, Object[] value) {
        try {
            logger.debug("Executing SQL delete [{}], params: [{}]", SqlHelper.getArrayToString(value));
            return this.jdbcTemplate.update(sql, value);
        } catch (Exception var4) {
            logger.error("Executing SQL delete error: " + var4.getMessage(), var4);
            throw new DAOException("Executing SQL delete error: " + var4.getMessage(), var4);
        }
    }

    protected final <E extends Entity> int[] batchDelete(List<E> list) {
        try {
            EntityDescribe e = this.getEntityDescribe(((Entity) list.get(0)).getClass());
            Map dtoMap = PropertyUtils.describe(list.get(0));
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createDeleteSql(e, dtoMap);
            LinkedList batchArgs = new LinkedList();
            Iterator i$ = list.iterator();

            while (i$.hasNext()) {
                Entity entity = (Entity) i$.next();
                Object[] args = sqlHelper.getPKWhereList(e, PropertyUtils.describe(entity));
                batchArgs.add(args);
                logger.debug("Executing SQL batch delete [{}], params: [{}]", sql, args);
            }

            return this.jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (Exception var10) {
            logger.error("Executing SQL batch delete error: " + var10.getMessage(), var10);
            throw new DAOException("Executing SQL batch delete error: " + var10.getMessage(), var10);
        }
    }

    protected <E extends Entity> E findForObject(Class<? extends Entity> dtoClazz, Object primaryKeyValue) {
        try {
            SqlHelper e = new SqlHelper();
            String sql = e.createQuerySql(this.getEntityDescribe(dtoClazz));
            logger.debug("Executing SQL query [{}], params: [{}]", sql, primaryKeyValue);
            Object[] list = new Object[]{primaryKeyValue};
            Entity dto = (Entity) this.jdbcTemplate.queryForObject(sql, list, new BeanPropertyRowMapper(dtoClazz));
            return (E) dto;
        } catch (Exception var7) {
            if (var7 instanceof EmptyResultDataAccessException) {
                return null;
            } else if (var7 instanceof IncorrectResultSizeDataAccessException) {
                logger.error("Executing SQL query error: errorCode[{}]", DAOErrorCode.DAO_SINGLE_RECORD.getValue() + ":" + DAOErrorCode.DAO_SINGLE_RECORD.getValue(), var7);
                throw new DAOException(DAOErrorCode.DAO_SINGLE_RECORD.getCode(), var7);
            } else {
                logger.error("Executing SQL query error: " + var7.getMessage(), var7);
                throw new DAOException("Executing SQL query error: " + var7.getMessage(), var7);
            }
        }
    }

    protected <E extends Entity> E findForObject(E dto) {
        try {
            EntityDescribe e = this.getEntityDescribe(dto.getClass());
            Map dtoMap = PropertyUtils.describe(dto);
            SqlHelper sqlHelper = new SqlHelper();
            String sql = sqlHelper.createQuerySql(e);
            Object[] args = sqlHelper.getPKWhereList(e, dtoMap);
            logger.debug("Executing SQL query [{}], params: [{}]", sql, args);
            dto = (E) this.jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper(dto.getClass()));
            return dto;
        } catch (Exception var7) {
            if (var7 instanceof EmptyResultDataAccessException) {
                return null;
            } else if (var7 instanceof IncorrectResultSizeDataAccessException) {
                logger.error("Executing SQL query error: errorCode[{}]", DAOErrorCode.DAO_SINGLE_RECORD.getValue() + ":" + DAOErrorCode.DAO_SINGLE_RECORD.getValue(), var7);
                throw new DAOException(DAOErrorCode.DAO_SINGLE_RECORD.getCode(), var7);
            } else {
                logger.error("Executing SQL query error: " + var7.getMessage(), var7);
                throw new DAOException("Executing SQL query error: " + var7.getMessage(), var7);
            }
        }
    }

    protected <E extends Entity> E findForObjectBySql(String sql, Object[] args, Class<? extends Entity> dtoClazz) {
        try {
            logger.debug("Executing SQL query [{}], params: [{}]", sql, SqlHelper.getArrayToString(args));
            return (E) this.jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper(dtoClazz));
        } catch (Exception var5) {
            if (var5 instanceof EmptyResultDataAccessException) {
                return null;
            } else if (var5 instanceof IncorrectResultSizeDataAccessException) {
                logger.error("Executing SQL query error: errorCode[{}]", DAOErrorCode.DAO_SINGLE_RECORD.getValue() + ":" + DAOErrorCode.DAO_SINGLE_RECORD.getValue(), var5);
                throw new DAOException(DAOErrorCode.DAO_SINGLE_RECORD.getCode(), var5);
            } else {
                logger.error("Executing SQL query error: " + var5.getMessage(), var5);
                throw new DAOException("Executing SQL query error: " + var5.getMessage(), var5);
            }
        }
    }

    protected int findForIntBySql(String sql, Object[] args) {
        try {
            logger.debug("Executing SQL query [{}], params: [{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.queryForObject(sql, args, Integer.class);
        } catch (DataAccessException var4) {
            if (var4 instanceof EmptyResultDataAccessException) {
                return 0;
            } else {
                logger.error("Executing SQL query error: " + var4.getMessage(), var4);
                throw new DAOException("Executing SQL query error: " + var4.getMessage(), var4);
            }
        }
    }

    protected Long findForLongBySql(String sql, Object[] args) {
        try {
            logger.debug("Executing SQL query [{}], params: [{}]", sql, SqlHelper.getArrayToString(args));
            return Long.valueOf(this.jdbcTemplate.queryForObject(sql, args, Long.class));
        } catch (DataAccessException var4) {
            if (var4 instanceof EmptyResultDataAccessException) {
                return Long.valueOf(0L);
            } else {
                logger.error("Executing SQL query error: " + var4.getMessage(), var4);
                throw new DAOException("Executing SQL query error: " + var4.getMessage(), var4);
            }
        }
    }

    protected Object findForDefTypeBySql(String sql, Object[] args, Class<?> clazz) {
        try {
            logger.debug("Executing SQL query [{}], params: [{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper(clazz));
        } catch (Exception var5) {
            if (var5 instanceof EmptyResultDataAccessException) {
                return null;
            } else if (var5 instanceof IncorrectResultSizeDataAccessException) {
                logger.error("Executing SQL query error: errorCode[{}]", DAOErrorCode.DAO_SINGLE_RECORD.getValue() + ":" + DAOErrorCode.DAO_SINGLE_RECORD.getValue(), var5);
                throw new DAOException(DAOErrorCode.DAO_SINGLE_RECORD.getCode(), var5);
            } else {
                logger.error("Executing SQL query error: " + var5.getMessage(), var5);
                throw new DAOException("Executing SQL query error: " + var5.getMessage(), var5);
            }
        }
    }

    protected List<? extends Entity> findForList(Class<? extends Entity> dtoClazz) {
        try {
            SqlHelper e = new SqlHelper();
            String sql = e.createSelectSql(this.getEntityDescribe(dtoClazz), true);
            logger.debug("Executing SQL query [{}]", sql);
            return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(dtoClazz));
        } catch (Exception var4) {
            logger.error("Executing SQL query error: " + var4.getMessage(), var4);
            throw new DAOException("Executing SQL query error: " + var4.getMessage(), var4);
        }
    }

    protected List<?> findForList(Class<? extends Entity> dtoClazz, Map<String, Object> condition) {
        try {
            SqlHelper e = new SqlHelper();
            String sql = e.createSelectSql(this.getEntityDescribe(dtoClazz), false) + e.createWhereSql(condition);
            Object[] list = condition.values().toArray();
            logger.debug("Executing SQL query [{}], params:[{}]", sql, SqlHelper.getArrayToString(list));
            return this.jdbcTemplate.query(sql, list, new BeanPropertyRowMapper(dtoClazz));
        } catch (Exception var6) {
            logger.error("Executing SQL query error: " + var6.getMessage(), var6);
            throw new DAOException("Executing SQL query error: " + var6.getMessage(), var6);
        }
    }

    protected List<? extends Entity> findForListBySql(String sql, Object[] args, Class<? extends Object> dtoClazz) {
        try {
            logger.debug("Executing SQL query [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(dtoClazz));
        } catch (Exception var5) {
            logger.error("Executing SQL query error: " + var5.getMessage(), var5);
            throw new DAOException("Executing SQL query error: " + var5.getMessage(), var5);
        }
    }

    protected List<Map<String, Object>> findForListMapBySql(String sql, Object[] args) {
        try {
            logger.debug("Executing SQL query [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            return this.jdbcTemplate.queryForList(sql, args);
        } catch (DataAccessException var4) {
            logger.error("Executing SQL query error: " + var4.getMessage(), var4);
            throw new DAOException("Executing SQL query error: " + var4.getMessage(), var4);
        }
    }

    protected List<Object[]> findForListBySql(String sql, Object[] args) {
        try {
            logger.debug("Executing SQL query [{}], params:[{}]", sql, SqlHelper.getArrayToString(args));
            List e = this.jdbcTemplate.queryForList(sql, args);
            ArrayList rtList = new ArrayList();

            for (int i = 0; i < e.size(); ++i) {
                Map map = (Map) e.get(i);
                Set s = map.entrySet();
                Object[] obj = new Object[s.size()];
                int j = 0;

                for (Iterator i$ = s.iterator(); i$.hasNext(); ++j) {
                    Entry entry = (Entry) i$.next();
                    obj[j] = entry.getValue();
                }

                rtList.add(obj);
            }

            return rtList;
        } catch (Exception var12) {
            logger.error("Executing SQL query error: " + var12.getMessage(), var12);
            throw new DAOException("Executing SQL query error: " + var12.getMessage(), var12);
        }
    }

    protected PageBean findForPage(String sql, Object[] args, PageBean page, Class<? extends Entity> clazz) {

        logger.debug("Executing SQL query [{}], params: [{}]", sql, args);

        try {
            PageBean e = null;
            Long totalRows = this.getTotalRows(sql, args);
            if (StringUtils.isNotBlank(page.getSort())) {
                sql = sql + " order by " + page.getSort() + " " + page.getOrder();
            }
            if (totalRows.longValue() > 0L) {
                Integer startIndex = Integer.valueOf((page.getPage().intValue() - 1) * page.getRows().intValue());
                sql = this.getPageSql(sql, startIndex, page.getRows());
                logger.debug("Executing SQL query [{}], params: [{}]", sql, args);
                List dataList = this.jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(clazz));
                e = new PageBean(totalRows, dataList, page.getPage(), page.getRows());
            } else {
                e = new PageBean();
            }

            return e;
        } catch (Exception var10) {
            logger.error("Executing SQL query error: " + var10.getMessage(), var10);
            throw new DAOException("Executing SQL query error: " + var10.getMessage(), var10);
        }
    }

    private final Object[] callProcedure(String procedureStr, LinkedList<LinkedHashMap<ColumnTypeEnum, Object>> inParams) {
        try {
            String e = "call {0}";
            String newStr = MessageFormat.format(e, new Object[]{procedureStr});
            newStr = "{" + newStr + "}";
            logger.debug("Executing procedure: {}", newStr);
            Connection conn = this.jdbcTemplate.getDataSource().getConnection();
            CallableStatement cs = conn.prepareCall(newStr);
            int i = 0;
            Iterator rs = inParams.iterator();

            while (rs.hasNext()) {
                LinkedHashMap ret = (LinkedHashMap) rs.next();
                Set typeEnumSet = ret.keySet();
                Object[] values = ret.values().toArray();
                Iterator it = typeEnumSet.iterator();

                while (it.hasNext()) {
                    ColumnTypeEnum typeEnum = (ColumnTypeEnum) it.next();
                    if (ColumnTypeEnum.VARCHAR.getTypeCode().equals(typeEnum.getTypeCode())) {
                        cs.setString(i + 1, (String) values[i]);
                    } else if (ColumnTypeEnum.INTEGER.getTypeCode().equals(typeEnum.getTypeCode())) {
                        cs.setInt(i + 1, Integer.valueOf((String) values[i]).intValue());
                    } else if (ColumnTypeEnum.LONG.getTypeCode().equals(typeEnum.getTypeCode())) {
                        cs.setLong(i + 1, Long.valueOf((String) values[i]).longValue());
                    } else if (ColumnTypeEnum.DOUBLE.getTypeCode().equals(typeEnum.getTypeCode())) {
                        cs.setDouble(i + 1, Double.valueOf((String) values[i]).doubleValue());
                    } else if (ColumnTypeEnum.BigDecimal.getTypeCode().equals(typeEnum.getTypeCode())) {
                        cs.setBigDecimal(i + 1, new BigDecimal((String) values[i]));
                    } else if (ColumnTypeEnum.DATE.getTypeCode().equals(typeEnum.getTypeCode())) {
                        cs.setDate(i + 1, DateTimeUtil.getStrToDateTime((String) values[i], (String) null));
                    } else if (ColumnTypeEnum.Timestamp.getTypeCode().equals(typeEnum.getTypeCode())) {
                        cs.setTimestamp(i + 1, DateTimeUtil.getStrToTimestamp((String) values[i], (String) null));
                    } else {
                        ++i;
                    }
                }
            }

            cs.execute();
            ResultSet var15 = cs.getResultSet();
            LinkedList var16 = new LinkedList();
            if (var15.next()) {
                var16.add(var15.getString(0));
            }

            return var16.toArray();
        } catch (Exception var14) {
            logger.error("Executing procedure fialure:" + var14.getMessage(), var14);
            throw new DAOException(DAOErrorCode.SQL_INVOKE_PROC_FAIL.getCode(), var14, new String[]{procedureStr});
        }
    }

    protected final void executeSql(String sql) {
        try {
            logger.debug("Executing DDL SQL [{}]", sql);
            this.jdbcTemplate.execute(sql);
        } catch (DataAccessException var3) {
            throw new DAOException("Executing DDL SQL error: " + var3.getMessage(), var3);
        }
    }

    private final String getPageSql(String sql, Integer startIndex, Integer rows) {
        try {
            StringBuilder e = new StringBuilder(sql.length() + 512);
            e.append(sql);
            e.append("  offset ");
            e.append(startIndex);
            e.append(" row fetch next ");
            e.append(rows);
            e.append(" row only");
            return e.toString();
        } catch (Exception var5) {
            throw new DAOException("Generate page SQL error: " + var5.getMessage(), var5);
        }
    }

    private final Long getTotalRows(String sql, Object[] args) {
        try {
            StringBuilder e = new StringBuilder(sql.length() + 32);
            e.append("SELECT count(0) FROM (").append(sql).append(") ttttxxxxxxtttt");
            return Long.valueOf(this.jdbcTemplate.queryForObject(e.toString(), args, Long.class));
        } catch (DataAccessException var4) {
            throw new DAOException("Executing SQL query error: " + var4.getMessage(), var4);
        }
    }

    private EntityDescribe getEntityDescribe(Class<? extends Entity> entity) throws DAOException {
        Annotation[] fieldAnnotations = null;
        HashMap columnMap = null;
        LinkedList columnList = null;
        Field[] fields = null;
        EntityDescribe entityDescribe = null;
        ColumnMapping column = null;
        ColumnDescribe columnDescribe = null;
        Map var9 = dtoDescMap;
        synchronized (dtoDescMap) {
            entityDescribe = (EntityDescribe) dtoDescMap.get(entity);
            if (entityDescribe != null) {
                return entityDescribe;
            } else {
                entityDescribe = new EntityDescribe();
                entityDescribe.setEntityName(entity.getSimpleName());
                Annotation[] classAnnotations = entity.getDeclaredAnnotations();
                if (classAnnotations.length == 0) {
                    throw new DAOException(DAOErrorCode.ENT_NOTFOUND_TABANNO.getCode(), new String[]{entity.getName()});
                } else {
                    Annotation[] arr$ = classAnnotations;
                    int len$ = classAnnotations.length;

                    int i$;
                    for (i$ = 0; i$ < len$; ++i$) {
                        Annotation field = arr$[i$];
                        if (field instanceof TableMapping) {
                            entityDescribe.setClassAnnotation(field);
                        }
                    }

                    if (entityDescribe.getClassAnnotation() == null) {
                        throw new DAOException(DAOErrorCode.ENT_INVALID_TABANNO.getCode(), new String[]{entity.getName()});
                    } else {
                        fields = entity.getDeclaredFields();
                        columnMap = new HashMap();
                        columnList = new LinkedList();
                        Field[] var21 = fields;
                        len$ = fields.length;

                        for (i$ = 0; i$ < len$; ++i$) {
                            Field var22 = var21[i$];
                            fieldAnnotations = var22.getDeclaredAnnotations();
                            if (fieldAnnotations.length != 0) {
                                Annotation[] arr$1 = fieldAnnotations;
                                int len$1 = fieldAnnotations.length;

                                for (int i$1 = 0; i$1 < len$1; ++i$1) {
                                    Annotation an = arr$1[i$1];
                                    if (an instanceof ColumnMapping) {
                                        column = (ColumnMapping) an;
                                        columnDescribe = new ColumnDescribe();
                                        columnDescribe.setFieldName(var22.getName());
                                        columnDescribe.setColumnName(column.columnName());
                                        columnDescribe.setColumnType(column.columnType());
                                        columnDescribe.setColumnLength(column.columnLength());
                                        columnDescribe.setColumnIsNotNull(column.columnIsNotNull());
                                        columnDescribe.setDateFormatPattern(column.dateFormatPattern());
                                        columnMap.put(var22.getName(), columnDescribe);
                                        columnList.add(columnDescribe);
                                    }
                                }
                            }
                        }

                        entityDescribe.setColumnMap(columnMap);
                        entityDescribe.setColumnList(columnList);
                        dtoDescMap.put(entity, entityDescribe);
                        return entityDescribe;
                    }
                }
            }
        }
    }
}
