package com.upd.business.service.impl;

import com.upd.auth.entity.Operator;
import com.upd.business.dao.IntegralSettingDao;
import com.upd.business.dao.impl.IntegralSettingDaoImpl;
import com.upd.business.entity.IntegralSetting;
import com.upd.business.service.IntegralSettingService;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.service.BaseService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xiao.tao on 2017/10/26.
 */
@Service("integralSettingService")
public class IntegralSettingServiceImpl extends BaseServiceImpl<IntegralSetting,Integer> implements IntegralSettingService {
    @Autowired
    private IntegralSettingDao integralSettingDao;
    @Autowired
    public void setBaseDao(IntegralSettingDaoImpl dao) {
        this.baseDao = dao;
    }

    /**
     * 保存
     * @param type
     * @param operator
     * @param parent
     */
    @Override
    public IntegralSetting save(Dict type, Operator operator, IntegralSetting parent) {
        IntegralSetting integralSetting = new IntegralSetting();
        integralSetting.setType(type);
        integralSetting.setValue("0");
        integralSetting.setOperator(operator);
        integralSetting.setParent(parent);
        integralSetting.initTime();
        integralSettingDao.save(integralSetting);
        return integralSetting;
    }

    /**
     * 获取单位下积分配置
     * @return
     */
    @Override
    public List<IntegralSetting> getList(String code) {

        return integralSettingDao.find("from IntegralSetting where operator.org.code = ? order by type.sort",code);
    }

    /**
     * 获取积分
     * @param code
     * @param type
     * @return
     */
    @Override
    public IntegralSetting getByCodeAndType(String code, String type) {
        return integralSettingDao.findOne("from IntegralSetting where operator.org.code = ? and type.dictId = ?",code, type);
    }
}
