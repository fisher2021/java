package com.upd.business.dao.impl;

import com.upd.business.dao.RoomDao;
import com.upd.business.entity.Room;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/5/19.
 */
@Repository("roomDao")
public class RoomDaoImpl extends BaseDaoImpl<Room,Integer> implements RoomDao{
}
