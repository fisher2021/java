package com.upd.business.service;

import com.upd.business.entity.Appraisement;
import com.upd.business.entity.Choice;
import com.upd.business.form.AppraisementForm;
import com.upd.business.form.ChoiceForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * 选项类业务层
 * Created by ljw on 2017/5/4.
 */
public interface ChoiceService extends BaseService<Choice,Integer> {
    /**
     * 分页查询选项
     * @param form 民主评议ID
     * @return
     */
    public List<Choice> search(ChoiceForm form);
    /**
     * 新增民主评议选项
     * @param appraisement
     * @param option
     */
    public void add(Appraisement appraisement, String[] option);
    /**
     * 修改民主评议选项
     * @param choice
     * @return
     */
    public Choice edit(Choice choice);

    /**
     * 批量删除民主评议选项
     * @param ids
     */
    public void deleteBatch(String ids);
}
