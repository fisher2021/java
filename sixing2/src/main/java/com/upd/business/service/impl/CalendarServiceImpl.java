package com.upd.business.service.impl;

import com.upd.business.dao.CalendarDao;
import com.upd.business.dao.UserDao;
import com.upd.business.dao.VoteDao;
import com.upd.business.entity.Article;
import com.upd.business.entity.Calendar;
import com.upd.business.entity.User;
import com.upd.business.entity.Vote;
import com.upd.business.form.AdviceForm;
import com.upd.business.form.CalendarForm;
import com.upd.business.service.CalendarService;
import com.upd.business.service.UserService;
import com.upd.business.service.VoteService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("calendarService")
public class CalendarServiceImpl extends BaseServiceImpl<Calendar, Integer> implements CalendarService {
    @Autowired
    public CalendarDao calendarDao;
    @Autowired
    private UserService userService;
    @Autowired
    public void setBaseDao(CalendarDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Calendar searchByMonth(String month,Integer id) {
        Calendar calendar = calendarDao.findOne("from Calendar where date_format(doDate,'%Y-%m') = ? and id = ?",month,id);
        return calendar;
    }

    @Override
    public Pagination page(Pagination page, CalendarForm form) {
        Pagination pageRsult = calendarDao.findbypage(page,"from Calendar where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public void add(Calendar calendar) {
        calendar.initTime();
        calendarDao.save(calendar);
    }

    @Override
    public void edit(Calendar calendar) {
        calendar.initTime();
        calendarDao.update(calendar);
    }


    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            calendarDao.delete(calendarDao.get(Integer.parseInt(id)));
        }
    }

    @Override
    public void addBatch(Integer calendarId, Integer[] ids) {
        List<User> users = new ArrayList<User>();
        for (int i=0;i<ids.length;i++){
            users.add(userService.get(ids[i]));
        }
        Calendar calendar = calendarDao.get(calendarId);
        List<User> old = calendar.getUser();
        for (User u: old){
            users.add(u);
        }
        calendar.setUser(users);
        calendarDao.update(calendar);
    }
}
