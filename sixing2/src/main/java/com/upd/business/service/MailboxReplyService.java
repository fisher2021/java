package com.upd.business.service;

import com.upd.business.entity.MailboxReply;
import com.upd.business.entity.WorkTaskReply;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 工作任务回复类业务层
 * Created by ljw on 2017/5/4.
 */
public interface MailboxReplyService extends BaseService<MailboxReply,Integer> {
    /**
     * 按工作任务分页查询
     * @param page 分页数据
     * @return
     */
    Pagination pageByMailboxId(Pagination page, Integer mailboxId);

    /**
     * 新增
     * @param mailboxReply
     */
    void add(MailboxReply mailboxReply);

}
