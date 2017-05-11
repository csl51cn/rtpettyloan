package com.global.framework.system.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.OperateDao;
import com.global.framework.system.domain.Operate;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.ZTreeNode;

/**
 * @author chen.feng
 * @version v1.0
 * @date 2013-3-25
 */
@Repository("operateDao")
public class OperateDaoImpl extends BaseDaoSupport implements OperateDao {

    public Operate saveOperate(Operate operate) throws BaseException {
        return super.insert(operate);
    }

    public Operate updateOperte(Operate operate) throws BaseException {
        super.update(operate);
        return operate;
    }

    public void deleteOperate(LinkedList<Operate> operateList) throws BaseException {
        super.batchDelete(operateList);
    }

    public PageBean queryOperateForPage(Operate operate, PageBean page)
            throws BaseException {
        StringBuffer sql = new StringBuffer(
                "select t.*,(select menuName from dc_sys_menu where menuId=t.menuId) menuName from dc_sys_operate t where 1=1 ");
        List<Object> list = new ArrayList<Object>();
        if (StringUtils.isNotBlank(operate.getMenuId())) {
            sql.append(" and t.menuid = ? ");
            list.add(operate.getMenuId());
        }
        if (StringUtils.isNotBlank(operate.getOperateName())) {
            sql.append(" and t.operateName = ? ");
            list.add(operate.getOperateName());
        }
        return super.findForPage(sql.toString(), list.toArray(), page,
                Operate.class);
    }

    @SuppressWarnings("unchecked")
    public List<ZTreeNode> getRightTree() throws BaseException {
        //权限类型 1:代表菜单, 2:代表按钮 3:工作流权限
        String sql = "select * from ("
                + "select a.menuId treeId,a.parentMenuId pid,a.menuName treeName,a.sortno,'1' righttype from dc_sys_menu a "//菜单
                //+ "union "
                //+ "select b.operateId treeId,b.menuId pid,b.operateName treeName,b.sortno, '2' righttype from dc_sys_operate b where b.ischeck='Y' "//按钮
                + "union "
                + "select CAST(c.privid AS VARCHAR) as treeId,c.menuId pid,CASE WHEN c.opeid = 1 THEN '经办' WHEN c.opeid = 2 THEN '复核' WHEN c.opeid = 3 THEN '授权' END AS treeName,1 sortno, '3' righttype from dc_wfl_tradeprivilege c "
                + "  where c.tradeno in (select tradeno from dc_wfl_tradetemplate where isused = 'Y') "//工作流权限
                + ") abc order by abc.sortno asc ";
        return (List<ZTreeNode>) super.findForListBySql(sql, null,
                ZTreeNode.class);
    }

    public Map<String, Object> queryTradeTemplateByPrivID(String privId)
            throws BaseException {
        String sql = "select p.opeid,t.ishandle,t.ischeck,t.isauth from DC_WFL_TRADETEMPLATE t,DC_WFL_TRADEPRIVILEGE p " +
                "where t.tradeno=p.tradeno and p.privid=? ";
        return super.getJdbcTemplate().queryForMap(sql, new Object[]{privId});
    }

    @SuppressWarnings("unchecked")
    public List<RoleRight> getRightsByRoleId(String roleId)
            throws BaseException {
        String sql = "select * from dc_sys_roleright t where t.roleid = ? ";
        return (List<RoleRight>) super.findForListBySql(sql,
                new Object[]{roleId}, RoleRight.class);
    }

    @SuppressWarnings("unchecked")
    public List<Operate> getOperateList() throws BaseException {
        return (List<Operate>) super.findForList(Operate.class);
    }

    @SuppressWarnings("unchecked")
    public List<Operate> getNoCheckOperateList() throws BaseException {
        String sql = "select * from dc_sys_operate t where t.ischeck = 'N' ";
        return (List<Operate>) super.findForListBySql(sql, null, Operate.class);
    }
}
