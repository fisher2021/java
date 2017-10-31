package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by ljw on 2017/5/23.
 */
@Entity
@Table(name="data_user")
public class DataUser extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="data_id")
    private Data data;
    @ManyToOne
    @JSONField(serialize=false)
    @JoinColumn(name="user_id")
    private User user;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
