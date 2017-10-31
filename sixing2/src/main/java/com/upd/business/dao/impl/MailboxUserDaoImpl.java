package com.upd.business.dao.impl;

import com.upd.business.dao.MailboxUserDao;
import com.upd.business.entity.MailboxUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("mailboxUserDao")
public class MailboxUserDaoImpl extends BaseDaoImpl<MailboxUser,Integer> implements MailboxUserDao {
}
