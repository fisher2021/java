package com.upd.business.dao.impl;

import com.upd.business.dao.WorkTaskUserDao;
import com.upd.business.entity.WorkTaskUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("workTaskUserDao")
public class WorkTaskUserDaoImpl extends BaseDaoImpl<WorkTaskUser,Integer> implements WorkTaskUserDao {
}
