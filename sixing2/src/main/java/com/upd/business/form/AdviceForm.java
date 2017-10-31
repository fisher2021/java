package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by ljw on 2017/5/3.
 */
public class AdviceForm extends QueryForm {
    private Integer userId;//用户ID
    private String content;//内容
    private String startTime;//开始时间
    private String endTime;//结束时间
    private Integer orgId;//组织ID
    public AdviceForm() {
    }

    public AdviceForm(Integer userId) {
        this.userId = userId;
    }

    protected void doParseInternal() {
        eq("user.isDeleted",false);
        ge("DATE_FORMAT(createTime,'%Y-%m-%d')",startTime);
        le("DATE_FORMAT(createTime,'%Y-%m-%d')",endTime);
        like("content",content);
        eq("user.id",userId);
        eq("user.org.id",orgId);
        orderBy("createTime desc");
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
