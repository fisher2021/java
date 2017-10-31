package com.upd.business.service.impl;

import com.upd.business.dao.DictDao;
import com.upd.business.dao.DictTypeDao;
import com.upd.business.dao.impl.DictDaoImpl;
import com.upd.business.service.DictTypeService;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.basis.vo.JsonReturn;
import com.upd.common.util.queryParameter.QueryParam;
import com.upd.common.util.queryParameter.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangshao on 2015/12/9.
 */
@Service("dictTypeService")
public class DictTypeServiceImpl extends BaseServiceImpl<DictType, Integer> implements DictTypeService {
    @Autowired
    private DictDao dictDao;
    @Autowired
    private DictTypeDao dictTypeDao;
    @Autowired
    public void setBaseDao(DictDaoImpl dictDao) {
        this.baseDao = dictTypeDao;
    }

    @Override
    public List<DictType> getDictTypeList() {
        String hql = "from DictType" ;
        List<DictType> types=this.dictTypeDao.find(hql);
        return types;
    }

    @Override
    public DictType getDictTypeByDictTypeId(String dictid) {
        SearchCondition searchCondition=new SearchCondition();
        QueryParam[] queryParam=new QueryParam[]{new QueryParam("dictTypeId",dictid,"=")};
        searchCondition.setParams(queryParam);
        List<Object> result = this.dictTypeDao.find(searchCondition);
        if (result!=null && result.size() > 0){
            return (DictType)result.get(0);
        }
        return null;
    }

    @Override
    public DictType getDictTypeByDictTypeName(String dictName) {
        return dictTypeDao.findOne(baseDao.gethql()+"WHERE dictTypeName=?",dictName);
    }

    public JsonReturn addDictType(DictType dictType){
        JsonReturn jsonReturn = new JsonReturn();
        String hql = "from DictType d where d.dictTypeId='"+dictType.getDictTypeId()+"'";
        List<DictType> list = dictTypeDao.find(hql);
        if(dictType.getId()==0){
            if (list!=null && list.size()>0){
                jsonReturn.setFlag(false);
                jsonReturn.setMessage("字典标识符已存在,请重新输入！");
            }else {
                dictType.initTime();
                dictTypeDao.saveOrUpdate(dictType);
                jsonReturn.setFlag(true);
                jsonReturn.setMessage("提交成功!");
            }
        }else{
            DictType oldDictType = dictTypeDao.get(dictType.getId());
            if(oldDictType.getDictTypeId().equals(dictType.getDictTypeId())){
                oldDictType.initTime();
                oldDictType.setDictTypeId(dictType.getDictTypeId());
                oldDictType.setDictTypeName(dictType.getDictTypeName());
                dictTypeDao.saveOrUpdate(oldDictType);
                jsonReturn.setFlag(true);
                jsonReturn.setMessage("提交成功!");
            }else{
                if (list!=null && list.size()>0){
                    jsonReturn.setFlag(false);
                    jsonReturn.setMessage("字典标识符已存在,请重新输入！");
                }else {
                    oldDictType.initTime();
                    oldDictType.setDictTypeId(dictType.getDictTypeId());
                    oldDictType.setDictTypeName(dictType.getDictTypeName());
                    dictTypeDao.saveOrUpdate(oldDictType);
                    jsonReturn.setFlag(true);
                    jsonReturn.setMessage("提交成功!");
                }
            }
        }
        return jsonReturn;
    }

    @Override
    public void updateORGVersion() {
        DictType dictType = getDictTypeByDictTypeId("orgversion");
        long time = new Date().getTime();
        dictType.setValue(String.valueOf(time));
        dictTypeDao.update(dictType);
    }
}
