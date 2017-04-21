//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.support;

import com.global.framework.dbutils.annotation.BeanDataHandler;
import com.global.framework.dbutils.annotation.ColumnDescribe;
import com.global.framework.dbutils.annotation.ColumnType.ColumnTypeEnum;
import com.global.framework.dbutils.annotation.EntityDescribe;
import com.global.framework.dbutils.annotation.PrimaryKeyType.PrimaryKeyTypeEnum;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.util.CharUtil;
import com.global.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class SqlHelper {
    public SqlHelper() {
    }

    public String createInsertSql(EntityDescribe entityDescribe, Map<String, Object> dtoMap) throws DAOException {
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();
        String pkType = tm.primaryKeyType();
        String primaryKeyName = tm.primaryKey();
        this.checkTableMapping(entityDescribe.getEntityName(), tableName, pkType, primaryKeyName);
        StringBuffer sql = new StringBuffer();
        StringBuffer valuesSql = new StringBuffer();
        sql.append("INSERT INTO ").append(tableName).append("(");

        for(int i = 0; i < entityDescribe.getColumnList().size(); ++i) {
            ColumnDescribe columnDesc = (ColumnDescribe)entityDescribe.getColumnList().get(i);
            String columnName = columnDesc.getColumnName();
            if(!StringUtils.isBlank(columnName)) {
                if(i != 0) {
                    sql.append(",");
                    valuesSql.append(",");
                }

                sql.append(columnName);
                valuesSql.append("?");
            }
        }

        sql.append(") VALUES (").append(valuesSql).append(")");
        return sql.toString();
    }

    public Object[] getInsertValues(EntityDescribe entityDescribe, Map<String, Object> dtoMap) throws DAOException {
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String primaryKeyName = tm.primaryKey();
        String primaryKeyType = tm.primaryKeyType();
        LinkedList list = new LinkedList();
        if(!PrimaryKeyTypeEnum.None.getCode().equals(primaryKeyType)) {
            String[] i = primaryKeyName.split(",");
            this.checkPrimaryKeyValid(entityDescribe, dtoMap, primaryKeyName, i);
            if(PrimaryKeyTypeEnum.Single.getCode().equals(primaryKeyType)) {
                if(StringUtil.isNullOrEmpty(dtoMap.get(primaryKeyName))) {
                    dtoMap.put(primaryKeyName, PKGenerator.getInstanse().getGUID());
                }
            } else if(PrimaryKeyTypeEnum.Combine.getCode().equals(primaryKeyType)) {
                for(int column = 0; column < i.length; ++column) {
                    if(dtoMap.get(i[column]) == null) {
                        throw new DAOException(DAOErrorCode.ENT_PKVALUE_NULL.getCode(), new String[]{entityDescribe.getEntityName(), primaryKeyName});
                    }
                }
            }
        }

        for(int var11 = 0; var11 < entityDescribe.getColumnList().size(); ++var11) {
            ColumnDescribe var12 = (ColumnDescribe)entityDescribe.getColumnList().get(var11);
            String columnType = var12.getColumnType();
            Object value = dtoMap.get(var12.getFieldName());
            value = BeanDataHandler.trans(value, ColumnTypeEnum.getType(columnType), var12.getDateFormatPattern());
            this.checkColumnLengthAndNull(entityDescribe.getEntityName(), var12, value);
            list.add(value);
        }

        return list.toArray();
    }

    public String createUpdateSql(EntityDescribe entityDescribe, Map<String, Object> dtoMap, boolean isUpdateValueNullField) throws DAOException {
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();
        this.checkTableMapping(entityDescribe.getEntityName(), tableName, tm.primaryKeyType(), tm.primaryKey());
        String[] primaryKeyNames = this.getPrimaryKey(tm);
        if(primaryKeyNames != null && primaryKeyNames.length != 0) {
            boolean i = false;
            StringBuffer sql = new StringBuffer();
            StringBuffer whereSql = new StringBuffer(" WHERE ");
            sql.append("UPDATE ").append(tableName).append(" SET ");

            int var12;
            for(var12 = 0; var12 < entityDescribe.getColumnList().size(); ++var12) {
                ColumnDescribe value = (ColumnDescribe)entityDescribe.getColumnList().get(var12);
                checkColumnMapping(entityDescribe.getEntityName(), value.getFieldName(), value.getColumnName(), value.getColumnType());
                Object value1 = dtoMap.get(value.getFieldName());
                if(StringUtil.isNullOrEmpty(value1)) {
                    if(isUpdateValueNullField) {
                        sql.append(value.getColumnName()).append("=?");
                        sql.append(", ");
                    }
                } else {
                    sql.append(value.getColumnName()).append("=?");
                    sql.append(", ");
                }
            }

            sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1);

            for(var12 = 0; var12 < primaryKeyNames.length; ++var12) {
                if(var12 != 0) {
                    whereSql.append(" AND ");
                }

                whereSql.append(primaryKeyNames[var12]);
                Object var13 = dtoMap.get(primaryKeyNames[var12]);
                if(StringUtil.isNullOrEmpty(var13) && primaryKeyNames.length != 1) {
                    whereSql.append(" IS NULL ");
                } else {
                    whereSql.append("=?");
                }
            }

            sql.append(whereSql.toString());
            return sql.toString();
        } else {
            throw new DAOException(DAOErrorCode.SQL_UPDATE_NOTPK.getCode(), new String[]{entityDescribe.getEntityName()});
        }
    }

    public Object[] getUpdateValues(EntityDescribe entityDescribe, Map<String, Object> dtoMap, boolean isUpdateValueNullField) throws DAOException {
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String[] primaryKeyNames = this.getPrimaryKey(tm);
        ArrayList list = new ArrayList();

        int i;
        ColumnDescribe column;
        for(i = 0; i < entityDescribe.getColumnList().size(); ++i) {
            column = (ColumnDescribe)entityDescribe.getColumnList().get(i);
            String value = column.getColumnType();
            Object columnType = dtoMap.get(column.getFieldName());
            if(StringUtil.isNullOrEmpty(columnType)) {
                if(isUpdateValueNullField) {
                    list.add(columnType);
                }
            } else {
                columnType = BeanDataHandler.trans(columnType, ColumnTypeEnum.getType(value), column.getDateFormatPattern());
                list.add(columnType);
            }
        }

        for(i = 0; i < primaryKeyNames.length; ++i) {
            column = (ColumnDescribe)entityDescribe.getColumnMap().get(primaryKeyNames[i]);
            Object var12 = dtoMap.get(column.getFieldName());
            String var11 = column.getColumnType();
            if(var12 != null) {
                var12 = BeanDataHandler.trans(var12, ColumnTypeEnum.getType(var11), column.getDateFormatPattern());
                list.add(var12);
            }
        }

        return list.toArray();
    }

    public String createDeleteSql(EntityDescribe entityDescribe, Map<String, Object> dtoMap) throws DAOException {
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();
        this.checkTableMapping(entityDescribe.getEntityName(), tableName, tm.primaryKeyType(), tm.primaryKey());
        String[] primaryKeyNames = this.getPrimaryKey(tm);
        if(primaryKeyNames != null && primaryKeyNames.length != 0) {
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM ").append(tableName).append(" WHERE ");

            for(int i = 0; i < primaryKeyNames.length; ++i) {
                if(i != 0) {
                    sql.append(" AND ");
                }

                sql.append(primaryKeyNames[i]);
                Object value = dtoMap.get(primaryKeyNames[i]);
                if(StringUtil.isNullOrEmpty(value) && primaryKeyNames.length != 1) {
                    sql.append(" is null ");
                } else {
                    sql.append("=?");
                }
            }

            return sql.toString();
        } else {
            throw new DAOException(DAOErrorCode.SQL_DELETE_NOTPK.getCode(), new String[]{entityDescribe.getEntityName()});
        }
    }

    public Object[] getPKWhereList(EntityDescribe entityDescribe, Map<String, Object> dtoMap) throws DAOException {
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String[] primaryKeyNames = this.getPrimaryKey(tm);
        ArrayList list = new ArrayList();

        for(int i = 0; i < primaryKeyNames.length; ++i) {
            Object value = dtoMap.get(primaryKeyNames[i]);
            ColumnDescribe column = (ColumnDescribe)entityDescribe.getColumnMap().get(primaryKeyNames[i]);
            String columnType = column.getColumnType();
            if(!StringUtil.isNullOrEmpty(value)) {
                value = BeanDataHandler.trans(value, ColumnTypeEnum.getType(columnType), column.getDateFormatPattern());
                list.add(value);
            }
        }

        return list.toArray();
    }

    public String createQuerySql(EntityDescribe entityDescribe) throws DAOException {
        StringBuffer selectSql = new StringBuffer(this.createSelectSql(entityDescribe, false));
        selectSql.append(" WHERE ");
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String[] primaryKeys = this.getPrimaryKey(tm);

        for(int i = 0; i < primaryKeys.length; ++i) {
            if(i != 0) {
                selectSql.append(" AND ");
            }

            ColumnDescribe column = (ColumnDescribe)entityDescribe.getColumnMap().get(primaryKeys[i]);
            selectSql.append(column.getColumnName() + " = ? ");
        }

        return selectSql.toString();
    }

    public String createSelectSql(EntityDescribe entityDescribe, boolean isOrderBy) throws DAOException {
        TableMapping tm = (TableMapping)entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        Iterator columns = entityDescribe.getColumnMap().keySet().iterator();

        while(columns.hasNext()) {
            String key = (String)columns.next();
            ColumnDescribe column = (ColumnDescribe)entityDescribe.getColumnMap().get(key);
            String columnName = column.getColumnName();
            sql.append(columnName).append(",");
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(" FROM ").append(tableName);
        if(isOrderBy && StringUtils.isNotBlank(tm.primaryKey())) {
            sql.append(" ORDER BY ").append(tm.primaryKey()).append(" ASC ");
        }

        return sql.toString();
    }

    public String createWhereSql(Map<String, Object> condition) throws DAOException {
        StringBuffer sql = new StringBuffer();
        sql.append(" WHERE ");
        Iterator keys = condition.keySet().iterator();

        String orderBy;
        while(keys.hasNext()) {
            orderBy = (String)keys.next();
            if(!orderBy.equals("order by")) {
                if(orderBy.endsWith("!=")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 2)).append("!=?");
                } else if(orderBy.endsWith("<>")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 2)).append("<>?");
                } else if(orderBy.endsWith("%")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 1)).append(" like ?");
                } else if(orderBy.endsWith(">")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 1)).append(">?");
                } else if(orderBy.endsWith(">=")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 2)).append(">=?");
                } else if(orderBy.endsWith("<")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 1)).append("<?");
                } else if(orderBy.endsWith("<=")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 2)).append("<=?");
                } else if(orderBy.endsWith("_in")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 3)).append(" in (").append(condition.get(orderBy)).append(")");
                } else if(orderBy.endsWith("_null")) {
                    sql.append(orderBy.substring(0, orderBy.length() - 5)).append(" is ?");
                } else {
                    sql.append(orderBy).append("=?");
                }

                sql.append(" AND ");
            }
        }

        sql.delete(sql.length() - 5, sql.length());
        orderBy = (String)condition.remove("order by");
        if(condition.isEmpty()) {
            sql = new StringBuffer();
        }

        if(orderBy != null) {
            sql.append(" order by ").append(orderBy);
        }

        return sql.toString();
    }

    public String[] getPrimaryKey(TableMapping tm) {
        String[] primaryKeyNames = null;
        if(PrimaryKeyTypeEnum.Single.getCode().equals(tm.primaryKeyType())) {
            primaryKeyNames = new String[]{tm.primaryKey()};
        } else if(PrimaryKeyTypeEnum.Combine.getCode().equals(tm.primaryKeyType())) {
            primaryKeyNames = tm.primaryKey().split(",");
        }

        return primaryKeyNames;
    }

    private void checkTableMapping(String entityName, String tableName, String pkType, String primaryKeyName) {
        if(StringUtils.isBlank(tableName)) {
            throw new DAOException(DAOErrorCode.ENT_TABNAME_NULL.getCode(), new String[]{entityName});
        } else if(StringUtils.isBlank(pkType)) {
            throw new DAOException(DAOErrorCode.ENT_PKTYPE_NULL.getCode(), new String[]{entityName});
        } else if(!PrimaryKeyTypeEnum.isCheckTypeExist(pkType)) {
            throw new DAOException(DAOErrorCode.ENT_INVALID_PKTYPE.getCode(), new String[]{entityName, pkType});
        } else if(!PrimaryKeyTypeEnum.None.getCode().equals(pkType) && StringUtils.isBlank(primaryKeyName)) {
            throw new DAOException(DAOErrorCode.ENT_PK_NULL.getCode(), new String[]{entityName});
        } else {
            if(PrimaryKeyTypeEnum.Combine.getCode().equals(pkType)) {
                String[] pkNames = primaryKeyName.split(",");
                if(pkNames.length < 2) {
                    throw new DAOException(DAOErrorCode.ENT_NOT_COMBINE_PK.getCode(), new String[]{entityName, primaryKeyName});
                }
            }

        }
    }

    private void checkPrimaryKeyValid(EntityDescribe entityDescribe, Map<String, Object> dtoMap, String primaryKeyName, String[] primaryKeyNames) {
        for(int i = 0; i < primaryKeyNames.length; ++i) {
            if(!dtoMap.containsKey(primaryKeyNames[i])) {
                throw new DAOException(DAOErrorCode.ENT_INVALID_PK.getCode(), new String[]{entityDescribe.getEntityName(), primaryKeyName});
            }
        }

    }

    public static void checkColumnMapping(String entityName, String fieldName, String columnName, String columnType) {
        if(StringUtils.isBlank(columnName)) {
            throw new DAOException(DAOErrorCode.ENT_COLUMNNAME_NULL.getCode(), new String[]{entityName, fieldName});
        } else if(!StringUtils.isNotBlank(columnType) || !ColumnTypeEnum.isCheckTypeExist(columnType)) {
            throw new DAOException(DAOErrorCode.ENT_INVALID_COLUMTYPE.getCode(), new String[]{entityName, fieldName, columnType});
        }
    }

    private void checkColumnLengthAndNull(String entityName, ColumnDescribe column, Object value) {
        if(!"".equals(column.getColumnLength())) {
            if(ColumnTypeEnum.DOUBLE.getTypeCode().equals(column.getColumnType()) || ColumnTypeEnum.BigDecimal.getTypeCode().equals(column.getColumnType())) {
                String[] dLens = column.getColumnLength().split(",");
                if(dLens != null && dLens.length != 2) {
                    throw new DAOException(DAOErrorCode.ENT_INVALID_COLUMN_LENGTH.getCode(), new String[]{entityName, column.getColumnName()});
                }

                int dLen1 = Integer.valueOf(dLens[0]).intValue();
                int dLen2 = Integer.valueOf(dLens[1]).intValue();
                Double dValue = (Double)value;
                String sValue = String.valueOf(dValue);
                String[] sValues = sValue.split("\\.");
                if(sValues[0].length() > dLen1) {
                    throw new DAOException(DAOErrorCode.ENT_COLUMN_VALUE_THANLENGTH.getCode());
                }

                if(sValues.length == 2 && sValues[1].length() > dLen2) {
                    throw new DAOException(DAOErrorCode.ENT_COLUMN_VALUE_THANLENGTH.getCode());
                }
            }

            if(CharUtil.getChineseStrLen((String)value) > Integer.parseInt(column.getColumnLength())) {
                throw new DAOException(DAOErrorCode.ENT_COLUMN_VALUE_THANLENGTH.getCode(), new String[]{entityName, column.getColumnName()});
            }
        }

        if(column.getColumnIsNotNull() && StringUtil.isNullOrEmpty(value)) {
            throw new DAOException(DAOErrorCode.ENT_COLUMN_VALUE_NULL.getCode(), new String[]{entityName, column.getColumnName()});
        }
    }

    public static String getArrayToString(Object[] args) {
        if(args == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            boolean done = false;

            for(int i = 0; i < args.length; ++i) {
                if(done) {
                    sb.append(",");
                }

                sb.append(args[i]);
                done = true;
            }

            return sb.toString();
        }
    }
}
