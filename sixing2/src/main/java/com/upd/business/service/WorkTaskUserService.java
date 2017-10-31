package com.upd.business.service;

import com.upd.business.entity.WorkTaskUser;
import com.upd.business.form.AdviceForm;
import com.upd.business.form.WorkTaskForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
public interface WorkTaskUserService extends BaseService<WorkTaskUser,Integer> {
    /**
     * 按工作任务分页查询
     * @param page 分页数据
     * @return
     */
    Pagination pageByWorkTaskId(Pagination page, Integer workTaskId);

    /**
     * 新增
     * @param workTaskUser
     */
    void add(WorkTaskUser workTaskUser);

    /**
     * 修改
     * @param workTaskUser
     */
    void edit(WorkTaskUser workTaskUser);

    /**
     * 通过用户查询
     * @param userId
     * @return
     */
    Pagination findByUser(Pagination page,Integer userId);


}
