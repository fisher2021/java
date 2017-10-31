package com.upd.business.dao.impl;

import com.upd.business.dao.ChoiceDao;
import com.upd.business.entity.Choice;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("choiceDao")
public class ChoiceDaoImpl extends BaseDaoImpl<Choice,Integer> implements ChoiceDao {
}
