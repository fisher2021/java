package com.upd.business.service.impl;

import com.upd.business.dao.ExamDao;
import com.upd.business.entity.Exam;
import com.upd.business.form.ExamForm;
import com.upd.business.service.ExamService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/23.
 */
@Service("examService")
public class ExamServiceImpl extends BaseServiceImpl<Exam, Integer> implements ExamService{
    @Autowired
    private ExamDao dao;
    @Autowired
    public void setBaseDao(ExamDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination restPage(Pagination page, ExamForm form,String unitCode) {
        String sql = "SELECT e.id,e.createTime,e.updateTime,e.operator_id,title,content,(times-(SELECT count(*) FROM score WHERE exam_id=e.id and user_id=" + form.getUser() + "))as times," +
                "duration,pass,type,expire,total FROM exam e,operator o,org g WHERE 1=1 ";
        if(null!=form.type){
            sql+=" AND type="+form.type.ordinal();
        }
        if(null!=form.getId()){
            sql+=" AND id="+form.getId();
        }
        if (null!=unitCode){
            sql+=" and e.operator_id = o.id and o.org_id = g.id and g.code like '"+unitCode+"%'";
        }
        sql+=" ORDER BY expire DESC";
        return baseDao.findbyPageSQL(page,sql);
    }

    @Override
    public void insert(int examId,String ids){
        dao.excuteBySql("INSERT INTO exam_questions (exam_id,questions_id,createTime,updateTime)" +
                "SELECT "+examId+",q.id,NOW(),NOW() FROM questions q " +
                "WHERE FIND_IN_SET(q.id,'"+ids+"') and " +
                "id not in (select e.questions_id FROM exam_questions e WHERE FIND_IN_SET(e.questions_id,'"+ids+"') and e.exam_id="+examId+")");
        //计算总分
        Object total=baseDao.findObject("SELECT SUM(e.questions.score) as total FROM ExamQuestions e WHERE e.exam.id=?",examId);
        Exam exam=get(examId);
        exam.setTotal(Double.parseDouble(total.toString()));
        update(exam);
    }

    @Override
    public void deleteQuestions(Integer examId,String ids) {
        dao.excuteBySql("DELETE FROM exam_questions WHERE exam_id =? and FIND_IN_SET(questions_id,'"+ids+"')",examId);
        //计算总分
        Object total=baseDao.findObject("SELECT SUM(e.questions.score) as total FROM ExamQuestions e WHERE e.exam.id=?",examId);
        Exam exam=get(examId);
        exam.setTotal(Double.parseDouble(total.toString()));
        update(exam);
    }

    @Override
    public void edit(Exam exam) {
        Exam exam1 = baseDao.get(exam.getId());
        exam1.setTitle(exam.getTitle());
        exam1.setPass(exam.getPass());
        exam1.setTimes(exam.getTimes());
        exam1.setDuration(exam.getDuration());
        exam1.setExpire(exam.getExpire());
        exam1.setType(exam.getType());
        exam1.setContent(exam.getContent());
        exam1.initTime();
        baseDao.update(exam1);
    }

}
