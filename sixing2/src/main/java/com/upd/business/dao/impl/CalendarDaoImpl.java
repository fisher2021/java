package com.upd.business.dao.impl;

import com.upd.business.dao.CalendarDao;
import com.upd.business.entity.Calendar;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("calendarDao")
public class CalendarDaoImpl extends BaseDaoImpl<Calendar,Integer> implements CalendarDao {
}
