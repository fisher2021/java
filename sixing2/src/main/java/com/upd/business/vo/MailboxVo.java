package com.upd.business.vo;

import com.upd.business.entity.MailboxReply;
import com.upd.business.entity.WorkTaskReply;

import java.util.List;

/**
 * Created by ljw on 2017/5/5.
 */
public class MailboxVo {
    private int id;
    private String createTime;//创建时间
    private String title;//标题
    private String content;//内容
    private UserVo createUser;//创建人
    private List<MailboxReply> mailboxReplies;//回复
    private List<UserVo> receivedUsers;//接收人
    private boolean isRead;//阅读状态

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
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

    public List<UserVo> getReceivedUsers() {
        return receivedUsers;
    }

    public void setReceivedUsers(List<UserVo> receivedUsers) {
        this.receivedUsers = receivedUsers;
    }

    public UserVo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserVo createUser) {
        this.createUser = createUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
