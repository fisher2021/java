package com.upd.business.dao.impl;

import com.upd.business.dao.VoteDao;
import com.upd.business.entity.Vote;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("voteDao")
public class VoteDaoImpl extends BaseDaoImpl<Vote,Integer> implements VoteDao{
}
