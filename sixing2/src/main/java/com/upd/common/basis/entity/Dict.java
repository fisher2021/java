package com.upd.common.basis.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Dict extends BaseEntity {
	// 字典层级
	String seqNo;
	// 中文层级
	String seqCn;
	//数据字典 id
	String  dictId;
	// 数据字典名称
	String dictName;
	//字典值
	String value;
	//所属字典类型
	@ManyToOne
	@JoinColumn(name="dictType" )
	DictType dictType;
	//父字典项
	@ManyToOne
	@JoinColumn(name="parent" )
	Dict parent;
	//子字典项
	@JSONField(serialize=false)
	@OneToMany(mappedBy = "parent" )
	@JsonIgnore(value = true)
	List<Dict>	subdicts;
	// 备注
	String remark;
	//排序
	int sort;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public List<Dict> getSubdicts() {
		return subdicts;
	}

	public void setSubdicts(List<Dict> subdicts) {
		this.subdicts = subdicts;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Dict() {
		super();
	}
	public Dict(Integer id) {
		super();
		this.id = id;
	}
	public Dict getParent() {
		return parent;
	}
	public void setParent(Dict parent) {
		this.parent = parent;
	}
	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getSeqCn() {
		return seqCn;
	}

	public void setSeqCn(String seqCn) {
		this.seqCn = seqCn;
	}

	public DictType getDictType() {
		return dictType;
	}

	public void setDictType(DictType dictType) {
		this.dictType = dictType;
	}


}
