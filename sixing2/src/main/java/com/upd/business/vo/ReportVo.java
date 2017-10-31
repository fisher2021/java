package com.upd.business.vo;

/**
 * Created by ljw on 2017/5/5.
 */
public class ReportVo {
    private int id;//汇报ID
    private String createTime;//汇报更新时间
    private String subject;//主题
    private String content;//内容

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

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
