package com.upd.business.service;

import com.upd.auth.entity.Operator;
import com.upd.business.entity.IntegralSetting;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.service.BaseService;

import java.util.List;

/**
 * Created by xiao.tao on 2017/10/26.
 */
public interface IntegralSettingService extends BaseService<IntegralSetting,Integer> {
    /**
     * 保存
     * @param type
     * @param operator
     */
    public IntegralSetting save(Dict type,Operator operator, IntegralSetting parent);

    /**
     * 获取单位下积分配置
     * @param code
     * @return
     */
    public List<IntegralSetting> getList(String code);

    /**
     * 获取积分
     * @param code
     * @param type
     * @return
     */
    public IntegralSetting getByCodeAndType(String code, String type);
}
