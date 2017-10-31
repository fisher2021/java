package com.upd.business.dao.impl;

import com.upd.business.dao.MessageDao;
import com.upd.business.entity.Message;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message,Integer> implements MessageDao {
}
