package com.upd.business.service;

import com.upd.business.entity.Mailbox;
import com.upd.business.entity.WorkTask;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 工作任务类业务层
 * Created by ljw on 2017/5/4.
 */
public interface MailboxService extends BaseService<Mailbox,Integer> {
    /**
     * 分页查询
     * @param page 分页数据
     * @param type 0:收件箱 1：发件箱
     * @return
     */
    Pagination getPage(Pagination page, Integer userId, Integer type);

    /**
     * 新增
     * @param mailbox
     */
    void add(Mailbox mailbox);

    /**
     * 修改
     * @param mailbox
     */
    void edit(Mailbox mailbox);

}
