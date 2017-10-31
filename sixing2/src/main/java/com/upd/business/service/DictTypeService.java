package com.upd.business.service;


import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.service.BaseService;
import com.upd.common.basis.vo.JsonReturn;

import java.util.List;

/**
 * Created by ljw on 2017/5/3.
 */
public interface DictTypeService extends BaseService<DictType,Integer> {
    public List<DictType> getDictTypeList();
    public DictType getDictTypeByDictTypeId(String dictid);
    public DictType getDictTypeByDictTypeName(String dictName);
    public JsonReturn addDictType(DictType dictType);
    public void updateORGVersion();
}
