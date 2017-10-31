package com.upd.business.dao;

import com.upd.business.entity.ORG;
import com.upd.common.basis.dao.BaseDao;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface ORGDao extends BaseDao<ORG,Integer> {
    /**
     * 查询子组织
     * @param org
     * @return
     */
    List<ORG> getChildren(ORG org);

    /**
     * 查询子组织最大编号
     * @param id 父组织ID，查询顶级组织编号id传入0
     * @return
     */
    String getLastCode(int id);
}
