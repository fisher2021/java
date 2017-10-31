package com.upd.business.entity;

import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * 意见反馈实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
@Table
public class Advice extends BaseEntity {
    private String content;//内容
    @ManyToOne
    @JoinColumn(name="user" )
    private User user;//用户

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
}
