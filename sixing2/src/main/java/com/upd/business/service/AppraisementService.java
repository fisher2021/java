package com.upd.business.service;


import com.upd.business.entity.Appraisement;
import com.upd.business.form.AppraisementForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * 民主评议类业务层
 * Created by ljw on 2017/5/4.
 */
public interface AppraisementService extends BaseService<Appraisement,Integer> {
    /**
     * 查询民主评议
     * @param id
     * @return
     */
    List<Appraisement> search( Integer id);
    /**
     * 分页查询民主评议
     * @param page 分页数据
     * @return
     */
    Pagination page(Pagination page, AppraisementForm form);
    /**
     * 按组织分页查询民主评议
     * @return
     */
    List<Appraisement> searchByOrg(Integer userId);
    /**
     * 新增民主评议
     * @param appraisement
     * @return
     */
    Appraisement add(Appraisement appraisement);
    /**
     * 修改民主评议
     * @param appraisement
     * @return
     */
    Appraisement edit(Appraisement appraisement);

    /**
     * 批量删除民主评议
     * @param ids
     */
    void deleteBatch(String ids);
}
