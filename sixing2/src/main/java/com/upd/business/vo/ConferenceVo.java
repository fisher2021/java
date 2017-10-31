package com.upd.business.vo;

import java.util.List;

/**
 * Created by ljw on 2017/5/5.
 */
public class ConferenceVo {
    private int id;//ID
    private String createTime;//创建时间
    private String subject;//主题
    private String time;//时间
    private String address;//地点
    private UserVo createUser;//创建人
    private int receivedUsersCount;//接收用户数
    private List<UserVo> receivedUsers;//接收用户
    private boolean isEnd;// 会议状态结束

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
