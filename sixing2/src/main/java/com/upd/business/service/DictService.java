package com.upd.business.service;



import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.service.BaseService;
import com.upd.common.basis.vo.JsonReturn;

import java.util.List;

/**
 * Created by ljw on 2017/5/3.
 */
public interface DictService extends BaseService<Dict,Integer> {
    public List<Dict> getDictListByTypeId(String dictTypeId);
    public List<Dict> getSubDictList(String parentDictId, String dictTypeId);
    public Dict getDictByDictId(String dictid);
    public Dict getDictByDictName(String dictName);
    public List<Dict> getDictList(Integer id);
    public List<Dict> getSubDictList(Integer id);
    public JsonReturn addDict(Dict dict, Integer parentId, Integer sort, Integer dictTypeId);
    public List<Dict> getDictListByParentId(Integer parentId);
}
