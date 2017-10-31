package com.upd.business.vo;

import java.util.Date;

/**
 * Created by ljw on 2017/5/2.
 */
public class ArticleVo {
    private int id;//文章ID
    private String title;//题目
    private String author;//作者
    private String imgUrl;//图片路径
    private Integer count;//浏览次数
    private String createTime;//文章创建时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime.substring(0,10);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
