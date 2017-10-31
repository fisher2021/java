package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 工作反馈（展示）实体类
 * Created by ljw on 2017/5/3.
 */
@Entity
@Table
public class JobBack extends BaseEntity {
    @Column(length = 50)
    private String subject;//主题
    private String image1;//图片1
    @JSONField(serialize=false)
    private String desc1;//图片1描述
    private String image2;//图片2
    @JSONField(serialize=false)
    private String desc2;//图片2描述
    @JSONField(serialize=false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date activityDate;//活动时间
    @Column(length = 1000)
    private String content;//详细描述
    @Transient
    private String description1;//图片1组合描述
    @Transient
    private String description2;//图片2组合描述
    @JSONField(serialize=false)
    @ManyToOne
    @JoinColumn(name="user" )
    private User user;//创建人
    @Column(name = "org_id")
    private Integer orgId;//管理员组织ID
    @OneToMany(mappedBy = "jobBack",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<JobBackReply> jobBackReplies;//评论回复
    @JSONField(serialize = false)
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="jobback_user",joinColumns={@JoinColumn(name="jobback_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> users;//点赞用户
    @Transient
    private int replyCount;//回复数;
    @Transient
    private int zanCount;//点赞数;
    @Transient
    private boolean zan;//用户是否已赞;

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }

    public boolean isZan() {
        return zan;
    }

    public void setZan(boolean zan) {
        this.zan = zan;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<JobBackReply> getJobBackReplies() {
        return jobBackReplies;
    }

    public void setJobBackReplies(List<JobBackReply> jobBackReplies) {
        this.jobBackReplies = jobBackReplies;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    @JSONField (format="yyyy年MM月dd日 HH:mm")
    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }


    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc1() {
        return desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public String getDescription1() {
        if (desc1 != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            String desc = format.format(activityDate) + "，" + subject+ "。" + desc1+ "。";
            return desc;
        }
        return desc1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        if (desc2 != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            String desc = format.format(activityDate)+"，"+subject+ "。"+desc2+ "。";
            return desc;
        }
        return desc2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
