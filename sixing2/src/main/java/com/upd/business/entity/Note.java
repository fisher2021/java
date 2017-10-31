package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 笔记
 * Created by ljw on 2017/8/29.
 */
@Entity
public class Note extends BaseEntity {
    @Column(length = 50)
    private String title;//标题
    @Column(length = 16777215)
    private String content;//内容
    @JSONField(serialize = false)
    @Column(name = "user_id")
    private Integer userId;//用户ID
    @Transient
    private String time;//创建时间

    public String getTime() {
        return this.getCreateTime();
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
