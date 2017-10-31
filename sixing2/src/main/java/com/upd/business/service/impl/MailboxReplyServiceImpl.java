package com.upd.business.service.impl;

import com.upd.business.dao.MailboxReplyDao;
import com.upd.business.dao.WorkTaskReplyDao;
import com.upd.business.entity.MailboxReply;
import com.upd.business.entity.WorkTaskReply;
import com.upd.business.service.MailboxReplyService;
import com.upd.business.service.WorkTaskReplyService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("mailboxReplyService")
public class MailboxReplyServiceImpl extends BaseServiceImpl<MailboxReply, Integer> implements MailboxReplyService {
    @Autowired
    public MailboxReplyDao mailboxReplyDao;
    @Autowired
    public void setBaseDao(MailboxReplyDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByMailboxId(Pagination page, Integer mailboxId) {
        return mailboxReplyDao.findbypage(page,"from MailboxReply where mailbox.id = ? order by createTime desc",mailboxId);
    }

    @Override
    public void add(MailboxReply mailboxReply) {
        mailboxReply.initTime();
        mailboxReplyDao.save(mailboxReply);
    }

}
