package com.upd.business.service;

import com.upd.business.entity.Conference;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 工作任务类业务层
 * Created by ljw on 2017/5/4.
 */
public interface ConferenceService extends BaseService<Conference,Integer> {
    /**
     * 分页查询
     * @param page 分页数据
     * @return
     */
    Pagination getPage(Pagination page, Integer userId, Integer type);

    /**
     * 新增
     * @param conference
     */
    void add(Conference conference);

    /**
     * 修改
     * @param conference
     */
    void edit(Conference conference);

}
