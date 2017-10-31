package com.upd.business.dao.impl;

import com.upd.business.dao.ScoreDao;
import com.upd.business.entity.Score;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/5/24.
 */
@Repository("scoreDao")
public class ScoreDaoImpl extends BaseDaoImpl<Score,Integer> implements ScoreDao {
}
