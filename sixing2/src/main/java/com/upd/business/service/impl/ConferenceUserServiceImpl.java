package com.upd.business.service.impl;

import com.upd.business.dao.ConferenceUserDao;
import com.upd.business.dao.WorkTaskUserDao;
import com.upd.business.entity.ConferenceUser;
import com.upd.business.entity.WorkTaskUser;
import com.upd.business.service.ConferenceUserService;
import com.upd.business.service.WorkTaskUserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("conferenceUserService")
public class ConferenceUserServiceImpl extends BaseServiceImpl<ConferenceUser, Integer> implements ConferenceUserService {
    @Autowired
    public ConferenceUserDao conferenceUserDao;
    @Autowired
    public void setBaseDao(ConferenceUserDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByConferenceId(Pagination page, Integer conferenceId) {
        return conferenceUserDao.findbypage(page,"from ConferenceUser where conference.id = ?",conferenceId);
    }

    @Override
    public void add(ConferenceUser conferenceUser) {
        conferenceUser.initTime();
        conferenceUserDao.save(conferenceUser);
    }

    @Override
    public void edit(ConferenceUser conferenceUser) {
        conferenceUser.initTime();
        conferenceUserDao.update(conferenceUser);
    }

    @Override
    public Pagination findByUser(Pagination page,Integer userId) {
        return conferenceUserDao.findbypage(page,"from ConferenceUser where conference.user.isDeleted = false and user.id = ? order by conference.time desc",userId);
    }

}
