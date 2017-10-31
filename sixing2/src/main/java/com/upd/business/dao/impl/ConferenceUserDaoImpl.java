package com.upd.business.dao.impl;

import com.upd.business.dao.ConferenceUserDao;
import com.upd.business.entity.ConferenceUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("conferenceUserDao")
public class ConferenceUserDaoImpl extends BaseDaoImpl<ConferenceUser,Integer> implements ConferenceUserDao {
}
