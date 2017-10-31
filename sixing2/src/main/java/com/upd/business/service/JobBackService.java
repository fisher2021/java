package com.upd.business.service;

import com.upd.business.entity.JobBack;
import com.upd.business.form.JobForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 工作展示类业务层
 * Created by ljw on 2017/5/3.
 */
public interface JobBackService extends BaseService<JobBack,Integer>{

    /**
     * 分页查询工作展示
     * @param page 分页数据
     * @param form 用户ID
     * @return
     */
    public Pagination page(Pagination page, JobForm form);
    /**
     * 批量删除
     * @param ids
     */
    public void deleteBatch(String ids);
    /**
     * 新增工作展示
     * @param jobBack
     * @return
     */
    public void add(JobBack jobBack);
    /**
     * 修改工作展示
     * @param jobBack
     * @return
     */
    public JobBack edit(JobBack jobBack);


}
