package com.upd.auth.dao.impl;

import com.upd.auth.dao.LogDao;
import com.upd.auth.entity.Log;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/2.
 */
@Repository
public class LogDaoImpl extends BaseDaoImpl<Log,Integer> implements LogDao {
}
