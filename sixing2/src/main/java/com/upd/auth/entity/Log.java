package com.upd.auth.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.entity.BaseEntity;
import com.upd.common.basis.rest.LogType;
import org.hibernate.annotations.DynamicUpdate;
import sun.security.util.Length;

import javax.persistence.*;

/**
 * 日志
 * Created by Administrator on 2017/6/2.
 */
@Entity
@Table
@DynamicUpdate(true)
public class Log extends BaseEntity{

    public Log() {
    }

    public Log(Integer user,Integer other, LogType type, String desc, String result,Operator operator) {
        initTime();
        this.user = user;
        this.type = type;
        this.desc = desc;
        this.result = result;
        this.other = other;
        this.operator = operator;
    }

    @JSONField(serialize=false)
    @Column(length=10,name="user_id")
    private Integer user;//关联用户

    @JSONField(serialize=false)
    @Column(length=10,name="other_id")
    private Integer other;//操作相关id

    @JSONField(serialize=false)
    @Column(length=10)
    @Enumerated(EnumType.ORDINAL)
    private LogType type;//操作类型

//    @Column(name = "[DESC]", nullable = false)
    @Column(name = "description", nullable = false)
    private String desc;//操作描述

    private String result;//操作结果

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
