package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import com.upd.common.basis.entity.Dict;

import javax.persistence.*;

/**
 * 文章实体类
 * Created by ljw on 2017/5/2.
 */
@Entity
@Table
public class Article extends BaseEntity{
    @Column(length = 50)
    private String title;//标题
    @Column(length = 12)
    private String author;//发布人
    private Boolean targetOut;//是否外链
    private String targetUrl;//外链地址
    @Column(length = 16777215)
    private String content;//内容
    private String imgUrl;//图片路径
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer count;//浏览次数
    @ManyToOne
    @JoinColumn(name="type" )
    private Dict type;//类型
    @Column(name = "org_id")
    private Integer orgId;//管理员组织ID
    @JSONField(serialize=false)
    private Integer rank;//排序

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Dict getType() {
        return type;
    }

    public void setType(Dict type) {
        this.type = type;
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

    public Boolean getTargetOut() {
        return targetOut;
    }

    public void setTargetOut(Boolean targetOut) {
        this.targetOut = targetOut;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
