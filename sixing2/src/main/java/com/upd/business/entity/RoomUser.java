package com.upd.business.entity;

import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/5/23.
 */
@Entity
@Table(name="room_user")
public class RoomUser extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
