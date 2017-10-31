package com.upd.business.service;

import com.upd.business.constant.MeetingType;
import com.upd.business.entity.Room;
import com.upd.common.basis.service.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface RoomService extends BaseService<Room,Integer> {
    int insert(int room,String ids);

    void delete(int room,String ids);

    /**
     * 添加会议室
     * @param room
     */
    void add(Room room);
    /**
     * 编辑会议室
     * @param room
     */
    void edit(Room room);
    /**
     * 删除会议室
     * @param room
     */
    void deleteRoom(Room room);

    /**
     * 查询用户的会议室
     * @param level
     * @return
     */
    List<Room> getUserRoom(Integer userId,String level);

    /**
     * 转让群主
     * @param roomId
     * @param account
     */
    void newGroupOwner(int roomId,String account);

    /**
     * 判断 单位下的会议类型是否存在
     * @param code
     * @param meetingType
     * @return
     */
    public boolean isExitByCodeAndType(String code, MeetingType meetingType);
}
