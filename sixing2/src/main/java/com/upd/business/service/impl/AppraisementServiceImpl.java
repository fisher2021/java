package com.upd.business.service.impl;

import com.upd.business.dao.AppraisementDao;
import com.upd.business.entity.Appraisement;
import com.upd.business.entity.ORG;
import com.upd.business.entity.User;
import com.upd.business.form.AppraisementForm;
import com.upd.business.service.AppraisementService;
import com.upd.business.service.UserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("appraisementService")
public class AppraisementServiceImpl extends BaseServiceImpl<Appraisement, Integer> implements AppraisementService{
    @Autowired
    public AppraisementDao appraisementDao;
    @Autowired
    UserService userService;
    @Autowired
    public void setBaseDao(AppraisementDao dao) {
        this.baseDao = dao;
    }

    @Override
    public List<Appraisement> search( Integer id) {
        List<Appraisement> appraisements = appraisementDao.find("from Appraisement where id = ?",id);
        return appraisements;
    }
    @Override
    public Pagination page(Pagination page, AppraisementForm form) {
        Pagination pageRsult = findbypage("select count(*) from Appraisement where"+form.toQueryDescription(),page,"from Appraisement where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public List<Appraisement> searchByOrg(Integer userId) {
        User user = userService.get(userId);
        List<ORG> orgs = user.getOrgs();
        List<Appraisement> result = new ArrayList<>();
        for (ORG org : orgs){
            //查询该类型的本支部的，或者上级组织添加的
            List<Appraisement> appraisements = new ArrayList<>();
            appraisements = appraisementDao.find("from Appraisement where (orgId = "+org.getId()+" OR id in (SELECT id FROM Appraisement where FIND_IN_SET("+org.getId()+",orgList)>0)) and id not in (SELECT id FROM Appraisement where FIND_IN_SET("+userId+",voter)>0) order by createTime desc");
            if (appraisements != null && !appraisements.isEmpty()){
                for (Appraisement appraisement:appraisements){
                    if (result.contains(appraisement)){//去重
                        break;
                    }
                    result.add(appraisement);
                }
            }
        }
        return result;
    }

    @Override
    public Appraisement add(Appraisement appraisement) {
        appraisement.initTime();
        appraisementDao.save(appraisement);
        return null;
    }

    @Override
    public Appraisement edit(Appraisement appraisement) {
        appraisement.initTime();
        appraisementDao.update(appraisement);
        return appraisement;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            appraisementDao.delete(appraisementDao.get(Integer.parseInt(id)));
        }
    }
}
