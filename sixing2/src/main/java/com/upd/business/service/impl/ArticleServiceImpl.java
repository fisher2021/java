package com.upd.business.service.impl;

import com.upd.business.dao.ArticleDao;
import com.upd.business.entity.Article;
import com.upd.business.form.ArticleForm;
import com.upd.business.service.ArticleService;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/2.
 */
@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<Article, Integer> implements ArticleService {
    @Autowired
    public ArticleDao articleDao;
    @Autowired
    public void setBaseDao(ArticleDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination page(Pagination page, ArticleForm form) {
        Pagination pageRsult = articleDao.findbypage(page,"from Article where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public List<Article> articleList(ArticleForm form) {
        return articleDao.find("from Article where"+form.toQueryDescription(),form.values());
    }

    @Override
    public Pagination pageByOrg(Pagination page, ArticleForm form, Integer orgId) {
        //查询该类型的同支部的，或者党组的添加的
        Pagination pageRsult = articleDao.findbypage(page,"from Article where (orgId = "+orgId+" or orgId = 0) and "+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public void add(Article article) {
        article.setCount(0);
        if (article.getCreateTime().equals("")){
            article.setCreateTime(null);
        }
        article.initTime();
        articleDao.save(article);
        article.setRank(article.getId());
        articleDao.update(article);
    }

    @Override
    public Article edit(Article article) {
        Article article1 = articleDao.get(article.getId());
        if (article.getTitle() != null && !article.getTitle().equals("")){
            article1.setTitle(article.getTitle());
        }
        if (article.getAuthor() != null && !article.getAuthor().equals("")){
            article1.setAuthor(article.getAuthor());
        }
        if (article.getTargetOut() != null && !article.getTargetOut().equals("")){
            article1.setTargetOut(article.getTargetOut());
        }
        if (article.getTargetUrl() != null && !article.getTargetUrl().equals("")){
            article1.setTargetUrl(article.getTargetUrl());
        }
        if (article.getContent() != null && !article.getContent().equals("")){
            article1.setContent(article.getContent());
        }
        if (article.getImgUrl() != null && !article.getImgUrl().equals("")){
            article1.setImgUrl(article.getImgUrl());
        }
        if (article.getCount() != null && !article.getCount().equals("")){
            article1.setCount(article.getCount());
        }
        if (article.getType() != null && !article.getType().equals("")){
            article1.setType(article.getType());
        }
        if (article.getCreateTime() != null && !article.getCreateTime().equals("")){
            article1.setCreateTime(article.getCreateTime());
        }
        article1.initTime();
        articleDao.update(article1);
        return article1;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            articleDao.delete(articleDao.get(Integer.parseInt(id)));
        }
    }

    @Override
    public void rank(Integer id, Integer otherId) {
        Article article = articleDao.get(id);
        Article article1 = articleDao.get(otherId);
        int rank = article.getRank();
        article.setRank(article1.getRank());
        article1.setRank(rank);
        articleDao.update(article);
        articleDao.update(article1);
    }

}
