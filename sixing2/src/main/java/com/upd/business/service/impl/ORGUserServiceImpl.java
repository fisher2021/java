package com.upd.business.service.impl;

import com.upd.business.dao.ORGUserDao;
import com.upd.business.entity.ORGUser;
import com.upd.business.service.*;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/23.
 */
@Service("orgUserService")
public class ORGUserServiceImpl extends BaseServiceImpl<ORGUser, Integer> implements ORGUserService {
    @Autowired
    public ORGUserDao orgUserDao;

    @Autowired
    public void setBaseDao(ORGUserDao dao) {
        this.baseDao = dao;
    }


    @Override
    public void deleteByUser(Integer id) {
        List<ORGUser> orgUsers = orgUserDao.find("from ORGUser where user.id = ?",id);
        for (ORGUser orgUser : orgUsers){
            orgUserDao.delete(orgUser);
        }
    }
}
