package com.upd.auth.dao.impl;

import java.util.List;

import com.upd.auth.dao.OperatorDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.upd.auth.entity.Operator;
import com.upd.common.basis.dao.impl.BaseDaoImpl;

/**
 * 管理员持久层实现
 * @author cao.xin
 * 2016年12月29日
 */
@Repository
public class OperatorDaoImpl extends BaseDaoImpl<Operator, Integer> implements OperatorDao {

	@Override
	public Operator getByAccount(String account) {
		String hql = "FROM Operator WHERE account = :account";
		List<Operator> operatorList = (List<Operator>) this.getHibernateTemplate().findByNamedParam(hql, "account", account);
		if(CollectionUtils.isNotEmpty(operatorList)){
			return operatorList.get(0);
		}
		return null;
	}

	@Override
	public Operator getByAccountAndPassword(String account, String password) {
		String hql = "FROM Operator WHERE account = ? AND password = md5(?)";
		return findOne(hql,account,account+password);
	}
}
