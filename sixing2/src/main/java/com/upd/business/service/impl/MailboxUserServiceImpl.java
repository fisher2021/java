package com.upd.business.service.impl;

import com.upd.business.dao.MailboxUserDao;
import com.upd.business.dao.WorkTaskUserDao;
import com.upd.business.entity.MailboxUser;
import com.upd.business.entity.WorkTaskUser;
import com.upd.business.service.MailboxUserService;
import com.upd.business.service.WorkTaskUserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("mailboxUserService")
public class MailboxUserServiceImpl extends BaseServiceImpl<MailboxUser, Integer> implements MailboxUserService {
    @Autowired
    public MailboxUserDao mailboxUserDao;
    @Autowired
    public void setBaseDao(MailboxUserDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByMailboxId(Pagination page, Integer mailbioxId) {
        return mailboxUserDao.findbypage(page,"from MailboxUser where mailbox.id = ? and isDeleted = false",mailbioxId);
    }

    @Override
    public void add(MailboxUser mailboxUser) {
        mailboxUser.initTime();
        mailboxUserDao.save(mailboxUser);
    }

    @Override
    public void edit(MailboxUser mailboxUser) {
        mailboxUser.initTime();
        mailboxUserDao.update(mailboxUser);
    }

    @Override
    public Pagination findByUser(Pagination page,Integer userId) {
        return mailboxUserDao.findbypage(page,"from MailboxUser where mailbox.user.isDeleted = false and isDeleted = false and user.id = ? order by mailbox.createTime desc",userId);
    }
    @Override
    public MailboxUser findByMailboxAndUser(Integer mailboxId, Integer userId) {
        return mailboxUserDao.findOne("from MailboxUser where mailbox.id = ?and user.id = ?",mailboxId,userId);
    }
}
