package com.upd.business.service;

import com.upd.business.entity.RoomUser;
import com.upd.business.form.RoomUserForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface RoomUserService extends BaseService<RoomUser,Integer> {

    Pagination userPage(Pagination page, RoomUserForm form);
    /**
     * 批量会议室成员
     * @param ids
     */
    void addBatch(Integer roomId,Integer[] ids);
    /**
     * 删除会议室成员
     * @param id
     * @param roomId
     */
    void deleteUser(Integer roomId,Integer id);
    /**
     * 查找会议室成员
     * @param id
     * @param roomId
     */
    RoomUser findUser(Integer roomId,Integer id);
}
