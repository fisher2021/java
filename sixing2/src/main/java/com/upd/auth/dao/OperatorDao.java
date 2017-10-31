package com.upd.auth.dao;

import com.upd.auth.entity.Operator;
import com.upd.common.basis.dao.BaseDao;

/**
 * 管理员持久层
 * @author cao.xin
 * 2016年12月29日
 */
public interface OperatorDao extends BaseDao<Operator, Integer> {

	/**
	 * 根据账号查询管理员
	 * @param account
	 * @return
	 */
	Operator getByAccount(String account);
	
	/**
	 * 根据账号和密码查询管理员
	 * @param account
	 * @param password
	 * @return
	 */
	Operator getByAccountAndPassword(String account, String password);

	

}
