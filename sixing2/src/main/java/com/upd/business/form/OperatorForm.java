package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by Administrator on 2017/5/3.
 */
public class OperatorForm extends QueryForm {

    private String account;//账号
    private String name;//用户昵称
    private String password;//密码
    private Integer id;//用户ID
    private Integer orgId;//用户组织
    private String orgName;//组织名称
    private String orgCode;//单位编码
    public OperatorForm() {
    }

    public OperatorForm(Integer id) {
        this.id = id;
    }

    public OperatorForm(String account, String password) {
        this.account = account;
        this.password = password;
    }

    protected void doParseInternal() {
        eq("account",account);
        eq("password",password);
        eq("name",name);
        eq("id",id);
        eq("org.id",orgId);
        notEq("deleted",1);
        likeR("org.code", orgCode);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
