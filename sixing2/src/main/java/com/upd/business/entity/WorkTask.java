package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.constant.BacklogType;
import com.upd.business.vo.ORGUserVo;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工作任务实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
@DynamicUpdate
public class WorkTask extends BaseEntity {
    @Column(length = 1000)
    private String content;//任务内容
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "worktask_id")
    private List<FileSave> images = new ArrayList<>();//图片
    @JSONField(serialize = false)
    @ManyToOne
    private User user;//创建用户
    @JSONField(serialize = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="worktask_user",joinColumns={@JoinColumn(name="worktask_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> users;//指派用户

    @OneToMany(mappedBy = "workTask",cascade = CascadeType.ALL)
    private List<WorkTaskReply> workTaskReplies = new ArrayList<>();

    public List<FileSave> getImages() {
        return images;
    }

    public void setImages(List<FileSave> images) {
        this.images = images;
    }

    public List<WorkTaskReply> getWorkTaskReplies() {
        return workTaskReplies;
    }

    public void setWorkTaskReplies(List<WorkTaskReply> workTaskReplies) {
        this.workTaskReplies = workTaskReplies;
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
