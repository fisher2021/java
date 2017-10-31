package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线投票实体类
 * Created by ljw on 2017/5/9.
 */
@Entity
@Table
public class Vote extends BaseEntity {
    @Column(length = 50)
    private String title;//投票标题
    @JSONField(serialize=false)
    @Column(length = 200)
    private String content;//投票描述
    @Column(length = 1500)
    @JSONField(serialize=false)
    private String voter;//投票人

    @JSONField(serialize=false)
    @OneToMany(mappedBy = "vote",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Options> options = new ArrayList<Options>();

    @JSONField(serialize=false)
    @Column(name = "org_list")
    private String orgList;//指派组织
    @Column(name = "org_id")
    private Integer orgId;//管理员组织ID
    @Transient
    @Column(length = 1)
    private Integer voted;

    public Integer getVoted() {
        return voted;
    }

    public void setVoted(Integer voted) {
        this.voted = voted;
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

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgList() {
        return orgList;
    }

    public void setOrgList(String orgList) {
        this.orgList = orgList;
    }
}
