package com.upd.auth.service;

import java.util.List;

import com.upd.auth.entity.Menu;
import com.upd.common.basis.service.BaseService;

/**
 * 菜单业务层接口
 * @author cao.xin
 * 2016年12月29日
 */
public interface MenuService extends BaseService<Menu, Integer> {
	
	/**
	 * 新增或修改菜单信息
	 * @param menuVo
	 * @return
	 */
	Menu addOrEditMenu(Menu menuVo);

	/**
	 * 查询子菜单
	 * @param menu
	 * @return
	 */
	List<Menu> getChildren(Menu menu);

	/**
	 * 查询所有菜单
	 * @return
	 */
	List<Menu> findAll();

}
