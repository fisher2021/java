package com.upd.business.vo;

/**
 * Created by ljw on 2017/5/5.
 */
public class AdviceVo {
    private int id;//意见ID
    private String createTime;//意见创建时间
    private String content;//内容


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
