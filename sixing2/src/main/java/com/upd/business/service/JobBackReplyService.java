package com.upd.business.service;

import com.upd.business.entity.JobBackReply;
import com.upd.business.form.WorkTaskForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 工作展示回复类业务层
 * Created by ljw on 2017/5/4.
 */
public interface JobBackReplyService extends BaseService<JobBackReply,Integer> {
    /**
     * 按工作展示分页查询
     * @param page 分页数据
     * @return
     */
    Pagination pageByJobBackId(Pagination page, Integer jobBackId);

    /**
     * 新增
     * @param jobBackReply
     */
    void add(JobBackReply jobBackReply);

}
