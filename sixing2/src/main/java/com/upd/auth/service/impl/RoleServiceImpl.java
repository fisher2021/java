package com.upd.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.upd.auth.dao.RoleDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upd.auth.entity.Menu;
import com.upd.auth.entity.Role;
import com.upd.auth.service.MenuService;
import com.upd.auth.service.RoleService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色业务层实现
 * @author cao.xin
 * 2016年12月29日
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements RoleService {
	private RoleDao roleDao;
	@Autowired
	private MenuService menuService;

	public RoleDao getRoleDao() {
		return roleDao;
	}
	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.baseDao = roleDao;
		this.roleDao = roleDao;
	}
	
	@Override
	public Pagination getRoleListByPage(Pagination page, String name) {
		String hql = "FROM Role where name LIKE ?";
		page = roleDao.findbypage(page, hql, "%"+name+"%");
		return page;
	}
	
	@Override
	public Role addOrEditRole(Role roleVo) {
		if(roleVo.getId() != null){
			//执行更新操作
			Role role = roleDao.get(roleVo.getId());
			if(role != null){
				role.setName(roleVo.getName());
				role.initTime();
				//更新到数据库
				roleDao.update(role);
				return role;
			}
		}else{
			//执行保存操作
			roleVo.initTime();
			//更新到数据库
			roleDao.save(roleVo);
			return roleVo;
			
		}
		return null;
	}
	
	@Override
	public void editRoleMenu(Integer id, String menus) {
		Role role = roleDao.get(id);
		
		if(role != null){
			//获取菜单列表
			List<Menu> menuList = new ArrayList<Menu>();
			if(StringUtils.isNotBlank(menus)){
				String[] strings = menus.split(",");
				for (String string : strings) {
					Menu menu = menuService.get(Integer.parseInt(string));
					if(menu != null){
						menuList.add(menu);
					}
				}
			}
			//维护角色菜单关系
			role.setMenuList(menuList);
			//更新到数据库
			roleDao.update(role);
		}
	}
	
	@Override
	public List<Role> findAll() {
		String hql = "FROM Role";
		return roleDao.find(hql);
	}
	
	@Override
	public Role getByEmerg(Integer id) {
		Role role = roleDao.get(id);
		//强制加载菜单列表
		Hibernate.initialize(role.getMenuList());

		return role;
	}
	
	
}
