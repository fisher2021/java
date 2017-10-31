package com.upd.business.constant;

/**
 * 待办事项类型
 * Created by ljw on 2017/5/23.
 */
public enum BacklogType {

    XWCK("新闻查看"),ZXKS("在线考试"),ZXJF("在线缴费"),
    GZFK("工作展示"),SXHB("思想汇报"),MZPY("民主评议"),ZXTP("在线投票") ;

    BacklogType(String desc) {
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
