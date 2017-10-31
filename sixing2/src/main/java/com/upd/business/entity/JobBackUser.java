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
@Table(name="jobback_user")
public class JobBackUser extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="jobback_id")
    private JobBack jobBack;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public JobBack getJobBack() {
        return jobBack;
    }

    public void setJobBack(JobBack jobBack) {
        this.jobBack = jobBack;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
