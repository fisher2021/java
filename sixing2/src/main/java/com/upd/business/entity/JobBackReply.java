package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.vo.ORGUserVo;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 工作展示回复实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
public class JobBackReply extends BaseEntity {
    private String content;//内容
    @JSONField(serialize = false)
    @ManyToOne
    private User user;//用户
    @JSONField(serialize = false)
    @ManyToOne
    private JobBack jobBack;//工作任务
    @Transient
    private ORGUserVo replyUser;//回复人

    public ORGUserVo getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(ORGUserVo replyUser) {
        this.replyUser = replyUser;
    }

    public JobBack getJobBack() {
        return jobBack;
    }

    public void setJobBack(JobBack jobBack) {
        this.jobBack = jobBack;
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
