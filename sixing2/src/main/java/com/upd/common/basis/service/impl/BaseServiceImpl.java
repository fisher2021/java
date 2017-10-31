/**
 * 
 * @author: 张守兵
 *    
 * @date: 2015-05-14
 * 
 * @version: V1.0   
 */

package com.upd.common.basis.service.impl;

import com.upd.common.basis.base.QueryForm;
import com.upd.common.basis.dao.BaseDao;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.queryParameter.QueryParam;
import com.upd.common.util.queryParameter.SearchCondition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


/**
 * @pdOid d0256d94-2404-4c68-8bc8-f57eedd420b0
 * @ClassName: AbstractBaseService
 * 
 * @Description: 基础服务接口实现抽象类
 * 
 * @author: 张守兵
 * 
 * @date: 2015/12/9.
 */
@Service("baseService")
@Transactional
public class BaseServiceImpl<T, ID extends Serializable> implements
		BaseService<T, ID> {
	/**
	 * log4j日志
	 * 
	 * @pdOid 39c526ea-7ef6-41a1-872a-0b33be15b977
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 数据访问接口
	 * 
	 * @pdOid 77b416c2-98b9-4ec6-a50b-53861b8f3e6e
	 */
	@Autowired(required = false)
	@Qualifier("baseDao")
	public BaseDao<T, ID> baseDao;

	@Override
	public void save(T entity) {
      this.baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
	this.baseDao.update(entity);
	}

	@Override
	public T saveOrUpdate(T entity) {
	return this.baseDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
     this.baseDao.delete(entity);
	}

	@Override
	public void delete(ID id) {
		this.baseDao.delete(id);
	}

	@Override
	public void deletemore(String ids) {
		this.baseDao.deletemore(ids);
	}

	@Override
	public T get(ID id) {
		return this.baseDao.get(id);
	}

	@Override
	public T findOne(String hql,Object... values) {
		List<T> ls=this.baseDao.find(hql,values);
		return null!=ls&&ls.size()>0?ls.get(0):null;
	}

	@Override
	public List<T> find(String hql, Object... values) {
		return this.baseDao.find(hql,values);
	}

	@Override
	public List<T> find(QueryParam... params) {
		return this.baseDao.find(params);
	}

	@Override
	public List<Object> find(SearchCondition searchCondition) {
		return this.baseDao.find(searchCondition);
	}

	@Override
	public int findTotalCount(QueryParam... params) {
		return this.baseDao.findTotalCount(params);
	}

	@Override
	public int findTotalCount(String hql, Object... values) {
		return this.baseDao.findTotalCount(hql,values);
	}

	@Override
	public Pagination findbypage(Pagination page, String hql, Object... values) {
		return this.baseDao.findbypage(page,hql,values);
	}

	@Override
	public Pagination findbypage(String countHql,Pagination page, String hql, Object... values) {
		return this.baseDao.findbypage(countHql,page,hql,values);
	}

	@Override
	public Pagination findbypage(Pagination page, QueryParam... params) {
		return this.baseDao.findbypage(page,params);
	}

	@Override
	public Pagination findbypage(Pagination page, SearchCondition searchCondition) {
		return this.baseDao.findbypage(page,searchCondition);
	}

	@Override
	public List<T> getTopRows(int rows, QueryParam... params) {
		return this.baseDao.getTopRows(rows, params);
	}

	@Override
	public List<Object> getTopRows(String hql, int rows, Object... values) {
		return this.baseDao.getTopRows(hql,rows,values);
	}

	@Override
	public List<T> getTopRows(int rows, SearchCondition searchCondition) {
		return this.baseDao.getTopRows(rows, searchCondition);
	}

	@Override
	public boolean evicObject(Object obj) {
		return this.baseDao.evicObject(obj);
	}

	@Override
	public T merge(T entity) {
		return this.baseDao.merge(entity);
	}

	public List<T> findAll(){
		return baseDao.find(baseDao.gethql());
	}

	@Override
	public Pagination page(Pagination page,QueryForm form) {
		if(null!=form){
			return findbypage(page,baseDao.gethql()+" WHERE"+form.toQueryDescription(),form.values());
		}
		return findbypage(page,baseDao.gethql());
	}

	public Pagination page(Pagination page,String select,QueryForm form) {
		return findbypage(page,select+" FROM "+baseDao.getEntityClass().getName()+" WHERE"+form.toQueryDescription(),form.values());
	}

    public Object findObject(final String hql, final Object... values) {
        return baseDao.findObject(hql,values);
    }


}