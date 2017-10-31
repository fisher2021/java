package com.upd.business.service;

import com.upd.business.entity.Advice;
import com.upd.business.form.AdviceForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 意见反馈类业务层
 * Created by ljw on 2017/5/4.
 */
public interface AdviceService extends BaseService<Advice,Integer> {
    /**
     * 分页查询意见反馈
     * @param page 分页数据
     * @return
     */
    Pagination page(Pagination page, AdviceForm form);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(String ids);
}
