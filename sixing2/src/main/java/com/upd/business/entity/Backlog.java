package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.business.constant.BacklogType;
import com.upd.common.basis.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 待办事项实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
@Table
public class Backlog extends BaseEntity {
    @Column(length = 50)
    private String subject;//主题
    @JSONField(serialize=false)
    @ManyToMany
    @JoinTable(name="backlog_user",joinColumns={@JoinColumn(name="backlog_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> user;//指派用户
    @JSONField(serialize=false)
    @Enumerated(EnumType.ORDINAL)
    private BacklogType type;//类型
    @Column(name="mission_id")
    private Integer missionId;//任务ID
    @Column(length = 10)
    private String year;//缴费月份
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date doDate;//指派日期
    @Column(name = "org_id")
    private Integer orgId;//管理员组织ID

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDoDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(doDate);
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

    public BacklogType getType() {
        return type;
    }

    public void setType(BacklogType type) {
        this.type = type;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

}
