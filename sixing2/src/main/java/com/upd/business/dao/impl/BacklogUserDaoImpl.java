package com.upd.business.dao.impl;

import com.upd.business.dao.BacklogUserDao;
import com.upd.business.entity.BacklogUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/23.
 */
@Repository("backlogUserDao")
public class BacklogUserDaoImpl extends BaseDaoImpl<BacklogUser,Integer> implements BacklogUserDao {
}
