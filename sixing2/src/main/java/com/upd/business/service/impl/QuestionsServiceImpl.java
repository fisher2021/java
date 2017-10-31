package com.upd.business.service.impl;

import com.upd.business.dao.QuestionsDao;
import com.upd.business.entity.Questions;
import com.upd.business.service.QuestionsService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/25.
 */
@Service("questionsService")
public class QuestionsServiceImpl extends BaseServiceImpl<Questions,Integer> implements QuestionsService{
    @Autowired
    public QuestionsDao reportDao;
    @Autowired
    public void setBaseDao(QuestionsDao dao) {
        this.baseDao = dao;
    }
}
