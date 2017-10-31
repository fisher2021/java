package com.upd.auth.service.impl;

import java.util.List;

import com.upd.auth.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upd.auth.entity.Menu;
import com.upd.auth.service.MenuService;
import com.upd.common.basis.service.impl.BaseServiceImpl;

/**
 * 菜单业务层实现
 * @author cao.xin
 * 2016年12月29日
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Integer> implements MenuService {
	private MenuDao menuDao;

	public MenuDao getMenuDao() {
		return menuDao;
	}
	@Autowired
	public void setMenuDao(MenuDao menuDao) {
		this.baseDao = menuDao;
		this.menuDao = menuDao;
	}
	
	@Override
	public Menu addOrEditMenu(Menu menuVo) {
		if(menuVo.getId() != null){
			//执行更新操作
			Menu menu = menuDao.get(menuVo.getId());
			if(menu!=null){
				menu.setName(menuVo.getName());
				menu.setCode(menuVo.getCode());
				menu.setDisplay(menuVo.getDisplay());
				menu.setTargetUrl(menuVo.getTargetUrl());
				menu.setRank(menuVo.getRank());
				menu.initTime();
				//更新到数据库
				menuDao.update(menu);
				return menu;
			}
		}else{
			//执行保存操作
			menuVo.setParent(menuDao.get(menuVo.getParent().getId()));
			menuVo.initTime();
			//保存到数据库
			menuDao.save(menuVo);
			return menuVo;
		}
		return null;
	}
	
	@Override
	public List<Menu> getChildren(Menu menu) {
		menu = menuDao.get(menu.getId());
		return menuDao.getChildren(menu);
	}
	
	@Override
	public List<Menu> findAll() {
		String hql = "FROM Menu ORDER BY rank";
		return menuDao.find(hql);
	}
}
