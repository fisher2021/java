package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**民主评议选项表实体类
 * Created by ljw on 2017/5/9.
 */
@Entity
@Table
public class Choice extends BaseEntity {

    private String choice;//评价选项
    private String evaluate;//评价结果
    @JSONField(serialize=false)
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Appraisement appr;//民主评选
    @Transient
    private Integer apprId;//民主评选ID
    @Column(length = 10)
    private String userName;//被选人

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Appraisement getAppr() {
        return appr;
    }

    public void setAppr(Appraisement appr) {
        this.appr = appr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Integer getApprId() {
        return appr.getId();
    }

    public void setApprId(Integer apprId) {
        this.apprId = apprId;
    }

}
