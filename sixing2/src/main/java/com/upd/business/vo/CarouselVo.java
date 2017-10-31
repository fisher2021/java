package com.upd.business.vo;

/**
 * Created by ljw on 2017/5/5.
 */
public class CarouselVo {
    private int id;//轮播图ID
    private String createTime;//轮播图创建时间
    private String title;//轮播图标题

    private String imageUrl;//轮播图地址
    private Boolean targetOut;//是否外链
    private String targetUrl;//轮播图链接地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
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

    public Boolean getTargetOut() {
        return targetOut;
    }

    public void setTargetOut(Boolean targetOut) {
        this.targetOut = targetOut;
    }
}
