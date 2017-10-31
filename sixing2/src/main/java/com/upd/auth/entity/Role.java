package com.upd.auth.entity;

import com.upd.common.basis.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * 角色实体类
 * @author cao.xin
 * 2016年12月29日
 */
@Entity
public class Role extends BaseEntity {
	@Column(length = 50)
	private String name;//角色名
	@ManyToMany
    @JoinTable(name="role_menu",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="menu_id")})
	private List<Menu> menuList = new ArrayList<Menu>();//角色包含的权限

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

}
