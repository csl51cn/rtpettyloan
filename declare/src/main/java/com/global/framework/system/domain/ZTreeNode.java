package com.global.framework.system.domain;

import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2011-3-2 下午11:30:33
 * @version v1.0
 */
public class ZTreeNode extends Entity {

	private static final long serialVersionUID = 8959436210560143380L;
	
	private String treeId;
	private String treeName;
	private String pid;
	private boolean checked = true;
	private String rightType;//权限类型 1:代表菜单, 2:代表按钮
	
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public String getTreeName() {
		return treeName;
	}
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	
}
