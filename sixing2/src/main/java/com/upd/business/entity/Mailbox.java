package com.upd.business.entity;

import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 书记信箱实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
public class Mailbox extends BaseEntity {
    @Column(length = 50)
    private String title;//标题
    @Column(length = 1000)
    private String content;//内容
    @ManyToOne
    private User user;//创建人
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="mailbox_user",joinColumns={@JoinColumn(name="mailbox_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> users = new ArrayList<>();//接收人
    @OneToMany(mappedBy = "mailbox",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MailboxReply> mailboxReplies;//信箱回复
    @Column(name = "is_deleted",columnDefinition="bit default 0 ")
    private boolean isDeleted;//删除

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<MailboxReply> getMailboxReplies() {
        return mailboxReplies;
    }

    public void setMailboxReplies(List<MailboxReply> mailboxReplies) {
        this.mailboxReplies = mailboxReplies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
