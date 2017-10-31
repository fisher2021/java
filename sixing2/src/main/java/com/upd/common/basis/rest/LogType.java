package com.upd.common.basis.rest;

import java.text.MessageFormat;

/**
 * 操作日志记录类型
 * 0、类型 1、数据 例：新增用户[阿拉伯]
 * Created by hui on 2017/3/16.
 */
public enum LogType {
    POINTS("积分"),
    ADD("新增{0}[{1}]"),UP("更新{0}[{1}]"),DELETE("删除{0}[{1}]");

    private String desc;

    LogType(String desc) {
        this.desc = desc;
    }

    public String getDesc(String model,String value) {
        return  MessageFormat.format(desc, model, value);
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
