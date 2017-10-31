package com.upd.common.basis.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangshao on 2015/12/8.
 */
@MappedSuperclass
public class BaseEntity {
    // 主键 UUID增长
    @Id
    @SequenceGenerator(name = "mySeqGenerator",initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="mySeqGenerator")
    protected Integer id;

    @Column(length = 20)
    String createTime;

    @JSONField(serialize=false)
    @Column(length = 20)
    String updateTime;

    @JSONField(serialize=false)
    @ManyToOne
    protected Operator operator;//创建人

    @JSONField(serialize=false)
    public HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }

    //设置创建时间 与修改时间默认值
    public void initTime(){
        try {
            Date nowdate = new Date();
            // 格式
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(this.getCreateTime()==null){
                setCreateTime(format.format(nowdate));
            }
            setUpdateTime(format.format(nowdate));
            if (operator == null){
                Operator operator = (Operator) getSession().getAttribute("logedOperator");
                setOperator(operator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
