package com.upd.business.dao.impl;

import com.upd.business.dao.AppraisementDao;
import com.upd.business.entity.Appraisement;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("appraisementDao")
public class AppraisementDaoImpl extends BaseDaoImpl<Appraisement,Integer> implements AppraisementDao{
}
