package com.upd.business.service;

import com.upd.business.entity.Options;
import com.upd.business.form.OptionForm;
import com.upd.common.basis.service.BaseService;

import java.util.List;

/**
 * 选项类业务层
 * Created by ljw on 2017/5/4.
 */
public interface OptionsService extends BaseService<Options,Integer> {
    /**
     * 分页查询选项
     * @param form 在线投票ID
     * @return
     */
    public List<Options> search(OptionForm form);
    /**
     * 新增选项
     * @param options
     * @return
     */
    public Options add(Options options,String[] option);
    /**
     * 修改选项
     * @param options
     * @return
     */
    public Options edit(Options options);
}
