package com.upd.business.constant;

/**
 * Created by Administrator on 2017/5/23.
 */
public enum ExamQuestionsType {

    SINGLE("单选"),MORE("多选"),JUDGE("判断");

    ExamQuestionsType(String desc) {
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
