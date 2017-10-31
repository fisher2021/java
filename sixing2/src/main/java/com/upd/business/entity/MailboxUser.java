package com.upd.business.entity;

import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/5/23.
 */
@Entity
@Table(name="mailbox_user")
public class MailboxUser extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="mailbox_id")
    private Mailbox mailbox;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "is_deleted",columnDefinition="bit default 0 ")
    private boolean isDeleted;//删除
    @Column(name = "is_read",columnDefinition="bit default 0 ")
    private boolean isRead;//阅读状态

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
