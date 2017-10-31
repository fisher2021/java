package com.upd.business.dao.impl;

import com.upd.business.dao.ArticleDao;
import com.upd.business.entity.Article;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/2.
 */
@Repository("articleDao")
public class ArticleDaoImpl extends BaseDaoImpl<Article,Integer> implements ArticleDao {
}
