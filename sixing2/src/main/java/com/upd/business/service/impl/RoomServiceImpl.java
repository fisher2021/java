package com.upd.business.service.impl;

import com.alibaba.druid.sql.visitor.functions.Insert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upd.business.constant.MeetingType;
import com.upd.business.constant.ORGType;
import com.upd.business.dao.RoomDao;
import com.upd.business.entity.Room;
import com.upd.business.entity.RoomUser;
import com.upd.business.entity.User;
import com.upd.business.form.RoomForm;
import com.upd.business.service.RoomService;
import com.upd.business.service.RoomUserService;
import com.upd.business.service.UserService;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.easemob.Easemob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Administrator on 2017/5/19.
 */
@Service("roomService")
public class RoomServiceImpl extends BaseServiceImpl<Room, Integer> implements RoomService {

    @Autowired
    public RoomDao dao;
    @Autowired
    UserService userService;
    @Autowired
    RoomUserService roomUserService;
    @Autowired
    public void setBaseDao(RoomDao dao) {
        this.baseDao = dao;
    }

    public int insert(int room,String ids){
        String group_id = this.getGroupId(room);
        RoomForm form = new RoomForm(ids);
        List<User> userList = userService.find("from User where"+form.toQueryDescription(),form.values());
        if (userList != null && userList.size() != 0){
            String[] arry = new String[userList.size()];
            for (int i=0;i<arry.length;i++) {
                arry[i] = userList.get(i).getAccount();
            }
            Map<String,String[]> users = new HashMap<>();
            users.put("usernames",arry);
            Easemob.addGroupUser(group_id,users);
        }
        return dao.excuteBySql("INSERT INTO room_user (room_user.room_id,room_user.user_id,room_user.createTime,room_user.updateTime)" +
                        "SELECT "+room+",u.id,NOW(),NOW() FROM `user` u " +
                        "WHERE FIND_IN_SET(u.id,'"+ids+"') and " +
                        "id not in (select r.user_id FROM room_user r WHERE FIND_IN_SET(r.user_id,'"+ids+"') and r.room_id="+room+")");
    }

    @Override
    public void delete(int room, String ids) {
        String group_id = this.getGroupId(room);
        RoomForm form = new RoomForm(ids);
        List<RoomUser> roomUsers = roomUserService.find("from RoomUser where"+form.toQueryDescription(),form.values());
        List<User> userList = new ArrayList<>();
        for (RoomUser r:roomUsers){
            userList.add(userService.get(r.getUser().getId()));
        }
        if (userList != null && userList.size() != 0){
            StringBuffer sb = new StringBuffer();
            for (User u:userList){
                sb.append(u.getAccount()).append(",");
            }
            String users = sb.substring(0,sb.length()-1);
            Easemob.deleteGroupUser(group_id,users);
        }
        roomUserService.deletemore(ids);

    }

    @Override
    public void add(Room room) {
        String result = Easemob.createGroup(room.getName(),room.getName(),true,100,room.getOwner());
        JSONObject object = JSON.parseObject(JSON.parseObject(result).get("data").toString());
        room.setGroupId(object.getString("groupid"));//获取群组ID
        //room.setLevel(ORGType.PARTY_GROUP);//将新增会议室设置为党小组会
        room.initTime();
        dao.save(room);
        //将群主添加到RoomUser
        RoomUser roomUser = new RoomUser();
        roomUser.setRoom(room);
        roomUser.setUser(userService.findByAccount(room.getOwner()));
        roomUser.initTime();
        roomUserService.save(roomUser);
    }

    @Override
    public void edit(Room room) {
        Room room1 = dao.get(room.getId());
        room1.setName(room.getName());
        room1.setOrg(room.getOrg());
        //修改群主
        if (room.getOwner() != room1.getOwner()){
            User user = userService.findByAccount(room.getOwner());//查找群主
            if (roomUserService.findUser(room.getId(),user.getId()) == null){
                throw new BusinessException(RestErrorCode.PARAM,"群主不在此群！");
            }
            room1.setOwner(room.getOwner());
            Easemob.newGroupOwner(room1.getGroupId(),room.getOwner());
        }

        User user = userService.findByAccount(room.getOwner());//查找群主用户
        if (roomUserService.findUser(room.getId(),user.getId()) == null){//判断
            RoomUser roomUser = roomUserService.findUser(room.getId(),userService.findByAccount(room.getOwner()).getId());
            roomUser.setUser(user);
            roomUser.initTime();
            roomUserService.update(roomUser);

        }
        dao.update(room);
    }

    @Override
    public void deleteRoom(Room room) {
        Easemob.deleteGroup(room.getGroupId());
        dao.delete(room);
    }

    @Override
    public List<Room> getUserRoom(Integer userId, String level) {
        if (level.equals("all")){
            return dao.findbySQL("select * from room where id in (select room_id from room_user where user_id = ?) order by type desc",userId);
        }else {
            return dao.findbySQL("select * from room where id in (select room_id from room_user where user_id = ?) and type = ? order by createTime",userId,level);
        }
    }

    @Override
    public void newGroupOwner(int roomId, String account) {
        Room room = dao.get(roomId);
        room.setOwner(account);
        dao.update(room);
        Easemob.newGroupOwner(room.getGroupId(),account);
    }

    public String getGroupId(int room){
        String group_id = "";
        if (room == 1){
            group_id = Easemob.DEMOCRATIC_LIFE_ID;
        }else if (room == 2){
            group_id = Easemob.PARTY_MEMBER_ID;
        }else if (room == 3){
            group_id = Easemob.PARTY_GROUP_ID;
        }else if (room == 4){
            group_id = Easemob.PARTY_BRANCH_ID;
        }
        return group_id;
    }

    /**
     * 判断 单位下的会议类型是否存在
     *
     * @param code
     * @param meetingType
     * @return
     */
    @Override
    public boolean isExitByCodeAndType(String code, MeetingType meetingType) {
        Room room = dao.findOne("from Room where type = ? and operator.org.code like ? ",meetingType,code+"%");
        return room!=null ? true : false;
    }
}
