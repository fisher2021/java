package com.upd.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class JsonTreeCheckedNode implements Serializable {

	private static final long serialVersionUID = 1;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String id; // id
	private String text; // 显示名称
	private boolean leaf; // 叶子节点
	private String cls; // 设置显示样式：
	private List<JsonTreeCheckedNode> children;//
	private boolean expandable; //
	private String type; // 类型
	private String action; // 请求路径
	private boolean checked; // 是否选中 在select中使用
	private String iconCls; //
	private String nodeSeq;
	private String entityId;
	private String subType;	//子类型

	private Map<String, String> param = new HashMap<String, String>();


	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	public String getAction() {
		return action;
	}

	public boolean getChecked() {
		return checked;
	}


	public String getCls() {
		return cls;
	}

	public String getIconCls() {
		return iconCls;
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public boolean isExpandable() {
		return expandable;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<JsonTreeCheckedNode> getChildren() {
		return children;
	}

	public void setChildren(List<JsonTreeCheckedNode> children) {
		this.children = children;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNodeSeq() {
		return nodeSeq;
	}

	public void setNodeSeq(String nodeSeq) {
		this.nodeSeq = nodeSeq;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}


}
