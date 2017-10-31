package com.upd.business.service.impl;

import com.upd.business.dao.WorkTaskReplyDao;
import com.upd.business.entity.WorkTaskReply;
import com.upd.business.form.WorkTaskForm;
import com.upd.business.service.WorkTaskReplyService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("workTaskReplyService")
public class WorkTaskReplyServiceImpl extends BaseServiceImpl<WorkTaskReply, Integer> implements WorkTaskReplyService {
    @Autowired
    public WorkTaskReplyDao workTaskReplyDao;
    @Autowired
    public void setBaseDao(WorkTaskReplyDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByWorkTaskId(Pagination page, Integer workTaskId) {
        return workTaskReplyDao.findbypage(page,"from WorkTaskReply where workTask.id = ? order by createTime desc",workTaskId);
    }

    @Override
    public void add(WorkTaskReply workTaskReply) {
        workTaskReply.initTime();
        workTaskReplyDao.save(workTaskReply);
    }

}
