package com.upd.business.service;

import com.upd.business.entity.Report;
import com.upd.business.form.ReportForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 思想汇报业务层
 * Created by ljw on 2017/5/4.
 */
public interface ReportService extends BaseService<Report,Integer> {
    /**
     * 分页查询思想汇报
     * @param page 分页数据
     * @param form 用户ID
     * @return
     */
    public Pagination page(Pagination page, ReportForm form);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteBatch(String ids);
}
