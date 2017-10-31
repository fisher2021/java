package com.upd.business.service;

import com.upd.business.entity.ConferenceUser;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * Created by ljw on 2017/5/4.
 */
public interface ConferenceUserService extends BaseService<ConferenceUser,Integer> {
    /**
     * 按工作任务分页查询
     * @param page 分页数据
     * @return
     */
    Pagination pageByConferenceId(Pagination page, Integer conferenceId);

    /**
     * 新增
     * @param conferenceUser
     */
    void add(ConferenceUser conferenceUser);

    /**
     * 修改
     * @param conferenceUser
     */
    void edit(ConferenceUser conferenceUser);

    /**
     * 通过用户查询
     * @param userId
     * @return
     */
    Pagination findByUser(Pagination page, Integer userId);


}
