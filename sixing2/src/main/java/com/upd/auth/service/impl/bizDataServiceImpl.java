package com.upd.auth.service.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.upd.auth.dao.bizDataDao;
import com.upd.auth.service.bizDataService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bizDataService")
public class bizDataServiceImpl  extends BaseServiceImpl<Object,String> implements bizDataService {
	protected static final Log logger = LogFactory.getLog(bizDataServiceImpl.class);

	// 当前操作时间
	protected Date nowdate = new Date();
	// 格式
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	public void setBaseDao(bizDataDao bizDataDao) {
		this.baseDao = bizDataDao;
	}
	/**
	 * 修改保存方法 满足业务需求 将从数据库查询处理的对象从新赋值 entity ：当前实体 persistencebject：入库对象
	 * strutsObject struts从前台封装的对象
	 *
	 */
	public Object getPersistenceObj(Class claszz, Object persistencebject,
			Object strutsObject) throws Exception {
		if (persistencebject == null) {
			persistencebject = strutsObject;
		}
		for (Field property : claszz.getDeclaredFields()) {
			try {
				Object propertyValue =  PropertyUtils.getProperty(
						strutsObject, property.getName());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("实体"+claszz.getName()+"的属性"+property.getName()+"不存在");
				continue;
			}
				Object propertyValue =  PropertyUtils.getProperty(
						strutsObject, property.getName());
				if (propertyValue != null) {
					PropertyUtils.setProperty(persistencebject, property
							.getName(), propertyValue);
				}
		}
		return persistencebject;
	}
}
