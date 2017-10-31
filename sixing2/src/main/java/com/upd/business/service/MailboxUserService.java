package com.upd.business.service;

import com.upd.business.entity.MailboxUser;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * Created by ljw on 2017/5/4.
 */
public interface MailboxUserService extends BaseService<MailboxUser,Integer> {
    /**
     * 按信箱分页查询
     * @param page 分页数据
     * @return
     */
    Pagination pageByMailboxId(Pagination page, Integer mailboxId);

    /**
     * 新增
     * @param mailboxUser
     */
    void add(MailboxUser mailboxUser);

    /**
     * 修改
     * @param mailboxUser
     */
    void edit(MailboxUser mailboxUser);

    /**
     * 通过用户查询
     * @param userId
     * @return
     */
    Pagination findByUser(Pagination page, Integer userId);

    /**
     * 通过信和用户查询
     * @param userId
     * @return
     */
    MailboxUser findByMailboxAndUser(Integer mailboxId, Integer userId);
}
