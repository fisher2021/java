package com.upd.business.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.constant.ExamType;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */
public class ExamVo {

    private String title;//标题

    private String content;//描述

    private Integer times;//可考次数

    private Integer duration;//时长（分钟）

    private Double pass;//及格分数

    private ExamType type;//类型

    @JSONField(format="yyyy/MM/dd")
    private Date expire;//有效期

    private boolean isexpire;//是否在有效期


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPass() {
        return pass;
    }

    public void setPass(Double pass) {
        this.pass = pass;
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

    public boolean isIsexpire() {
        return isexpire;
    }

    public void setIsexpire(boolean isexpire) {
        this.isexpire = isexpire;
    }
}
