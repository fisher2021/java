package com.upd.business.service;

import com.upd.business.constant.BacklogType;
import com.upd.business.entity.BacklogUser;
import com.upd.business.entity.DataUser;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * Created by ljw on 2017/5/23.
 */
public interface DataUserService extends BaseService<DataUser,Integer> {

    /**
     * 通过用户ID分页查询
     * @param page 分页数据
     * @param userId
     * @return
     */
    Pagination pageByUser(Pagination page, Integer userId);
    /**
     * 删除指派对象
     * @param id
     * @param dataId
     */
    void deleteUser(Integer dataId, Integer id);
}
