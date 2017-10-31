package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/5/23.
 */
@Entity
@Table(name="org_user")
public class ORGUser extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "org_id")
    private ORG org;
    @ManyToOne
    @JSONField(serialize = false)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ORG getOrg() {
        return org;
    }

    public void setOrg(ORG org) {
        this.org = org;
    }
}