package com.upd.auth.service;

import com.upd.auth.entity.Operator;
import com.upd.business.form.ArticleForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 管理员业务层
 * @author cao.xin
 * 2016年12月29日
 */
public interface OperatorService extends BaseService<Operator, Integer> {

	/**
	 * 根据条件分页查询管理员
	 * @param page
	 * @param name
	 * @return
	 */
	Pagination getOperatorListByPage(Pagination page, String name);

    /**
     * 新增管理员信息
     *
     * @param operator
     * @return
     */
    void addOperator(Operator operator);
    /**
     * 修改管理员信息
     *
     * @param operator
     * @return
     */
    Operator editOperator(Operator operator);
	
	/**
	 * 该账号管理员是否已存在
	 * @param operator
	 * @return
	 */
	boolean operatorOnly(Operator operator);

	/**
	 * 管理登录逻辑
	 * @param operator
	 */
	Operator doLogin(Operator operator);

	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @param oldPwd 
	 */
	Boolean changePwd(Integer id, String password, String oldPwd);

	/**
	 * 重置密码
	 * @param id
	 * @param password 
	 */
	void resetPwd(Integer id, String password);

    /**
     * 通过组织查询管理员
     * @param code
     * @return
     */
	Operator getOperator(String code);

}
