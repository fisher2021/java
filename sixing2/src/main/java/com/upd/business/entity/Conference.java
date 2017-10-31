package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 预约会议实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
public class Conference extends BaseEntity {
    @Column(length = 50)
    private String subject;//主题

    @Column(length = 30)
    private String time;//时间

    @Column(length = 50)
    private String address;//地点

    @JSONField(serialize = false)
    @ManyToOne
    private User user;//创建用户

    @JSONField(serialize = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="conference_user",joinColumns={@JoinColumn(name="conference_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> users;//接收用户

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
