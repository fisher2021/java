package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import com.upd.common.basis.entity.Dict;

import javax.persistence.*;

/**
 * 轮播图实体类
 * @author cao.xin
 * 2017年1月4日
 */
@Entity
@Table
public class Carousel extends BaseEntity {
    @Column(length = 50)
    private String title;//轮播图标题
    @Column(length = 10)
    private String author;//发布人
    @Column(length = 16777215)
    private String content;//文本
	private String imageUrl;//轮播图地址
    private Boolean targetOut;//是否外链
	private String targetUrl;//轮播图链接地址
    private Integer rank;//排序
	@ManyToOne
	@JoinColumn(name="type" )
	Dict type;//词典ID

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getTargetOut() {
        return targetOut;
    }

    public void setTargetOut(Boolean targetOut) {
        this.targetOut = targetOut;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
