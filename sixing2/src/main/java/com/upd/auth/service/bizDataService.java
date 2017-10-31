package com.upd.auth.service;


import com.upd.common.basis.service.BaseService;

public interface bizDataService extends BaseService<Object, String> {
	/**
	 * 修改保存方法 满足业务需求 将从数据库查询处理的对象从新赋值 entity ：当前实体 persistencebject：入库对象
	 * strutsObject struts从前台封装的对象
	 * 
	 */
	public Object getPersistenceObj(Class claszz, Object persistencebject,
                                    Object strutsObject) throws Exception;
}
