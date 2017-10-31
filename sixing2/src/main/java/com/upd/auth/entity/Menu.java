package com.upd.auth.entity;

import com.upd.common.basis.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * 菜单权限实体
 * @author cao.xin
 * 2016年12月29日
 */
@Entity
@Table
public class Menu extends BaseEntity {
	@Column(length = 50)
	private String name;//菜单名称
	@Column(length = 50)
	private String code;//菜单编码
	private String display;//菜单显示样式
	@ManyToOne
	private Menu parent;//父菜单
	@OneToMany(mappedBy="parent")
	private List<Menu> children;//子菜单
	private String targetUrl;//菜单链接
	private Integer rank;//菜单排序
    @ManyToMany(mappedBy = "menuList",cascade = CascadeType.ALL)
    private List<Role> role = new ArrayList<Role>();//角色
    private Integer isShowAll;//0 全部查看 1:admin账号查看

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public Integer getIsShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(Integer isShowAll) {
        this.isShowAll = isShowAll;
    }
}
