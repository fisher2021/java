package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.vo.ORGUserVo;
import com.upd.business.vo.UserVo;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作任务回复实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
public class WorkTaskReply extends BaseEntity {
    private String content;//内容
    @JSONField(serialize = false)
    @ManyToOne
    private User user;//用户
    @JSONField(serialize = false)
    @ManyToOne
    private WorkTask workTask;//工作任务
    @Transient
    private UserVo replyUser;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "worktask_reply_id")
    private List<FileSave> images = new ArrayList<>();//图片

    public List<FileSave> getImages() {
        return images;
    }

    public void setImages(List<FileSave> images) {
        this.images = images;
    }

    public UserVo getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(UserVo replyUser) {
        this.replyUser = replyUser;
    }

    public WorkTask getWorkTask() {
        return workTask;
    }

    public void setWorkTask(WorkTask workTask) {
        this.workTask = workTask;
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
