package com.upd.business.service.impl;

import com.upd.business.dao.WorkTaskDao;
import com.upd.business.entity.WorkTask;
import com.upd.business.entity.WorkTaskUser;
import com.upd.business.form.AdviceForm;
import com.upd.business.form.WorkTaskForm;
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
@Service("workTaskService")
public class WorkTaskServiceImpl extends BaseServiceImpl<WorkTask, Integer> implements WorkTaskService {
    @Autowired
    public WorkTaskDao workTaskDao;
    @Autowired
    public void setBaseDao(WorkTaskDao dao) {
        this.baseDao = dao;
    }
    @Autowired
    WorkTaskUserService workTaskUserService;
    @Override
    public Pagination getPage(Pagination page, Integer userId,Integer type) {
        if (type != null && type.equals(1)){//我发起的
            return workTaskDao.findbypage(page,"from WorkTask where user.id = ? order by createTime desc",userId);
        }else {//我收到的或默认的
            workTaskUserService.findByUser(page,userId);
            List<WorkTaskUser> workTaskUsers = page.getList();
            if (workTaskUsers != null && workTaskUsers.size() != 0){
                List<WorkTask> workTasks = new ArrayList<>();
                for (WorkTaskUser workTaskUser:workTaskUsers){
                    WorkTask  workTask = workTaskDao.get(workTaskUser.getWorkTask().getId());
                    workTasks.add(workTask);
                }
                page.setList(workTasks);
            }
            return page;
        }
    }

    @Override
    public void add(WorkTask workTask) {
        workTask.initTime();
        workTaskDao.save(workTask);
    }

    @Override
    public void edit(WorkTask workTask) {
        workTask.initTime();
        workTaskDao.update(workTask);
    }

}
