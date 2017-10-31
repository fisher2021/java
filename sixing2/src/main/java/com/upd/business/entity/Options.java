package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;

/**在线投票选项表实体类
 * Created by ljw on 2017/5/9.
 */
@Entity
@Table
public class Options extends BaseEntity {

    private String content;//投票描述
    private String image;//图片地址
    @Column(length = 50)
    private String title;//投票题目
    private boolean radio;//是否单选
    private String choice;//评价选项
    private String evaluate;//评价结果
    @Transient
    private String rate;//得票率

    @JSONField(serialize=false)
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="vote" )
    private Vote vote;//在线投票

    @Transient
    private Integer voteId;//在线投票ID

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getVoteId() {
        return vote.getId();
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public boolean isRadio() {
        return radio;
    }

    public void setRadio(boolean radio) {
        this.radio = radio;
    }
}
