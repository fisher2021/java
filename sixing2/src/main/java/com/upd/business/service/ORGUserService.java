package com.upd.business.service;

import com.upd.business.entity.ORGUser;
import com.upd.common.basis.service.BaseService;

import java.util.List;

/**
 * Created by ljw on 2017/5/23.
 */
public interface ORGUserService extends BaseService<ORGUser,Integer> {
    /**
     * 通过用户删除
     * @return
     */
    void deleteByUser(Integer id);
}
