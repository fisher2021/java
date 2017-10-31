package com.upd.business.dao.impl;

import com.upd.business.dao.ReportDao;
import com.upd.business.entity.Report;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("reportDao")
public class ReportDaoImpl extends BaseDaoImpl<Report,Integer> implements ReportDao{
}
