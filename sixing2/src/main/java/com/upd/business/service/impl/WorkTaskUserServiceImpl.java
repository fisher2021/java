package com.upd.business.service.impl;

import com.upd.business.dao.WorkTaskUserDao;
import com.upd.business.entity.WorkTaskUser;
import com.upd.business.form.AdviceForm;
import com.upd.business.form.WorkTaskForm;
import com.upd.business.service.WorkTaskUserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("workTaskUserService")
public class WorkTaskUserServiceImpl extends BaseServiceImpl<WorkTaskUser, Integer> implements WorkTaskUserService {
    @Autowired
    public WorkTaskUserDao workTaskUserDao;
    @Autowired
    public void setBaseDao(WorkTaskUserDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByWorkTaskId(Pagination page, Integer workTaskId) {
        return workTaskUserDao.findbypage(page,"from WorkTaskUser where workTask.id = ?",workTaskId);
    }

    @Override
    public void add(WorkTaskUser workTaskUser) {
        workTaskUser.initTime();
        workTaskUserDao.save(workTaskUser);
    }

    @Override
    public void edit(WorkTaskUser workTaskUser) {
        workTaskUser.initTime();
        workTaskUserDao.update(workTaskUser);
    }

    @Override
    public Pagination findByUser(Pagination page,Integer userId) {
        return workTaskUserDao.findbypage(page,"from WorkTaskUser where workTask.user.isDeleted = false and user.id = ? order by workTask.createTime desc",userId);
    }

}
