package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 党务日历实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
@Table
public class Calendar extends BaseEntity {
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date doDate;//日期
    @Column(length = 15)
    private String subject;//主题
    private String content;//内容
    @JSONField(serialize=false)
    @ManyToMany
    @JoinTable(name="calendar_user",joinColumns={@JoinColumn(name="calendar_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> user;//指派用户
    @Column(name = "org_id")
    private Integer orgId;//管理员组织ID

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
    @JSONField (format="yyyy-MM-dd")
    public Date getDoDate() {
        return doDate;
    }

    public void setDoDate(Date doDate) {
        this.doDate = doDate;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
