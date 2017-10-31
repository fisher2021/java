package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by Administrator on 2017/5/23.
 */
public class RoomUserForm extends QueryForm {

    private String userName;
    private Integer roomId;//会议室ID
    private Integer noRoom;//不在此会议室中的
    private Integer orgId;//组织ID
    private String orgName;//组织ID

    @Override
    protected void doParseInternal() {
        like("user.nickname",userName);
        eq("org.id",orgId);
        eq("room.id",roomId);
        notIn("id","SELECT user.id FROM RoomUser WHERE room.id="+noRoom);
        orderBy("createTime desc");
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getNoRoom() {
        return noRoom;
    }

    public void setNoRoom(Integer noRoom) {
        this.noRoom = noRoom;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
