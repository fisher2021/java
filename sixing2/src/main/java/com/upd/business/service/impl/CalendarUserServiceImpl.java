package com.upd.business.service.impl;

import com.upd.business.dao.ArticleDao;
import com.upd.business.dao.BacklogUserDao;
import com.upd.business.dao.CalendarUserDao;
import com.upd.business.entity.BacklogUser;
import com.upd.business.entity.Calendar;
import com.upd.business.entity.CalendarUser;
import com.upd.business.service.BacklogUserService;
import com.upd.business.service.CalendarUserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/23.
 */
@Service("calendarUserService")
public class CalendarUserServiceImpl extends BaseServiceImpl<CalendarUser, Integer> implements CalendarUserService {

    @Autowired
    public CalendarUserDao calendarUserDao;
    @Autowired
    public ArticleDao articleDao;

    @Autowired
    public void setBaseDao(CalendarUserDao dao) {
        this.baseDao = dao;
    }


    @Override
    public List<CalendarUser> findByUser(Integer userId) {
        List<CalendarUser> backlogUsers = calendarUserDao.find("from CalendarUser where user_id = ?",userId);
        return backlogUsers;
    }

    @Override
    public List<CalendarUser> findByCalendar(Integer calendarId) {
        List<CalendarUser> backlogUsers = calendarUserDao.find("from CalendarUser where calendar_id = ?",calendarId);
        return backlogUsers;
    }

    @Override
    public void deleteUser(Integer calendarId, Integer id) {
        CalendarUser user = calendarUserDao.findOne("from CalendarUser where calendar.id = ? and user.id  = ?",calendarId,id);
        if (user != null){
            calendarUserDao.delete(user);
        }
    }

    @Override
    public CalendarUser findByUserAndCalendar(Integer calendarId, Integer userId) {
        return calendarUserDao.findOne("from CalendarUser where calendar.id = ? and user.id  = ?",calendarId,userId);
    }

}
