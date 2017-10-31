package com.upd.business.service.impl;

import com.upd.business.dao.ConferenceDao;
import com.upd.business.dao.WorkTaskDao;
import com.upd.business.entity.Conference;
import com.upd.business.entity.ConferenceUser;
import com.upd.business.entity.WorkTask;
import com.upd.business.entity.WorkTaskUser;
import com.upd.business.service.ConferenceService;
import com.upd.business.service.ConferenceUserService;
import com.upd.business.service.WorkTaskService;
import com.upd.business.service.WorkTaskUserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("conferenceService")
public class ConferenceServiceImpl extends BaseServiceImpl<Conference, Integer> implements ConferenceService {
    @Autowired
    public ConferenceDao conferenceDao;
    @Autowired
    public void setBaseDao(ConferenceDao dao) {
        this.baseDao = dao;
    }
    @Autowired
    ConferenceUserService conferenceUserService;
    @Override
    public Pagination getPage(Pagination page, Integer userId,Integer type) {
        if (type != null && type.equals(1)){//我发起的
            return conferenceDao.findbypage(page,"from Conference where user.id = ? order by time desc",userId);
        }else {//我收到的或默认的
            conferenceUserService.findByUser(page,userId);
            List<ConferenceUser> conferenceUsers = page.getList();
            if (conferenceUsers != null && conferenceUsers.size() != 0){
                List<Conference> conferences = new ArrayList<>();
                for (ConferenceUser conferenceUser:conferenceUsers){
                    Conference conference = conferenceDao.get(conferenceUser.getConference().getId());
                    conferences.add(conference);
                }
                page.setList(conferences);
            }
            return page;
        }
    }

    @Override
    public void add(Conference conference) {
        conference.initTime();
        conferenceDao.save(conference);
    }

    @Override
    public void edit(Conference conference) {
        conference.initTime();
        conferenceDao.update(conference);
    }

}
