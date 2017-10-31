package com.upd.business.service.impl;

import com.upd.business.dao.DictDao;
import com.upd.business.dao.DictTypeDao;
import com.upd.business.dao.impl.DictDaoImpl;
import com.upd.business.service.DictService;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.basis.vo.JsonReturn;
import com.upd.common.util.queryParameter.Order;
import com.upd.common.util.queryParameter.QueryParam;
import com.upd.common.util.queryParameter.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshao on 2015/12/9.
 */
@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl<Dict, Integer> implements DictService {
    @Autowired
    private DictDao dictDao;
    @Autowired
    private DictTypeDao dictTypeDao;
    @Autowired
    public void setBaseDao(DictDaoImpl dictDao) {
        this.baseDao = dictDao;
    }

    @Override
    public List<Dict> getDictListByTypeId(String dictTypeId) {
        QueryParam queryParam=new QueryParam("dictTypeId",dictTypeId,"=");
        List<DictType> types=this.dictTypeDao.find(queryParam);
        if (types==null||types.size()==0)
        return  null;
        else{
            DictType type=types.get(0);
            QueryParam qp=new QueryParam("dictType",String.valueOf(type.getId()),"=");
            QueryParam qp1=new QueryParam("parent",null,"is");
            SearchCondition searchCondition=new SearchCondition();
            Order order=new Order("sort");
            searchCondition.setOrders(new Order[]{order});
            searchCondition.setParams(new QueryParam[]{qp,qp1});
            List<Object> dicts=  this.dictDao.find(searchCondition);
            List<Dict> dicts1=new ArrayList<Dict>();
            for(Object dict: dicts){
                dicts1.add((Dict)dict);
            }
            return  dicts1;
        }
    }

    /**
     * 查询子字典
     * @param parentId
     * @return
     */
    @Override
    public List<Dict> getDictListByParentId(Integer parentId) {
        List<Dict> dicts = this.dictDao.find("from Dict where parent.id = ?",parentId);
        return dicts;
    }
    public List<Dict> getSubDictList(String parentDictId,String dictTypeId ){
        //类型
        QueryParam queryParam=new QueryParam("dictTypeId",dictTypeId,"=");
        List<DictType> types=this.dictTypeDao.find(queryParam);
        if (types==null||types.size()==0)
            return  null;
        else{
            DictType type=types.get(0);
            QueryParam qp=new QueryParam("dictType",String.valueOf(type.getId()),"=");

            //父字典项
            QueryParam pqueryParam=null;
            if(parentDictId!=null&&!parentDictId.equals("")){
                pqueryParam= new QueryParam("parent.id",parentDictId,"=");
            }else{
                pqueryParam=new QueryParam("parent",null,"is");
            }
            return  this.dictDao.find(qp,pqueryParam);
        }
    }

    @Override
    public Dict getDictByDictId(String dictid) {
        SearchCondition searchCondition=new SearchCondition();
        QueryParam[] queryParam=new QueryParam[]{new QueryParam("dictId",dictid,"=")};
        searchCondition.setParams(queryParam);
        List<Object> result = this.dictDao.find(searchCondition);
        if (result !=null && result.size() > 0){
            return (Dict)result.get(0);
        }
        return null;
    }

    @Override
    public Dict getDictByDictName(String dictName) {
        SearchCondition searchCondition=new SearchCondition();
        QueryParam[] queryParam=new QueryParam[]{new QueryParam("dictName","%"+dictName+"%","like")};
        searchCondition.setParams(queryParam);
        List<Object> result = this.dictDao.find(searchCondition);
        if (result!=null && result.size() > 0){
            return (Dict)result.get(0);
        }
        return null;
    }

    @Override
    public List<Dict> getDictList(Integer id) {
        String hql = "from Dict d where d.dictType="+ id +"and d.parent=null";
        List<Dict> types=this.dictDao.find(hql);
        return types;
    }

    @Override
    public List<Dict> getSubDictList(Integer id) {
        String hql = "from Dict d where d.parent="+ id ;
        List<Dict> types=this.dictDao.find(hql);
        return types;
    }

    public JsonReturn addDict(Dict dict, Integer parentId, Integer sort,Integer dictTypeId){
        JsonReturn jsonReturn = new JsonReturn();
        String hql = "from Dict d where d.dictId='"+dict.getDictId()+"'";
        List<Dict> list = dictDao.find(hql);
        Dict parentDict =null;
        DictType dictType = dictTypeDao.get(dictTypeId);
        if(sort!=0){
            parentDict = dictDao.get(parentId);
        }
        if(dict.getId()==0){
            if (list.size()>0){
                jsonReturn.setFlag(false);
                jsonReturn.setMessage("字典标识符已存在,请重新输入！");
            }else {
                dict.initTime();
                dict.setParent(parentDict);
                dict.setSort(sort+1);
                if(parentDict==null){
                    dict.setSeqCn(dict.getDictName());
                }else{
                    dict.setSeqCn(parentDict.getSeqCn()+"-"+dict.getDictName());
                }
                dict.setDictType(dictType);
                dictDao.saveOrUpdate(dict);
                if (parentDict==null){
                    dict.setSeqNo("."+dict.getId()+".");
                }else{
                    dict.setSeqNo(parentDict.getSeqNo()+dict.getId()+".");
                }
                dictDao.saveOrUpdate(dict);
                jsonReturn.setFlag(true);
                jsonReturn.setMessage("提交成功!");
            }
        }else{
            Dict oldDict = dictDao.get(dict.getId());
            if(oldDict.getDictId().equals(oldDict.getDictId())){
                oldDict.initTime();
                oldDict.setDictId(dict.getDictId());
                oldDict.setDictName(dict.getDictName());
                oldDict.setRemark(dict.getRemark());
                if(oldDict.getParent()!=null){
                    oldDict.setSeqCn(oldDict.getParent().getSeqCn()+"-"+dict.getDictName());
                }else{
                    oldDict.setSeqCn(dict.getDictName());
                }
                dictDao.saveOrUpdate(oldDict);
                jsonReturn.setFlag(true);
                jsonReturn.setMessage("提交成功!");
            }else{
                if (list.size()>0){
                    jsonReturn.setFlag(false);
                    jsonReturn.setMessage("字典标识符已存在,请重新输入！");
                }else {
                    oldDict.setDictId(dict.getDictId());
                    oldDict.setDictName(dict.getDictName());
                    oldDict.setRemark(dict.getRemark());
                    if(oldDict.getParent()!=null){
                        oldDict.setSeqCn(oldDict.getParent().getSeqCn()+"-"+dict.getDictName());
                    }else{
                        oldDict.setSeqCn(dict.getDictName());
                    }
                    oldDict.initTime();
                    dictDao.saveOrUpdate(oldDict);
                    jsonReturn.setFlag(true);
                    jsonReturn.setMessage("提交成功!");
                }
            }
        }
        return jsonReturn;
    }


}
