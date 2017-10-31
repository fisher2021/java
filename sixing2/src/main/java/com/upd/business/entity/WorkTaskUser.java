package com.upd.business.entity;

import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/5/23.
 */
@Entity
@Table(name="worktask_user")
public class WorkTaskUser extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="worktask_id")
    private WorkTask workTask;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public WorkTask getWorkTask() {
        return workTask;
    }

    public void setWorkTask(WorkTask workTask) {
        this.workTask = workTask;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
