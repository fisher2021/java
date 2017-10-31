package com.upd.business.form;

import com.upd.business.constant.EducationType;
import com.upd.business.constant.ORGType;
import com.upd.common.basis.base.QueryForm;

import javax.persistence.Column;

/**
 * Created by Administrator on 2017/5/3.
 */
public class UserForm extends QueryForm {
    private String account;//账号
    private String phone;//用户手机号
    private String nickname;//用户昵称
    private String password;//密码
    private Integer id;//用户ID
    private Integer orgId;//组织ID
    private String orgName;//组织名称
    public Integer roomId;//会议室ID
    public Integer backlogId;//待办事项id
    public Integer fileId;//资料下载id
    public Integer calendarId;//党务日历id
    public Boolean userHave;//已添加用户
    private ORGType level;//组织等级
    private String ids;//用户ID组合
    private String type;//组织架构筛选

    public UserForm() {
    }

    public UserForm(Integer id) {
        this.id = id;
    }

    public UserForm(String account, String password) {
        this.account = account;
        this.password = password;
    }
    protected void doParseInternal() {
        eq("isDeleted",false);
        like("nickname",nickname);
        eq("account",account);
        eq("phone",phone);
        eq("password",password);
        eq("id",id);
        if(null != roomId) {
            if (userHave) {
                in("id", "SELECT user.id FROM RoomUser WHERE room.id=" + roomId);
            } else {
                notIn("id", "SELECT user.id FROM RoomUser WHERE room.id=" + roomId);
            }
        }
        if(null != backlogId) {
            if (userHave) {
                in("id", "SELECT user.id FROM BacklogUser WHERE backlog.id=" + backlogId);
            } else {
                notIn("id", "SELECT user.id FROM BacklogUser WHERE backlog.id=" + backlogId);
            }
        }
        if(null != calendarId) {
            if (userHave) {
                in("id", "SELECT user.id FROM CalendarUser WHERE calendar.id=" + calendarId);
            } else {
                notIn("id", "SELECT user.id FROM CalendarUser WHERE calendar.id=" + calendarId);
            }
        }
        if(null != fileId) {
            if (userHave) {
                in("id", ids);
            } else {
                notIn("id", ids);
            }
        }
        if (type != null){
            if (type.equals("man")) {
                eq("sex", "男");
            }else if (type.equals("han")){
                like("nation","汉");
            }else if (type.equals("age")){
                le("DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(birth)), '%Y')+0",35);
            }else if (type.equals("edu")){
                ge("education", EducationType.COLLEGE);
            }
        }
        if (orgId == null){
            orderBy("highestOrg.id");
        }else {
            in("id","SELECT user.id FROM ORGUser WHERE org.id="+orgId);
            if (level == ORGType.PARTY_GROUP){
                orderBy("groupRank");
            }else if (level == ORGType.PARTY_COMMITTEE){
                orderBy("committeeRank");
            }else if (level == ORGType.PARTY_BRANCH){
                orderBy("branchRank");
            }
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public ORGType getLevel() {
        return level;
    }

    public void setLevel(ORGType level) {
        this.level = level;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Boolean getUserHave() {
        return userHave;
    }

    public void setUserHave(Boolean userHave) {
        this.userHave = userHave;
    }

    public Integer getBacklogId() {
        return backlogId;
    }

    public void setBacklogId(Integer backlogId) {
        this.backlogId = backlogId;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }
}
