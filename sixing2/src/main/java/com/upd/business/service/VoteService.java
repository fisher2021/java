package com.upd.business.service;


import com.upd.business.entity.Vote;
import com.upd.business.form.VoteForm;
import com.upd.common.basis.base.QueryForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * 在线投票类业务层
 * Created by ljw on 2017/5/4.
 */
public interface VoteService extends BaseService<Vote,Integer> {
    /**
     * 查询在线投票
     * @param id
     * @return
     */
    List<Vote> search(Integer id);
    /**
     * 按组织查询在线投票
     * @param userId
     * @return
     */
    List<Vote> searchByOrg(Integer userId);
    /**
     * 新增在线投票
     * @param vote
     * @return
     */
    Vote add(Vote vote);
    /**
     * 修改在线投票
     * @param vote
     * @return
     */
    Vote edit(Vote vote);

}
