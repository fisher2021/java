package com.upd.business.dao.impl;
import com.upd.business.dao.DictTypeDao;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import com.upd.common.basis.entity.DictType;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/3.
 */
@Repository("dictTypeDao")
public class DictTypeDaoImpl extends BaseDaoImpl<DictType,Integer> implements DictTypeDao {
}
