package com.upd.common.basis.service;

import com.upd.common.basis.base.QueryForm;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.queryParameter.QueryParam;
import com.upd.common.util.queryParameter.SearchCondition;

import java.io.Serializable;
import java.util.List;

/**
 * @pdOid 28284d08-6455-4ff4-b083-0a5a574a4268
 * @ClassName: BaseDao
 * 
 * @Description: DAO操作基类接口,实现了通用的数据操作
 * 
 * @author: 张守兵
 * 
 * @date: 2015/12/9.
 */
public interface BaseService<T, ID extends Serializable> {

//    abstract void baseDao();
    /**
     * 保存指定实体类
     *
     *
     * @param entity
     * @pdOid f4457d1c-2707-4bd5-8f33-4fcf6724c997
     */
    void save(T entity);
    /**
     * 修改指定实体类
     *
     *
     * @param entity
     * @pdOid f4457d1c-2707-4bd5-8f33-4fcf6724c997
     */
    void update(T entity);
    //保存或修改
    public T saveOrUpdate(T entity) ;
    /**
     * 删除指定实体
     *
     *
     * @param entity 删除对象
     * @pdOid 8a5aed43-ffeb-40c4-9599-1869dd459560
     */
    void  delete(T entity);
    /**
     * 删除指定实体
     *
     *
     * @param id
     * @pdOid 8a5aed43-ffeb-40c4-9599-1869dd459560
     */
    void delete(ID id);

    /**
     * 删除指定实体集合
     *
     *
     * @param ids 主键集合 以逗号隔开
     * @pdOid 8a5aed43-ffeb-40c4-9599-1869dd459560
     */
    void deletemore(String ids);

    /**
     * 查找指定实体
     *
     *
     * @param id
     * @pdOid 8a5aed43-ffeb-40c4-9599-1869dd459560
     */
    T get(ID id);

    /**
     * 查询返回一个实体
     * @param hql
     * @param values
     * @return
     */
    T findOne(String hql,Object... values);

    /**
     *
     * @param hql 自定义hql(复杂hql)
     * @param values
     * @return
     */
    List<T> find(String hql, Object... values);
    /**
     *
     * @param params 查询参数
     * @return
     */
    List<T> find(QueryParam... params);
    /**
     *
     * @param searchCondition 查询参数
     * @return
     */
    List<Object> find(SearchCondition searchCondition);

    /**
     * 返回带查询参数的实体总记录数
     * @param params 查询参数
     * @return
     */
    int findTotalCount(QueryParam... params);
    /**
     * 返回自定义查询语句总记录数
     * @param hql 自定义 hql
     * @param values 可变参数的值
     * @return
     */
    int findTotalCount(String hql, Object... values);
    /**
     *通过自定义hql查询分页数据
     * @param page  分页对象
     * @param hql   自定义查询语句
     * @param values 可变参数的值
     * @return
     */
    public Pagination findbypage(Pagination page, String hql, Object... values);

    /**
     *
     * @param page 分页对象
     * @param countHql  总数查询hql
     * @param hql 查询hql
     * @param values 参数
     * @return
     */
    Pagination findbypage(String countHql,Pagination page, String hql, Object... values);

    /**
     * 通过参数查询分页数据
     * @param page  分页对象
     * @param params  查询参数
     * @return 泛型对象集合
     */
    public Pagination findbypage(Pagination page, QueryParam... params);
    /**
     * 通过参数查询分页数据 高级查询 支持自定义属性，排序
     * @param page  分页对象
     * @param searchCondition  查询条件
     * @return 泛型对象集合
     */
    public Pagination findbypage(Pagination page, SearchCondition searchCondition);


    /**
     *通过参数查询前几条数据
     * @param params 查询参数
     * @param rows  取前几条数据
     * @return 泛型对象
     * @throws org.hibernate.HibernateException
     */
    public List<T> getTopRows(int rows, QueryParam... params)
    ;
    /**
     *
     * @param hql 自定义hql
     * @param rows  取前几条数据
     * @param values 可变参数的值
     * @return 任意类型
     * @throws org.hibernate.HibernateException
     */
    public List<Object> getTopRows(String hql, int rows, Object... values);

    /**
     *
     * @param searchCondition 高级查询
     * @param rows  取前几条数据
     * @return 任意类型
     * @throws org.hibernate.HibernateException
     */
    public List<T> getTopRows(int rows, SearchCondition searchCondition);
    /**
     * 从session中逐出对象
     * @param obj 操作对象
     */

    boolean evicObject(Object obj);

    /**
     *  merger保存实体对象
     * @param entity 保存的实体对象
     * @return
     */
    T merge(T entity);

    /**
     * 查全部
     * @return
     */
    List<T> findAll();

    /**
     * 分页查询
     * @param page
     * @param form
     * @return
     */
    Pagination page(Pagination page,QueryForm form);

    /**
     * 分页查询自定义查询字段
     * @param page
     * @param select
     * @param form
     * @return
     */
    Pagination page(Pagination page,String select,QueryForm form);

    Object findObject(final String hql, final Object... values);

}