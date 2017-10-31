package com.upd.business.form;

import com.upd.business.constant.BacklogType;
import com.upd.common.basis.base.QueryForm;

/**
 * Created by ljw on 2017/5/3.
 */
public class BacklogForm extends QueryForm {
    private Integer id;
    private Integer userId;//用户ID
    private BacklogType type;//事项类型
    private String startTime;//开始时间
    private String endTime;//结束时间
    private Integer orgId;//组织ID

    protected void doParseInternal() {
        ge("DATE_FORMAT(doDate,'%Y-%m-%d')",startTime);
        le("DATE_FORMAT(doDate,'%Y-%m-%d')",endTime);
        eq("type",type);
        eq("user.id",userId);
        in("id",id);
        eq("orgId",orgId);
        orderBy("createTime desc");
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BacklogType getType() {
        return type;
    }

    public void setType(BacklogType type) {
        this.type = type;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
