package com.upd.business.dao.impl;

import com.upd.business.dao.ConferenceDao;
import com.upd.business.entity.Conference;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("conferenceDao")
public class ConferenceDaoImpl extends BaseDaoImpl<Conference,Integer> implements ConferenceDao {
}
