package com.upd.business.dao.impl;

import com.upd.business.dao.PartyMembershipDuesDao;
import com.upd.business.entity.PartyMembershipDues;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/5/31.
 */
@Repository("partyMembershipDuesDao")
public class PartyMembershipDuesDaoImpl extends BaseDaoImpl<PartyMembershipDues,Integer> implements PartyMembershipDuesDao {
}
