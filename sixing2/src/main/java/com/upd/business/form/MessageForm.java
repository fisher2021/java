package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by ljw on 2017/5/3.
 */
public class MessageForm extends QueryForm {

    private String title;//主题
    private Integer orgId;//组织ID
    public MessageForm() {
    }

    protected void doParseInternal() {

        like("title",title);
        eq("orgId",orgId);
        orderBy("createTime desc");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
