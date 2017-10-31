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
@Table(name="conference_user")
public class ConferenceUser extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="conference_id")
    private Conference conference;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
