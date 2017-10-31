package com.upd.auth.dao.impl;

import com.upd.auth.dao.RoleDao;
import com.upd.auth.entity.Role;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;



/**
 * 角色持久层实现
 * @author cao.xin
 * 2016年12月29日
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, Integer> implements RoleDao {

}
