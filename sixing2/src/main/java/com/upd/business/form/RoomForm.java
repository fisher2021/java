package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by Administrator on 2017/5/19.
 */
public class RoomForm extends QueryForm{
    private String ids;
    private String name;
    private Integer org;

    public RoomForm() {
    }

    public RoomForm(String ids) {
        this.ids = ids;
    }

    @Override
    protected void doParseInternal() {
        in("id",ids);
        like("name",name);
        eq("org.id",org);
        orderBy("type desc");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
