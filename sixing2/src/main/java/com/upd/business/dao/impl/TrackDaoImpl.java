package com.upd.business.dao.impl;

import com.upd.business.dao.TrackDao;
import com.upd.business.entity.Track;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("trackDao")
public class TrackDaoImpl extends BaseDaoImpl<Track,Integer> implements TrackDao {
}
