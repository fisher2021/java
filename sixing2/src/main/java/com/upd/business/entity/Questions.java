package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.business.constant.ExamQuestionsType;
import com.upd.business.constant.ExamType;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 试题
 * Created by Administrator on 2017/5/23.
 */
@Entity
@Table(name="questions")
public class Questions extends BaseEntity {

    @Column(length = 200)
    private String title;//描述

    @Column(length = 1)
    @Enumerated(EnumType.ORDINAL)
    private ExamQuestionsType type;//题型

    @JSONField(serialize=false)
    @Column(name = "exam_type",length = 1)
    @Enumerated(EnumType.ORDINAL)
    private ExamType examType;//类型

    private String options;//选项 ‘||’ 分割

    @Column(columnDefinition = "double(4,2)",nullable = false)
    private Double score;//分值

    @Column(name = "options_result",length = 50)
    private String optionsResult;//选项结果

    @JSONField(serialize=false)
    @ManyToMany
    @JoinTable(name="exam_questions",joinColumns={@JoinColumn(name="questions_id")},
            inverseJoinColumns={@JoinColumn(name="exam_id")})
    private List<Exam> exams;

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExamQuestionsType getType() {
        return type;
    }

    public void setType(ExamQuestionsType type) {
        this.type = type;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptionsResult() {
        return optionsResult;
    }

    public void setOptionsResult(String optionsResult) {
        this.optionsResult = optionsResult;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
