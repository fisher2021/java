package com.upd.business.dao.impl;

import com.upd.business.dao.JobBackDao;
import com.upd.business.entity.JobBack;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by LJW on 2017/5/3.
 */
@Repository("jobBackDao")
public class JobBackDaoImpl extends BaseDaoImpl<JobBack,Integer> implements JobBackDao {
}
