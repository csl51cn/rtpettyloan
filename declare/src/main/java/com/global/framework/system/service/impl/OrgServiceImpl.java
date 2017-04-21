package com.global.framework.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.OrgDao;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.TreeNode;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.OrgService;
import com.global.framework.system.web.common.CacheService;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-7 下午11:38:58
 * @version v1.0
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao orgDao;

	
	public List<Org> getOrgList() throws BaseException {
		return this.orgDao.getOrgList();
	}

	
	public PageBean queryOrgForPage(Org org, PageBean page) throws BaseException {
		return this.orgDao.queryOrgForPage(org, page);
	}

	
	public Org saveOrUpdate(Org org) throws BaseException {
		Org o = null;
		if (StringUtils.isNotBlank(org.getOrgId())) {
			o = this.orgDao.updateOrg(org);
		} else {
			//生成机构No:规则1000+机构层级+顺序号
			String orgNo = "1000";
			orgNo += org.getOrgLevel();
			String maxOrgNo = this.orgDao.getMaxOrgNoByLevel(org.getOrgLevel());
			if (StringUtils.isNotBlank(maxOrgNo)) {
				orgNo = String.valueOf(Integer.valueOf(maxOrgNo) + 1);
			} else {
				orgNo += "00001";
			}
			org.setOrgId(orgNo);
			org.setStatus("Y");
			o = this.orgDao.insertOrg(org);
		}
		CacheService.getInstance().loadOrgAll();
		return o;
	}

	
	public void delete(Org org) throws BaseException {
		this.orgDao.deleteOrg(org);
		CacheService.getInstance().loadOrgAll();
	}

	
	public List<TreeNode> loadOrgTree() throws BaseException {
//		List<Org> orgList = CacheService.getOrgCacheList();
		List<Org> orgList = this.getOrgList();
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		boolean isFirst = true;
		for (Org org : orgList) {
			if (org.getParentOrgId() == null || "".equals(org.getParentOrgId())){
				TreeNode node = new TreeNode();
				List<TreeNode> children = getChildNodes(org.getOrgId(), orgList);
				node.setId(org.getOrgId());
				node.setText(org.getOrgName());
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("bankCode", org.getBankCode());
				attributes.put("orgLevel", org.getOrgLevel());
				node.setAttributes(attributes);
				if(children != null && children.size() > 0){
					node.setChildren(children);
					node.setState(isFirst?"open":"closed");
				}
				treeNodes.add(node);
				isFirst = false;
				break;
			}
		}
		return treeNodes;
	}

	private List<TreeNode> getChildNodes(String orgNo, List<Org> orgList) throws BaseException {
		List<TreeNode> childNodes = new ArrayList<TreeNode>();
		for (Org org : orgList) {
			if (orgNo.equals(org.getParentOrgId())) {
				List<TreeNode> children = getChildNodes(org.getOrgId(), orgList);
				TreeNode node = new TreeNode();
				node.setId(org.getOrgId());
				node.setText(org.getOrgName());
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("bankCode", org.getBankCode());
				attributes.put("orgLevel", org.getOrgLevel());
				node.setAttributes(attributes);
				if(children != null && children.size() > 0){
					node.setChildren(children);
					node.setState("closed");
				}
				childNodes.add(node);
			}
		}
		return childNodes;
	}

	
	public Org getOrgInfo(Org org) throws BaseException {
		return this.orgDao.getOrgInfo(org);
	}
	
	
	public Set<Org> getRightOrgList(User user) throws BaseException {
		Set<Org> rightOrgList = new HashSet<Org>();
		List<DataRight> dataRightList = user.getRoleDataRight();
		if (dataRightList != null && dataRightList.size() > 0) {
			for (DataRight dataRight : dataRightList) {
				if (DataRight.RIGHT_TYPE_SELF.equals(dataRight.getRightType()) 
						|| DataRight.RIGHT_TYPE_SELFORG.equals(dataRight.getRightType())) {
					Org org = CacheService.getOrgById(user.getOrgId());
					rightOrgList.add(org);
				} else if (DataRight.RIGHT_TYPE_SELFORG_CHILDORG.equals(dataRight.getRightType())) {
					Org org = CacheService.getOrgById(user.getOrgId());
					rightOrgList.add(org);
					//查询下级机构
					List<Org> allOrgList = CacheService.getOrgCacheList();
					List<Org> subOrgList = new ArrayList<Org>();
					getAllSubOrgList(user.getOrgId(), allOrgList, subOrgList);
					rightOrgList.addAll(subOrgList);
				} else if (DataRight.RIGHT_TYPE_CHECKORG.equals(dataRight.getRightType())) {
					String rightOrgNoStr = dataRight.getOrgNoList();
					if (StringUtils.isNotBlank(rightOrgNoStr)) {
						String[] orgNos = rightOrgNoStr.split(",");
						for (int i = 0; i < orgNos.length; i++) {
							Org org = CacheService.getOrgById((orgNos[i]));
							rightOrgList.add(org);
						}
					}
				}
			}
		}
		return rightOrgList;
	}
	
	private void getAllSubOrgList(String orgId, List<Org> orgList, List<Org> subOrgList) {
		for (Org org : orgList) {
			if (orgId.equals(org.getParentOrgId())) {
				subOrgList.add(org);
				getAllSubOrgList(org.getOrgId(), orgList, subOrgList);
			}
		}
	}
}
