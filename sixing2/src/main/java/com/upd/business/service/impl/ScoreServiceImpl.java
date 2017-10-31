package com.upd.business.service.impl;

import com.upd.business.dao.ScoreDao;
import com.upd.business.entity.Exam;
import com.upd.business.entity.Score;
import com.upd.business.form.ScoreForm;
import com.upd.business.service.ExamService;
import com.upd.business.service.ScoreService;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/24.
 */
@Service("scoreService")
public class ScoreServiceImpl extends BaseServiceImpl<Score, Integer> implements ScoreService {
    @Autowired
    public ScoreDao dao;
    @Autowired
    ExamService examService;

    @Autowired
    public void setBaseDao(ScoreDao dao) {
        this.baseDao = dao;
    }

    @Override
    public void save(Score score) {
        Exam exam= examService.get(score.getExam().getId());
        //判断是否过有效期
        if(!exam.getIsexpire()){
            throw new BusinessException(RestErrorCode.ERROR.getCode(),"考试时间已过！");
        }
        //判断可考次数,查询已考次数
        int total=findTotalCount("SELECT count(*) FROM Score WHERE exam.id=? and user.id=?",exam.getId(),score.getUser().getId());
        if(total>=exam.getTimes()){
            throw new BusinessException(RestErrorCode.ERROR.getCode(),"重考次数已达上限！");
        }
        if(exam.getPass()<=score.getValue()){
            score.setPass(true);
        }
        score.initTime();
        super.save(score);

    }

    public Map passTotal(ScoreForm form) {
        String hql="select id FROM Score WHERE pass=1 and"+form.toQueryDescription();
        String sql= baseDao.transHqlToSql(hql,form.values());
        sql=sql.substring(0,sql.indexOf("order"));
        sql="select count(0) from ("+sql+" group by user_id,exam_id) as t ";
        Map map=new HashMap();
        map.put("pass",baseDao.findIntBySQL(sql));

        hql="select id FROM Score WHERE"+form.toQueryDescription();
        sql=baseDao.transHqlToSql(hql,form.values());
        sql=sql.substring(0,sql.indexOf("order"));
        sql="select count(0) from ("+sql+" group by user_id,exam_id) as t ";
        map.put("total",baseDao.findIntBySQL(sql));
        return  map;
    }

}
