package com.upd.business.dao.impl;

import com.upd.business.dao.JobBackUserDao;
import com.upd.business.entity.JobBackUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("jobBackUserDao")
public class JobBackUserDaoImpl extends BaseDaoImpl<JobBackUser,Integer> implements JobBackUserDao {
}
