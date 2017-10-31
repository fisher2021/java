package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统消息实体类
 * Created by ljw on 2017/5/4.
 */
@Entity
@Table
public class Message extends BaseEntity {
    @Column(length = 20)
    private String title;//标题
    @Column(length = 200)
    private String content;//消息内容
    private String org;//指派组织
    @Column(name = "org_list")
    private String orgList;//指派组织
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

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
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
