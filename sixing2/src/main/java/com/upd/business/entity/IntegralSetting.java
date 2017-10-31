package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upd.common.basis.entity.BaseEntity;
import com.upd.common.basis.entity.Dict;

import javax.persistence.*;
import java.util.List;

/**
 * Created by xiao.tao on 2017/10/26.
 */
@Entity
@Table(name = "integral_setting")
public class IntegralSetting extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="type" )
    private Dict type;//类型

    private String value;//积分值

    @ManyToOne
    @JoinColumn(name="parent")
    private IntegralSetting parent;

    @JSONField(serialize=false)
    @OneToMany(mappedBy = "parent" )
    @JsonIgnore(value = true)
    List<IntegralSetting> settingList;

    public Dict getType() {
        return type;
    }

    public void setType(Dict type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public IntegralSetting getParent() {
        return parent;
    }

    public void setParent(IntegralSetting parent) {
        this.parent = parent;
    }

    public List<IntegralSetting> getSettingList() {
        return settingList;
    }

    public void setSettingList(List<IntegralSetting> settingList) {
        this.settingList = settingList;
    }
}
