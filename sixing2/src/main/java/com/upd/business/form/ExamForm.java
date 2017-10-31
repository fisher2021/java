package com.upd.business.form;

import com.upd.business.constant.ExamType;
import com.upd.common.basis.base.QueryForm;

/**
 * Created by Administrator on 2017/5/23.
 */
public class ExamForm extends QueryForm {

    public String title;//标题
    public Integer user;//用户id
    public ExamType type;//类型
    private Integer id;

    @Override
    protected void doParseInternal() {
        eq("type", type);
        like("title",title);
        eq("id",id);
        eq("user.id",user);
        orderBy("createTime DESC");
    }

    public ExamType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
