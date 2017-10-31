package com.upd.business.service.impl;

import com.upd.business.dao.JobBackUserDao;
import com.upd.business.dao.WorkTaskUserDao;
import com.upd.business.entity.JobBackUser;
import com.upd.business.entity.WorkTaskUser;
import com.upd.business.service.JobBackUserService;
import com.upd.business.service.WorkTaskUserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("jobBackUserService")
public class JobBackUserServiceImpl extends BaseServiceImpl<JobBackUser, Integer> implements JobBackUserService {
    @Autowired
    public JobBackUserDao jobBackUserDao;
    @Autowired
    public void setBaseDao(JobBackUserDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByWorkTaskId(Pagination page, Integer workTaskId) {
        return jobBackUserDao.findbypage(page,"from JobBackUser where workTask.id = ?",workTaskId);
    }

    @Override
    public void add(JobBackUser jobBackUser) {
        jobBackUser.initTime();
        jobBackUserDao.save(jobBackUser);
    }

    @Override
    public void edit(JobBackUser jobBackUser) {
        jobBackUser.initTime();
        jobBackUserDao.update(jobBackUser);
    }

    @Override
    public Pagination findByUser(Pagination page,Integer userId) {
        return jobBackUserDao.findbypage(page,"from JobBackUser where user.id = ? order by jobBack.createTime desc",userId);
    }

    @Override
    public JobBackUser findByUserAndJob(Integer userId, Integer jobBackId) {
        return jobBackUserDao.findOne("from JobBackUser where user.id = ? and jobBack.id = ?",userId,jobBackId);
    }

}
