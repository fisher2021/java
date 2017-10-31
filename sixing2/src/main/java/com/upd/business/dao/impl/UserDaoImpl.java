package com.upd.business.dao.impl;

import com.upd.business.dao.UserDao;
import com.upd.business.entity.User;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User,Integer> implements UserDao {
    @Override
    public User getByPhone(String phone) {
        String hql = "FROM User WHERE account = :account";
        @SuppressWarnings("unchecked")
        List<User> userInfoList = (List<User>) this.getHibernateTemplate().findByNamedParam(hql, "account", phone);
        if(CollectionUtils.isNotEmpty(userInfoList)){
            return userInfoList.get(0);
        }
        return null;
    }
}
