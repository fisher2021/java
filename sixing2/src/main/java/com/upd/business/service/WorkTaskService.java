package com.upd.business.service;

import com.upd.business.entity.WorkTask;
import com.upd.business.form.AdviceForm;
import com.upd.business.form.WorkTaskForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 工作任务类业务层
 * Created by ljw on 2017/5/4.
 */
public interface WorkTaskService extends BaseService<WorkTask,Integer> {
    /**
     * 分页查询
     * @param page 分页数据
     * @return
     */
    Pagination getPage(Pagination page, Integer userId,Integer type);

    /**
     * 新增
     * @param workTask
     */
    void add(WorkTask workTask);

    /**
     * 修改
     * @param workTask
     */
    void edit(WorkTask workTask);

}
