package com.upd.business.constant;

/**
 * Created by xiao.tao on 2017/10/19.
 */
public enum MeetingType {
    DXZH("党小组会"), DYDH("党员大会"),DZBWYH("党支部委员会");

    MeetingType(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
