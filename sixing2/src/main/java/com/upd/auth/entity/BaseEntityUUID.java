package com.upd.auth.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangshao on 2015/12/8.
 */
@MappedSuperclass
public class BaseEntityUUID {
    // 主键 UUID增长
    @Id
    @Column(unique = true, length = 36, nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String createTime;
    String updateTime;

    //设置创建时间 与修改时间默认值
    public void initTime(){
        try {
            Date nowdate = new Date();
            // 格式
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(this.getCreateTime()==null){
                setCreateTime(format
                        .format(nowdate));
            }
            setUpdateTime(format
                    .format(nowdate));
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



}
