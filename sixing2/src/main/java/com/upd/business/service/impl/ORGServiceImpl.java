package com.upd.business.service.impl;

import com.upd.business.constant.ORGType;
import com.upd.business.dao.ORGDao;
import com.upd.business.entity.Message;
import com.upd.business.entity.ORG;
import com.upd.business.form.ORGForm;
import com.upd.business.service.DictTypeService;
import com.upd.business.service.MessageService;
import com.upd.business.service.ORGService;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.DateUtil;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
@Service("orgService")
public class ORGServiceImpl extends BaseServiceImpl<ORG,Integer> implements ORGService {

    @Autowired
    DictTypeService dictTypeService;
    @Autowired
    MessageService messageService;

    @Autowired
    ORGDao orgDao;
    @Autowired
    public void setBaseDao(ORGDao dao) {
        this.baseDao = dao;
    }

    @Override
    public void delete(String ids) {
        for(String id:ids.split(",")){
            baseDao.delete(Integer.parseInt(id));
        }
    }

    @Override
    public void add(ORG org) {
        //org.setLevel(ORGType.valueOf(2));//党支部
        org.initTime();
        baseDao.save(org);
        //修改组织版本
        DictType dictType = dictTypeService.getDictTypeByDictTypeId("orgversion");
        dictType.setValue(String.valueOf(new Date().getTime()));
        dictTypeService.update(dictType);
    }

    @Override
    public void edit(ORG org) {
        ORG org1 = baseDao.get(org.getId());
        if (org.getName() != null && !org.getName().equals("")){
            org1.setName(org.getName());
        }
        if (org.getChangeDate() != null && !org.getChangeDate().equals("")){
            org1.setChangeDate(org.getChangeDate());
        }
        org1.initTime();
        baseDao.update(org1);
        //修改组织版本
        DictType dictType = dictTypeService.getDictTypeByDictTypeId("orgversion");
        dictType.setValue(String.valueOf(new Date().getTime()));
        dictTypeService.update(dictType);
    }

    @Override
    public ORG getAll(ORGType orgType,String unitCode) {
        return baseDao.findOne("from ORG where level = ? and operator.org.code = ?",orgType,unitCode);
    }

    @Override
    public String getOrgList(Integer[] orgs) {
        StringBuffer sb = new StringBuffer();
        for (Integer orgId:orgs){
            sb.append(orgId).append(",");
        }
        String orgList = sb.substring(0,sb.length()-1).toString();
        return orgList;
    }

    @Override
    public List<String> getOrgListName(String orgList) {
        String[] ids = orgList.split(",");
        List<String> names = new ArrayList<>();
        for (String id:ids){
            String name = baseDao.get(Integer.parseInt(id)).getName();
            names.add(name);
        }
        return names;
    }

    @Override
       public List<ORG> getChildren(ORG org) {
        org = baseDao.get(org.getId());
        return orgDao.getChildren(org);
    }

    @Override
    public String getLastCode(int id) {
        return orgDao.getLastCode(id);
    }

    @Override
    public List<ORG> getChildrenByCode(String code) {
        return orgDao.find("from ORG where code like '"+code+"%'");
    }

    @Override
    public Pagination pageForMessage(Pagination page, ORGForm form) {
        Message message = messageService.get(form.getMessageId());
        String orgs = message.getOrg();
        if (orgs != null && !orgs.equals("")){
            form.setOrgIds(orgs);//设置已添加的组织ID
        }else {
            form.setOrgIds("0");
        }
        if (form.getOrgHave() == null){
            form.setOrgHave(false);//默认查询未添加组织
        }
        Pagination pageRsult = baseDao.findbypage("select count(*) from ORG where"+form.toQueryDescription(),page,"from ORG where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public ORG findParent() {
        ORG org = baseDao.findOne("from ORG where level = 1");
        return org;
    }

    @Override
    public Pagination page(Pagination page, ORGForm form) {
        Pagination pageRsult = baseDao.findbypage(page,"from ORG where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }



}
