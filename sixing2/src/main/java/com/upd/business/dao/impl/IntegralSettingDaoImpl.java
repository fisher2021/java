package com.upd.business.dao.impl;

import com.upd.business.dao.IntegralSettingDao;
import com.upd.business.entity.IntegralSetting;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by xiao.tao on 2017/10/26.
 */
@Repository("integralSettingDao")
public class IntegralSettingDaoImpl extends BaseDaoImpl<IntegralSetting,Integer> implements IntegralSettingDao{
}
