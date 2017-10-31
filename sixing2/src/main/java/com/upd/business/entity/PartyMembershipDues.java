package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.entity.BaseEntity;
import com.upd.common.util.BigDecimalKit;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 党费
 * Created by Administrator on 2017/5/31.
 */
@Entity
@Table(name = "party_membership_dues")
public class PartyMembershipDues extends BaseEntity {

    @JSONField(serialize=false)
    private String trade_no;//支付订单号
    @Column(columnDefinition="double default 0 ",nullable = false,precision = 6, scale = 2)
    private BigDecimal amount;//应缴
    @Column(columnDefinition="double default 0 ", name="fee_received",nullable = false, precision = 6, scale = 2)
    private BigDecimal feeReceived;//已收费
    @Transient
    private BigDecimal payment;//待缴金额
    @Column(length = 7)
    private String year;
    @Column(columnDefinition="bit default 0 ")
    private Boolean status;//是否缴纳
    @Transient
    @JSONField(serialize=false)
    private String payed;
    @JSONField(serialize=false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user" )
    private User user;//用户ID

    @JSONField(serialize=false)
    @Column(name = "pay_type",length = 5)
    private String payType;//支付方式

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public BigDecimal getFeeReceived() {
        return feeReceived;
    }

    public void setFeeReceived(BigDecimal feeReceived) {
        this.feeReceived = feeReceived;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getPayment() {
        BigDecimal trade_free= BigDecimalKit.sub(amount.doubleValue(),feeReceived.doubleValue());
        return trade_free;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}
