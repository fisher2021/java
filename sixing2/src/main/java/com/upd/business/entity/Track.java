package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.constant.TrackType;
import com.upd.common.basis.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 党迹实体类
 * Created by ljw on 2017/6/1.
 */
@Entity
@Table
public class Track extends BaseEntity {

    @Column(length = 100)
    private String subject;//主题
    @Enumerated(EnumType.ORDINAL)
    private TrackType type;//类型

    @JSONField(serialize=false)
    @ManyToOne
    @JoinColumn(name="user" )
    private User user;//用户
    @Transient
    private String date;//时间

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type.getDesc();
    }

    public void setType(TrackType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return getCreateTime().substring(0,10);
    }

    public void setDate(String date) {
        this.date = date;
    }
}
