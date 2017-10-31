package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.auth.entity.Operator;
import com.upd.business.constant.MeetingType;
import com.upd.business.constant.ORGType;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 会议室
 * Created by Administrator on 2017/5/19.
 */
@Entity
@Table
public class Room extends BaseEntity{

    @Column(length = 10)
    private String name;//名称
    @Column(length = 12)
    private String owner;//群主
    @JSONField(serialize = false)
    @ManyToOne
    private ORG org;//组织关系
    @Column(name = "group_id",length = 30)
    private String groupId;//环信群组ID
    @JSONField(serialize = false)
    @Enumerated(EnumType.ORDINAL)
    private MeetingType type;//级别（0:党小组,1:党员,2：党支部）

    @JSONField(serialize = false)
    @ManyToMany
    @JoinTable(name="room_user",joinColumns={@JoinColumn(name="room_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> user;//会议室成员

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ORG getOrg() {
        return org;
    }

    public void setOrg(ORG org) {
        this.org = org;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }
}
