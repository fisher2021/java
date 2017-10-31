package com.upd.business.constant;

/**
 * 学历类型
 * Created by ljw on 2017/5/23.
 */
public enum EducationType {

    PRIMARY("小学"),JUNIOR("初中"),HIGH("高中"), SECONDARY("中专"),
    COLLEGE("大专"),BACHELOR("本科"),MASTER("硕士"),DOCTOR("博士") ;

    EducationType(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public String toString() {
        return this.desc;
    }
}
