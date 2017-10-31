package com.upd.business.dao.impl;

import com.upd.business.dao.ExamDao;
import com.upd.business.entity.Exam;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/5/23.
 */
@Repository("examDao")
public class ExamDaoImpl extends BaseDaoImpl<Exam,Integer> implements ExamDao {
}
