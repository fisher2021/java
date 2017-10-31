package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by ljw on 2017/5/23.
 */
@Entity
@Table(name="backlog_user")
public class BacklogUser extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="backlog_id")
    private Backlog backlog;
    @ManyToOne
    @JSONField(serialize=false)
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "is_finished",columnDefinition="INT default 0",nullable=false)
    @JSONField(serialize=false)
    private Boolean finish;//是否完成

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }
}
