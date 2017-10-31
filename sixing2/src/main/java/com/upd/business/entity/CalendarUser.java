package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by ljw on 2017/5/23.
 */
@Entity
@Table(name="calendar_user")
public class CalendarUser extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="calendar_id")
    private Calendar calendar;
    @ManyToOne
    @JSONField(serialize=false)
    @JoinColumn(name="user_id")
    private User user;
    @Transient
    private Integer userId;//用户ID

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserId() {
        return user.getId();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
