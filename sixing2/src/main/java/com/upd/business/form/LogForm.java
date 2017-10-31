package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;
import com.upd.common.basis.rest.LogType;

/**
 * Created by Administrator on 2017/6/2.
 */
public class LogForm extends QueryForm{

    private Integer user;//关联用户
    private LogType type;//操作类型

    @Override
    protected void doParseInternal() {
        eq("user",user);
        eq("type",type);
        orderBy("createTime desc");
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }
}
