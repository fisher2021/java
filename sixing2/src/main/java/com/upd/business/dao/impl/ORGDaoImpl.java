package com.upd.business.dao.impl;

import com.upd.business.dao.ORGDao;
import com.upd.business.entity.ORG;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
@Repository("orgDao")
public class ORGDaoImpl extends BaseDaoImpl<ORG,Integer> implements ORGDao {
    @Override
    public List<ORG> getChildren(ORG org) {
        if(org == null){
            String hql = "FROM ORG WHERE parent = null ORDER BY level";
            return (List<ORG>) this.find(hql);
        }else{
            String hql = "FROM ORG WHERE parent = :parent ORDER BY level";
            return (List<ORG>) this.getHibernateTemplate().findByNamedParam(hql,"parent", org);
        }
    }

    @Override
    public String getLastCode(int id) {
            String hql = "SELECT MAX(o.code) FROM ORG o WHERE parent.id = ";
        if (id == 0){
            hql += "null";
        }else{
            hql += id;
        }
        return (String)this.findUnique(hql);
    }
}
