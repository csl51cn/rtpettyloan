package com.global.framework.system.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.OperateDao;
import com.global.framework.system.domain.Operate;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.ZTreeNode;
import com.global.framework.system.service.OperateService;
import com.global.framework.system.web.common.CacheService;

/**
 * @author chen.feng
 * @date 2013-3-25
 * @version v1.0
 */
@Service("operateService")
public class OperateServiceImpl implements OperateService {

	@Autowired
	private OperateDao operateDao;

	public Operate saveOrUpdateOperate(Operate operate) throws BaseException {
		if (operate != null && StringUtils.isNotBlank(operate.getOperateId())) {
			operate = this.operateDao.updateOperte(operate);
		}
		operate = this.operateDao.saveOperate(operate);
		CacheService.getInstance().loadRightAll();
		return operate;
	}

	
	public void deleteOperate(String operateIds) throws BaseException {
		LinkedList<Operate> operateList = new LinkedList<Operate>();
		JSONArray jsonArray = JSON.parseArray(operateIds);
		for (Object obj : jsonArray) {
			JSONObject jsonObj = (JSONObject) obj;
			Operate operate = new Operate();
			operate.setOperateId(jsonObj.getString("operateId"));
			operateList.add(operate);
		}
		this.operateDao.deleteOperate(operateList);
		CacheService.getInstance().loadRightAll();
	}

	
	public PageBean queryOperateForPage(Operate operate, PageBean page)
			throws BaseException {
		return this.operateDao.queryOperateForPage(operate, page);
	}

	
	public List<String> getRightTree(String roleId) throws BaseException {
		// 所有菜单及按钮整合树
		List<ZTreeNode> list = this.operateDao.getRightTree();

		// 查询角色已分配了的权限
		List<RoleRight> rightList = this.operateDao.getRightsByRoleId(roleId);

		List<String> rtList = new LinkedList<String>();
		for (ZTreeNode rt : list) {
			StringBuffer sb = new StringBuffer();
			boolean isShow = true;
			//判断该交易是否有经办，复核，授权步骤
			//通过权限ID去查询wfl_tradetemplate
			if ("3".equals(rt.getRightType())) {
				Map<String, Object> map = this.operateDao.queryTradeTemplateByPrivID(rt.getTreeId());
				
				if ("1".equals(map.get("OPEID")) && !"Y".equals(map.get("ISHANDLE"))) {
					isShow = false;
				}
				if ("2".equals(map.get("OPEID")) && !"Y".equals(map.get("ISCHECK"))) {
					isShow = false;
				}
				if ("3".equals(map.get("OPEID")) && !"Y".equals(map.get("ISAUTH"))) {
					isShow = false;
				}
			}
			
			if (isShow) {
				sb.append("{").append("id:" + rt.getTreeId() + ",")
				.append("pId:" + rt.getPid() + ",")
				.append("name:\"" + rt.getTreeName() + "\",")
				.append("rightType:\"" + rt.getRightType() + "\",")
				.append("open:true");
				for (RoleRight rm : rightList) {
					String rightId = rm.getRightId();
					if (rt.getTreeId().equals(rightId)) {
						sb.append(",checked:true");
					}
				}
				sb.append("}");
				rtList.add(sb.toString());
			}
		}
		return rtList;
	}

	
	public List<Operate> getOperateList() throws BaseException {
		return this.operateDao.getOperateList();
	}

	
	public List<Operate> getNoCheckOperateList() throws BaseException {
		return this.operateDao.getNoCheckOperateList();
	}
}
