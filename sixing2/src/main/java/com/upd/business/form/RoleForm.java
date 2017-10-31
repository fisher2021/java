package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by zhenghui on 2017/10/11 0011.
 */
public class RoleForm extends QueryForm {

    private String name;

    @Override
    protected void doParseInternal() {
        eq("name",name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
