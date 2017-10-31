package com.upd.business.dao.impl;

import com.upd.business.dao.MailboxReplyDao;
import com.upd.business.entity.MailboxReply;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("mailboxReplyDao")
public class MailboxReplyDaoImpl extends BaseDaoImpl<MailboxReply,Integer> implements MailboxReplyDao {
}
