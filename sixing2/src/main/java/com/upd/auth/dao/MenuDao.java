package com.upd.auth.dao;

import com.upd.auth.entity.Menu;
import com.upd.common.basis.dao.BaseDao;

import java.util.List;

/**
 * 菜单持久层
 * @author cao.xin
 * 2016年12月29日
 */
public interface MenuDao extends BaseDao<Menu, Integer> {

	/**
	 * 查询子菜单
	 * @param menu
	 * @return
	 */
	List<Menu> getChildren(Menu menu);

}
