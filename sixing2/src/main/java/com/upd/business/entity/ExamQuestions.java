package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 试卷试题中间表
 * Created by Administrator on 2017/5/27.
 */
@Entity
@Table(name="exam_questions")
public class ExamQuestions extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;
    @ManyToOne
    @JoinColumn(name="questions_id")
    private Questions questions;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }
}
