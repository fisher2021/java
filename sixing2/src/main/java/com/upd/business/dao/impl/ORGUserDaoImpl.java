package com.upd.business.dao.impl;

import com.upd.business.dao.ORGUserDao;
import com.upd.business.entity.ORGUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/23.
 */
@Repository("orgUserDao")
public class ORGUserDaoImpl extends BaseDaoImpl<ORGUser,Integer> implements ORGUserDao {
}
