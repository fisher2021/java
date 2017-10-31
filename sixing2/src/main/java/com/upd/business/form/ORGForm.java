package com.upd.business.form;

import com.upd.business.constant.ORGType;
import com.upd.common.basis.base.QueryForm;

/**
 * Created by Administrator on 2017/5/17.
 */
public class ORGForm extends QueryForm{
    private String name;
    private ORGType type;
    private Integer messageId;//系统消息id
    private Boolean orgHave;//已添加组织
    private Integer orgId;//组织ID
    private String orgIds;

    @Override
    protected void doParseInternal() {
        eq("level",type);
        like("name",name);
        if(null != messageId) {
            if (orgId != null){
                eq("id",orgId);
            }else {
                if (orgHave) {
                    in("id", orgIds);
                } else {
                    notIn("id", orgIds);
                }
            }
        }
        orderBy("level");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ORGType getType() {
        return type;
    }

    public void setType(ORGType type) {
        this.type = type;
    }

    public Boolean getOrgHave() {
        return orgHave;
    }

    public void setOrgHave(Boolean orgHave) {
        this.orgHave = orgHave;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }
}
