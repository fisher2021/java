package com.upd.business.dao.impl;

import com.upd.business.dao.MailboxDao;
import com.upd.business.entity.Mailbox;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("mailboxDao")
public class MailboxDaoImpl extends BaseDaoImpl<Mailbox,Integer> implements MailboxDao {
}
