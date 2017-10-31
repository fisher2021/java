package com.upd.business.dao.impl;

import com.upd.business.dao.RoomUserDao;
import com.upd.business.entity.RoomUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/5/23.
 */
@Repository("roomUserDao")
public class RoomUserDaoImpl extends BaseDaoImpl<RoomUser,Integer> implements RoomUserDao {
}
