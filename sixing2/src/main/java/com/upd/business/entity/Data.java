package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/9/29.
 */
@Entity
@DynamicUpdate
public class Data extends File{
    private String title;//标题
    @JSONField(serialize = false)
    @Column(name = "org_id")
    private Integer orgId;//管理员组织ID
    @JSONField(serialize = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="data_user",joinColumns={@JoinColumn(name="data_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> users = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
