package com.upd.business.dao.impl;

import com.upd.business.dao.QuestionsDao;
import com.upd.business.entity.Questions;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/5/25.
 */
@Repository("questionsDao")
public class QuestionsDaoImpl extends BaseDaoImpl<Questions,Integer> implements QuestionsDao {
}
