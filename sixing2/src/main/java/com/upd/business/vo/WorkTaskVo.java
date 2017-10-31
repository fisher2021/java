package com.upd.business.vo;

import com.upd.business.entity.FileSave;
import com.upd.business.entity.WorkTaskReply;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/5/5.
 */
public class WorkTaskVo {
    private int id;//ID
    private String createTime;//创建时间
    private String content;//内容
    private UserVo createUser;//创建人
    private List<WorkTaskReply> workTaskReplies;//回复
    private int replyCount;//回复数
    private int receivedUsersCount;//接收用户数
    private List<UserVo> receivedUsers;//接收用户
    private List<FileSave> images = new ArrayList<>();//图片

    public List<FileSave> getImages() {
        return images;
    }

    public void setImages(List<FileSave> images) {
        this.images = images;
    }

    public int getReceivedUsersCount() {
        return receivedUsersCount;
    }

    public void setReceivedUsersCount(int receivedUsersCount) {
        this.receivedUsersCount = receivedUsersCount;
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

    public List<WorkTaskReply> getWorkTaskReplies() {
        return workTaskReplies;
    }

    public void setWorkTaskReplies(List<WorkTaskReply> workTaskReplies) {
        this.workTaskReplies = workTaskReplies;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
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
