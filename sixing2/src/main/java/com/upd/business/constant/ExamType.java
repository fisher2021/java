package com.upd.business.constant;

/**
 * 考试类型
 * Created by Administrator on 2017/5/23.
 */
public enum ExamType {

    DS("党史"),DZ("党章党规"),XLJH("系列讲话"),LLTS("理论推送");

    ExamType(String desc) {
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
