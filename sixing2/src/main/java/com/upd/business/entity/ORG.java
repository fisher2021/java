package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.constant.ORGType;
import com.upd.common.basis.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织关系
 * Created by Administrator on 2017/5/11.
 */
@Entity
@Table
public class ORG extends BaseEntity{

    @Column(length = 50)
    private String name;//名称
    @Column(length = 50)
    private String code;//组织编码
    @ManyToOne(fetch = FetchType.LAZY)
    private ORG parent;//父
    @OneToMany(mappedBy="parent",fetch = FetchType.LAZY)
    private List<ORG> children;//子
    @ManyToMany(mappedBy = "orgs",cascade = CascadeType.ALL)
    private List<User> user = new ArrayList<User>();//该组织用户
    @Enumerated(EnumType.ORDINAL)
    private ORGType level;//组织级别
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date changeDate;//下届换届时间
    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public ORGType getLevel() {
        return level;
    }

    public void setLevel(ORGType level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ORG getParent() {
        return parent;
    }

    public void setParent(ORG parent) {
        this.parent = parent;
    }

    public List<ORG> getChildren() {
        return children;
    }

    public void setChildren(List<ORG> children) {
        this.children = children;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
