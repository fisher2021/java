package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by ljw on 2017/5/3.
 */
public class CarouselForm extends QueryForm{
    private String title;//文章标题
    private String type;//词典类型
    private Integer rank;//排序

    @Override
    protected void doParseInternal() {
        like("title",title);
        eq("type.dictId",type);
        orderBy("rank");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
