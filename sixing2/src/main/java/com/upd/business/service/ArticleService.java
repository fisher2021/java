package com.upd.business.service;

import com.upd.business.entity.Article;
import com.upd.business.form.ArticleForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.basis.vo.JsonReturn;
import com.upd.common.util.page.Pagination;

import java.util.List;

/**
 * Created by ljw on 2017/5/2.
 */
public interface ArticleService extends BaseService<Article, Integer> {
    /**
     * 分页查询文章
     * @param page 分页
     * @param form 词典类型
     * @return
     */
    Pagination page(Pagination page, ArticleForm form);
    /**
     * 查询文章
     * @return
     */
    List<Article> articleList(ArticleForm form);
    /**
     * 按组织分页查询文章
     * @param page 分页
     * @param orgId
     * @return
     */
    Pagination pageByOrg(Pagination page, ArticleForm form,Integer orgId);
    /**
     * 新增文章
     * @param article
     * @return
     */
    void add(Article article);
    /**
     * 修改文章
     * @param article
     * @return
     */
    Article edit(Article article);

    /**
     * 批量删除文章
     * @param ids
     */
    void deleteBatch(String ids);
    /**
     * 排序
     */
    void rank(Integer id,Integer otherId);
}
