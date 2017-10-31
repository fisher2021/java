package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import com.upd.common.util.DateUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * 成绩表
 * Created by Administrator on 2017/5/23.
 */
@Entity
@Table
public class Score extends BaseEntity{

    @Column(columnDefinition = "double(5,2)",nullable = false)
    private Double value;//分数

    @Column(columnDefinition="bit default 0 ",nullable=false)
    private boolean pass;//是否通过

    @JSONField(serialize=false)
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;//用户

    @JSONField(serialize=false)
    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;//试卷

    @Transient
    private String examName;
    @Transient
    @JSONField(format="yyyy/MM/dd")
    private Date examDate;

    public Date getExamDate() {
        return DateUtil.parseDate(getCreateTime());
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getExamName() {
        return exam.getTitle();
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
