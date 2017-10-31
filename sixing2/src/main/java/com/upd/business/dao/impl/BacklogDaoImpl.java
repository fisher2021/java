package com.upd.business.dao.impl;

import com.upd.business.dao.BacklogDao;
import com.upd.business.entity.Backlog;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("backlogDao")
public class BacklogDaoImpl extends BaseDaoImpl<Backlog,Integer> implements BacklogDao {
}
