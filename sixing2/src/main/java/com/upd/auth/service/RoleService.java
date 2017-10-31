package com.upd.auth.service;

import java.util.List;

import com.upd.auth.entity.Role;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 角色业务层
 * @author cao.xin
 * 2016年12月29日
 */
public interface RoleService extends BaseService<Role, Integer> {

	/**
	 * 根据名称条件分页查询
	 * @param page
	 * @param name
	 * @return
	 */
	Pagination getRoleListByPage(Pagination page, String name);

	/**
	 * 新增或修改角色信息
	 * @param role
	 * @return
	 */
	Role addOrEditRole(Role role);

	/**
	 * 编辑角色菜单
	 * @param id
	 * @param menus
	 */
	void editRoleMenu(Integer id, String menus);

	/**
	 * 获取所有角色
	 * @return
	 */
	List<Role> findAll();

	/**
	 * 根据组件查询角色，并强制加载角色菜单
	 * @param id
	 * @return
	 */
	Role getByEmerg(Integer id);

}
