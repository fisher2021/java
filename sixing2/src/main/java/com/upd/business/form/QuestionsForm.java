package com.upd.business.form;

import com.upd.business.constant.ExamQuestionsType;
import com.upd.business.constant.ExamType;
import com.upd.common.basis.base.QueryForm;

import java.io.Console;

/**
 * Created by Administrator on 2017/5/25.
 */
public class QuestionsForm extends QueryForm{

    public Integer exam;//试卷id
    public Boolean examHave;//是否
    public String title;//标题
    public Integer user;//用户id
    public ExamType type;//类型
    public ExamQuestionsType questionsType;//题型

    @Override
    protected void doParseInternal() {
        System.out.println("examHave:"+examHave);
        if(null!=examHave) {
            if (examHave) {
                in("id", "SELECT questions.id FROM ExamQuestions WHERE exam.id=" + exam);
            } else {
                notIn("id", "SELECT questions.id FROM ExamQuestions WHERE exam.id=" + exam);
            }
        }
        eq("examType",type);
        eq("type",questionsType);
        like("title",title);
        orderBy("createTime DESC");
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }

    public boolean isExamHave() {
        return examHave;
    }

    public void setExamHave(boolean examHave) {
        this.examHave = examHave;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public ExamQuestionsType getQuestionsType() {
        return questionsType;
    }

    public void setQuestionsType(ExamQuestionsType questionsType) {
        this.questionsType = questionsType;
    }
}
