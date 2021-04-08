package com.huimi.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Ztree节点数据封装
 * @author 陌上人
 */
public class ZTreeNode implements Serializable {
	
	/**
	 * 节点id
	 */
	private String id;

	/**
	 * 节点名称
	 */
	private String name;

	/**
	 * 是否上级节点
	 */
	private boolean isParent;
	
	/**上级节点ID
	 */
	private String pId;

	/**
	 * 是否选中
	 */
	private boolean checked;

	/**
	 * 是否选中
	 */
	private boolean nocheck;
	
	/**
	 * 是否可以选择
	 */
	private boolean chkDisabled;
	
	/**
	 * 节点图标
	 */
	private String icon;
	
	/**
	 * 是否展开
	 */
	private boolean open;
	
	/**
	 * 子节点数据
	 */
	private List<ZTreeNode> children;
	
	/**
	 * 子节点个数
	 */
	private int childrenNum;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<ZTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ZTreeNode> children) {
		this.children = children;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public int getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(int childrenNum) {
		this.childrenNum = childrenNum;
	}
	
	
	
}
