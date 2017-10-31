package com.upd.business.vo;

import com.upd.business.constant.ExamQuestionsType;

/**
 * Created by Administrator on 2017/5/24.
 */
public class QuestionsVo {

    private String title;//描述

    private ExamQuestionsType type;

    private String options;//选项 ‘|’ 分割

    private String optionsResult;//选项结果

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExamQuestionsType getType() {
        return type;
    }

    public void setType(ExamQuestionsType type) {
        this.type = type;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptionsResult() {
        return optionsResult;
    }

    public void setOptionsResult(String optionsResult) {
        this.optionsResult = optionsResult;
    }
}
