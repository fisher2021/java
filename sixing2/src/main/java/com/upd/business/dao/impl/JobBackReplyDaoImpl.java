package com.upd.business.dao.impl;

import com.upd.business.dao.JobBackReplyDao;
import com.upd.business.entity.JobBackReply;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("jobBackReplyDao")
public class JobBackReplyDaoImpl extends BaseDaoImpl<JobBackReply,Integer> implements JobBackReplyDao {
}
