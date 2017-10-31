package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.business.constant.ExamType;
import com.upd.common.basis.entity.BaseEntity;
import com.upd.common.util.DateUtil;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 试卷
 * Created by Administrator on 2017/5/23.
 */
@Entity
@Table
public class Exam extends BaseEntity{

    @Column(length = 20)
    private String title;//标题

    @Column(length = 100)
    private String content;//描述

    @Column(length = 1)
    private Integer times;//可考次数

    @Column(length = 100)
    private Integer duration;//时长（分钟）

    @Column(columnDefinition = "double(5,2)",nullable=false)
    private Double pass;//及格分数

    @Enumerated(EnumType.ORDINAL)
    private ExamType type;//类型

    @JSONField(format="yyyy/MM/dd")
    private Date expire;//有效期

    @Column(columnDefinition = "double(5,2) default 0",nullable=false)
    @JSONField(serialize = false)
    private Double total;//总分数

    @Transient
    private boolean isexpire;//是否在有效期

    @JSONField(serialize=false)
    @ManyToMany
    @JoinTable(name="exam_questions",joinColumns={@JoinColumn(name="exam_id")},
            inverseJoinColumns={@JoinColumn(name="questions_id")})
    private List<Questions> questions;

    @JSONField(serialize=false)
    @OneToMany(mappedBy = "exam",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Score> score;

    public Exam() {
    }

    public Exam(Integer id,String title, String content, Integer times, Integer duration, Double pass, ExamType type, Date expire) {
        this.id=id;
        this.title = title;
        this.content = content;
        this.times = times;
        this.duration = duration;
        this.pass = pass;
        this.type = type;
        this.expire = expire;
    }

    public List<Score> getScore() {
        return score;
    }

    public void setScore(List<Score> score) {
        this.score = score;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPass() {
        return pass;
    }

    public void setPass(Double pass) {
        this.pass = pass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getIsexpire() {
        //判断是否在有效期内
        if(DateUtil.daysBetween(DateUtil.parseDateTimeToSec(new Date()),DateUtil.parseDateTimeToSec(getExpire()))>=0){
            this.isexpire= true;
        }else{
            this.isexpire= false;
        }
        return isexpire;
    }

    public void setIsexpire(Boolean isexpire) {
        this.isexpire = isexpire;
    }
}
