package com.upd.auth.dao.impl;

import java.util.List;

import com.upd.auth.dao.MenuDao;
import com.upd.auth.entity.Menu;
import org.springframework.stereotype.Repository;


import com.upd.common.basis.dao.impl.BaseDaoImpl;

/**
 * 菜单持久层实现
 * @author cao.xin
 * 2016年12月29日
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu, Integer> implements MenuDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getChildren(Menu menu) {
		if(menu == null){
			String hql = "FROM Menu WHERE parent = null ORDER BY rank";
			return (List<Menu>) this.find(hql);
		}else{
			String hql = "FROM Menu WHERE parent = :parent ORDER BY rank";
			return (List<Menu>) this.getHibernateTemplate().findByNamedParam(hql,"parent", menu);
		}
	}
	
}
