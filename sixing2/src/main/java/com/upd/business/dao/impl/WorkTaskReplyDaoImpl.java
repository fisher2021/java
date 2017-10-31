package com.upd.business.dao.impl;

import com.upd.business.dao.WorkTaskReplyDao;
import com.upd.business.entity.WorkTaskReply;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("workTaskReplyDao")
public class WorkTaskReplyDaoImpl extends BaseDaoImpl<WorkTaskReply,Integer> implements WorkTaskReplyDao {
}
