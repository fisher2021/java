package com.upd.business.service;

import com.upd.business.entity.AndroidIOSVesrion;
import com.upd.common.basis.service.BaseService;

import java.util.List;

/**
 * APP版本更新业务层
 * Created by ljw on 2017/5/4.
 */
public interface AndroidIOSVesrionService extends BaseService<AndroidIOSVesrion,Integer> {
    /**
     * 获取最新版本
     * @return
     */
    List<AndroidIOSVesrion> getLast(Integer type);

    /**
     * 新增
     * @param androidIOSVesrion
     */
    void add(AndroidIOSVesrion androidIOSVesrion);

    /**
     * 修改
     * @param androidIOSVesrion
     */
    void edit(AndroidIOSVesrion androidIOSVesrion);

}
