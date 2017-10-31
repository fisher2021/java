package com.upd.business.dao.impl;
import com.upd.business.dao.DictDao;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import com.upd.common.basis.entity.Dict;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/3.
 */
@Repository("dictDao")
public class DictDaoImpl extends BaseDaoImpl<Dict,Integer> implements DictDao {
}
