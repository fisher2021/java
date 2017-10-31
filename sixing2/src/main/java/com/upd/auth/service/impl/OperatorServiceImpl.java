package com.upd.auth.service.impl;

import com.upd.auth.dao.OperatorDao;
import com.upd.business.form.ArticleForm;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.util.security.MD5Utils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upd.auth.entity.Operator;
import com.upd.auth.entity.Role;
import com.upd.auth.service.OperatorService;
import com.upd.auth.service.RoleService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;

/**
 * 管理员业务层实现
 * @author cao.xin
 * 2016年12月29日
 */
@Service
public class OperatorServiceImpl extends BaseServiceImpl<Operator, Integer> implements OperatorService {
	private OperatorDao operatorDao;
	@Autowired
	private RoleService roleService;

	public OperatorDao getOperatorDao() {
		return operatorDao;
	}
	@Autowired
	public void setOperatorDao(OperatorDao operatorDao) {
		this.baseDao = operatorDao;
		this.operatorDao = operatorDao;
	}
	
	@Override
	public Pagination getOperatorListByPage(Pagination page, String name) {
		String hql = "FROM Operator WHERE name LIKE ?";
		page = operatorDao.findbypage(page, hql, "%"+name+"%");
		return page;
	}

    @Override
    public void addOperator(Operator operator) {
        operator.setPassword(MD5Utils.getMD5(operator.getAccount()+operator.getPassword()));
        operator.initTime();
        operatorDao.save(operator);
    }

    @Override
    public Operator editOperator(Operator operator) {
        Operator operator1 = operatorDao.get(operator.getId());
        if (operator.getAccount() != null && !operator.getAccount().equals("")){
            operator1.setAccount(operator.getAccount());
        }
        if (operator.getName() != null && !operator.getName().equals("")){
            operator1.setName(operator.getName());
        }
        if (operator.getRole() != null && !operator.getRole().equals("")){
            operator1.setRole(operator.getRole());
        }
        if (operator.getOrg() != null && !operator.getOrg().equals("")){
            operator1.setOrg(operator.getOrg());
        }
        operator1.initTime();
        operatorDao.update(operator1);
        return operator1;
    }

    @Override
	public boolean operatorOnly(Operator operator) {
		Operator oper = operatorDao.getByAccount(operator.getAccount());
		if(oper == null || oper.getId().equals(operator.getId())){
			return true;
		}
		return false;
	}
	
	@Override
	public Operator doLogin(Operator operator) {
		operator = operatorDao.getByAccountAndPassword(operator.getAccount(),operator.getPassword());
		if(operator != null){
			Role role = operator.getRole();
			if(role != null){
				//强制加载菜单列表
				Hibernate.initialize(role.getMenuList());
			}
		}
		return operator;
	}
	
	@Override
	public Boolean changePwd(Integer id, String password, String oldPwd) {
		Operator operator = operatorDao.get(id);
		if(operator != null){
			if(operator.getPassword().equals(MD5Utils.getMD5(operator.getAccount()+oldPwd))){
				operator.setPassword(MD5Utils.getMD5(operator.getAccount()+password));
				operator.initTime();
				operatorDao.update(operator);
				return true;
			}else {
			    throw new BusinessException(RestErrorCode.PARAM,"原密码错误！");
            }
		}
		return false;
	}
	
	@Override
	public void resetPwd(Integer id, String password) {
		Operator operator = operatorDao.get(id);
		if(operator != null){			
			operator.setPassword(MD5Utils.getMD5(operator.getAccount()+password));
			operator.initTime();
			operatorDao.update(operator);
		}
	}

    @Override
    public Operator getOperator(String code) {
        return operatorDao.findOne("from Operator where org.code = ?",code);
    }


}
