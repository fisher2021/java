package com.upd.common.util;

import java.io.Serializable;
import java.util.List;
public class JsonTreeNode implements Serializable {
	private static final long serialVersionUID = 1;
	private String id;
	private String text;
	private boolean leaf;
	private String cls;
	private List<JsonTreeNode> children;
	private boolean expandable;
	private String type;
	private String action;
	private String iconCls;
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public List<JsonTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<JsonTreeNode> children) {
		this.children = children;
	}

	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}

	

}
