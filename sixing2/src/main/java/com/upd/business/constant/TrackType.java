package com.upd.business.constant;

/**
 * 党迹类型
 * Created by ljw on 2017/5/23.
 */
public enum TrackType {

    ZXKS("参加在线考试"), GZFK("完成工作展示"),SXHB("完成思想汇报"),MZPY("参加民主评议"),ZXTP("参加在线投票") ;

    TrackType(String desc) {
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
