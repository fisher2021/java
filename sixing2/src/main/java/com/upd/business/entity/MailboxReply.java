package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.vo.ORGUserVo;
import com.upd.business.vo.UserVo;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**
 * 书记信箱回复实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
public class MailboxReply extends BaseEntity {
    private String content;//内容
    @JSONField(serialize = false)
    @ManyToOne
    private User user;//回复人

    @JSONField(serialize = false)
    @ManyToOne
    private Mailbox mailbox;//信箱

    @Transient
    private UserVo replyUser;//回复人VO

    public UserVo getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(UserVo replyUser) {
        this.replyUser = replyUser;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
