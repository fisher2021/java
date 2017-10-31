package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * 思想汇报实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
@Table
public class Report extends BaseEntity {
    @Column(length = 50)
    private String subject;//主题
    @Column(length = 1000)
    private String content;//内容
    @JSONField(serialize=false)
    @ManyToOne
    @JoinColumn(name="user" )
    private User user;//用户ID
    @Transient
    private String username;//汇报人

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        if (user != null){
            return user.getNickname();
        }
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
