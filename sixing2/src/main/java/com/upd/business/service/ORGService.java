package com.upd.business.service;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.ORG;
import com.upd.business.form.ArticleForm;
import com.upd.business.form.ORGForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface ORGService extends BaseService<ORG, Integer> {
    void delete(String ids);
    /**
     * 分页查询组织
     * @param page
     * @param form
     * @return
     */
    Pagination pageForMessage(Pagination page, ORGForm form);
    /**
     * 查询党支部的父级
     * @return
     */
    ORG findParent();
    /**
     * 分页查询组织
     * @param page 分页
     * @param form 词典类型
     * @return
     */
    Pagination page(Pagination page, ORGForm form);
    /**
     * 新增组织
     * @param org
     */
    void add(ORG org);
    /**
     * 修改组织
     * @param org
     */
    void edit(ORG org);
    /**
     * 查询全部组织级别
     * @return
     */
    ORG getAll(ORGType orgType,String unitCode);
    /**
     * 将ID数组转为字符串
     * @param orgs
     * @return
     */
    String getOrgList(Integer[] orgs);
    /**
     * 将字符串转为组织名称集合
     * @param orgList
     * @return
     */
    List<String> getOrgListName(String orgList);
    /**
     * 查询子组织
     * @param org
     * @return
     */
    List<ORG> getChildren(ORG org);

    /**
     * 获取父级组织下子级组织最大编号
     * @param id
     * @return
     */
    String getLastCode(int id);

    /**
     * 获取登录账号（非系统管理员）组织及其子组织（非最末级）列表
     * @param ope
     * @return
     */
  //  List<ORG> getOpeOrgs(Operator ope);

    /**
     * 根据code获取全部下级组织
     * @param code
     * @return
     */
    List<ORG> getChildrenByCode(String code);
}
