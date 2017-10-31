package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by Administrator on 2017/5/31.
 */
public class PartyMembershipDuesForm extends QueryForm{

    private String name;
    private String phone;
    private Integer user;
    private String year;
    private String account;
    private Boolean status;//是否缴纳
    private boolean mine;//我的
    public Integer orgId;//组织ID
    private String orgName;//组织名称

    @Override
    protected void doParseInternal() {
        eq("user.isDeleted",false);
        like("user.nickname",name);
        eq("user.phone",phone);
        eq("year",year);
        eq("user.id",user);
        eq("status",status);
        eq("user.account",account);
        if (null != orgId){
            in("user.id","SELECT user.id FROM ORGUser WHERE org.id="+orgId);
        }
        orderBy("year DESC");
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
