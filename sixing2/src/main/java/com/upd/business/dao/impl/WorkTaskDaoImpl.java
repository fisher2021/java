package com.upd.business.dao.impl;

import com.upd.business.dao.WorkTaskDao;
import com.upd.business.entity.WorkTask;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("workTaskDao")
public class WorkTaskDaoImpl extends BaseDaoImpl<WorkTask,Integer> implements WorkTaskDao {
}
