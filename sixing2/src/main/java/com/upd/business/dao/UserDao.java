package com.upd.business.dao;

import com.upd.business.entity.User;
import com.upd.common.basis.dao.BaseDao;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface UserDao extends BaseDao<User,Integer> {
    /**
     * 根据电话查询用户
     * @param phone
     * @return
     */
    User getByPhone(String phone);

}
