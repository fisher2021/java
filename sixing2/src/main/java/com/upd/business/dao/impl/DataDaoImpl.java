package com.upd.business.dao.impl;

import com.upd.business.dao.DataDao;
import com.upd.business.entity.Data;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/8.
 */
@Repository("dataDao")
public class DataDaoImpl extends BaseDaoImpl<Data,Integer> implements DataDao {
}
