package com.upd.business.dao.impl;

import com.upd.business.dao.AdviceDao;
import com.upd.business.entity.Advice;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("adviceDao")
public class AdviceDaoImpl extends BaseDaoImpl<Advice,Integer> implements AdviceDao{
}
