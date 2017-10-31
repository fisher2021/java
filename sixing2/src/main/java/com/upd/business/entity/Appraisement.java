package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 民主评议实体类
 * Created by ljw on 2017/5/9.
 */
@Entity
@Table
public class Appraisement extends BaseEntity {
    @Column(length = 50)
    private String title;//评议标题
    @Column(length = 50)
    @JSONField(serialize=false)
    private String content;//评议描述
    @Column(length = 1500)
    @JSONField(serialize=false)
    private String voter;//投票人
    @JSONField(serialize=false)
    @OneToMany(mappedBy = "appr",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Choice> choices = new ArrayList<Choice>();
    @JSONField(serialize=false)
    @Column(name = "org_list")
    private String orgList;//指派组织
    @JSONField(serialize=false)
    @Column(name = "org_id")
    private Integer orgId;//管理员组织ID

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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
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
