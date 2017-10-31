package com.upd.business.service.impl;

import com.upd.business.dao.JobBackReplyDao;
import com.upd.business.entity.JobBackReply;
import com.upd.business.form.WorkTaskForm;
import com.upd.business.service.JobBackReplyService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("jobBackReplyService")
public class JobBackReplyServiceImpl extends BaseServiceImpl<JobBackReply, Integer> implements JobBackReplyService {
    @Autowired
    public JobBackReplyDao jobBackReplyDao;
    @Autowired
    public void setBaseDao(JobBackReplyDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByJobBackId(Pagination page, Integer jobBackId) {
        return jobBackReplyDao.findbypage(page,"from JobBackReply where jobBack.id = ? order by createTime desc",jobBackId);
    }

    @Override
    public void add(JobBackReply jobBackReply) {
        jobBackReply.initTime();
        jobBackReplyDao.save(jobBackReply);
    }

}
