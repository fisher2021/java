package com.upd.business.dao.impl;

import com.upd.business.dao.CalendarUserDao;
import com.upd.business.entity.CalendarUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/23.
 */
@Repository("calendarUserDao")
public class CalendarUserDaoImpl extends BaseDaoImpl<CalendarUser,Integer> implements CalendarUserDao {
}
