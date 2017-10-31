package com.upd.business.service.impl;

import com.upd.business.dao.MailboxDao;
import com.upd.business.entity.Mailbox;
import com.upd.business.entity.MailboxUser;
import com.upd.business.service.MailboxService;
import com.upd.business.service.MailboxUserService;
import com.upd.business.service.WorkTaskUserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("mailboxService")
public class MailboxServiceImpl extends BaseServiceImpl<Mailbox, Integer> implements MailboxService {
    @Autowired
    public MailboxDao mailboxDao;
    @Autowired
    public void setBaseDao(MailboxDao dao) {
        this.baseDao = dao;
    }
    @Autowired
    MailboxUserService mailboxUserService;
    @Override
    public Pagination getPage(Pagination page, Integer userId,Integer type) {
        if (type.equals(0)){//收件箱
            mailboxUserService.findByUser(page,userId);
            List<MailboxUser> mailboxUsers = page.getList();
            List<Mailbox> mailboxes = new ArrayList<>();
            for (MailboxUser mailboxUser:mailboxUsers){
                Mailbox  mailbox = mailboxDao.get(mailboxUser.getMailbox().getId());
                mailboxes.add(mailbox);
            }
            page.setList(mailboxes);
            return page;
        }else if (type.equals(1)){//发件箱
            return mailboxDao.findbypage(page,"from Mailbox where user.id = ? and isDeleted = false order by createTime desc",userId);
        }
        return null;
    }

    @Override
    public void add(Mailbox mailbox) {
        mailbox.initTime();
        mailboxDao.save(mailbox);
    }

    @Override
    public void edit(Mailbox mailbox) {
        mailbox.initTime();
        mailboxDao.update(mailbox);
    }

}
