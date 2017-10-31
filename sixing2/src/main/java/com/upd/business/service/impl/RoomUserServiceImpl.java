package com.upd.business.service.impl;

import com.upd.business.dao.RoomUserDao;
import com.upd.business.entity.Room;
import com.upd.business.entity.RoomUser;
import com.upd.business.entity.User;
import com.upd.business.form.RoomUserForm;
import com.upd.business.service.RoomService;
import com.upd.business.service.RoomUserService;
import com.upd.business.service.UserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.easemob.Easemob;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/23.
 */
@Service("roomUserService")
public class RoomUserServiceImpl extends BaseServiceImpl<RoomUser, Integer> implements RoomUserService {

    @Autowired
    public RoomUserDao dao;
    @Autowired
    public UserService userService;
    @Autowired
    RoomService roomService;

    @Autowired
    public void setBaseDao(RoomUserDao dao) {
        this.baseDao = dao;
    }

    public Pagination userPage(Pagination page, RoomUserForm form){
        String hql="FROM User WHERE"+form.toQueryDescription();
        return userService.findbypage(page,hql,form.values());
    }

    @Override
    public void addBatch(Integer roomId, Integer[] ids) {
        Room room = roomService.get(roomId);
        Map<String,Object> users = new HashMap();
        String[] names = new String[ids.length];
        for (int i=0;i<ids.length;i++){
            User user = userService.get(ids[i]);
            RoomUser roomUser = new RoomUser();
            roomUser.setUser(user);
            roomUser.setRoom(room);
            names[i] = user.getAccount();
            roomUser.initTime();
            dao.save(roomUser);
        }
        users.put("usernames",names);
        Easemob.addGroupUser(room.getGroupId(),users);
    }

    @Override
    public void deleteUser(Integer roomId, Integer id) {
        RoomUser roomUser = findUser(roomId,id);
        if (roomUser != null){
            dao.delete(roomUser);
            Easemob.removeUser(roomUser.getRoom().getGroupId(),roomUser.getUser().getAccount());
        }
    }

    @Override
    public RoomUser findUser(Integer roomId, Integer id) {
        return dao.findOne("from RoomUser where room.id = ? and user.id  = ?",roomId,id);
    }
}
