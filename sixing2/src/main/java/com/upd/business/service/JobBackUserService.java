package com.upd.business.service;

import com.upd.business.entity.JobBackUser;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * Created by ljw on 2017/5/4.
 */
public interface JobBackUserService extends BaseService<JobBackUser,Integer> {
    /**
     * 按工作任务分页查询
     * @param page 分页数据
     * @return
     */
    Pagination pageByWorkTaskId(Pagination page, Integer workTaskId);

    /**
     * 新增
     * @param jobBackUser
     */
    void add(JobBackUser jobBackUser);

    /**
     * 修改
     * @param jobBackUser
     */
    void edit(JobBackUser jobBackUser);

    /**
     * 通过用户查询
     * @param userId
     * @return
     */
    Pagination findByUser(Pagination page, Integer userId);
    /**
     * 通过用户和工作展示查询
     * @param userId
     * @return
     */
    JobBackUser findByUserAndJob(Integer userId,Integer jobBackId);

}
