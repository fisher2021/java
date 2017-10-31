package com.upd.business.dao.impl;

import com.upd.business.dao.DataUserDao;
import com.upd.business.entity.DataUser;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/23.
 */
@Repository("dataUserDao")
public class DataUserDaoImpl extends BaseDaoImpl<DataUser,Integer> implements DataUserDao {
}
