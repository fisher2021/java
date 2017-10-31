package com.upd.business.service.impl;

import com.upd.business.dao.AndroidIOSVesrionDao;
import com.upd.business.entity.AndroidIOSVesrion;
import com.upd.business.service.AndroidIOSVesrionService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("androidIOSVesrionService")
public class AndroidIOSVesrionServiceImpl extends BaseServiceImpl<AndroidIOSVesrion, Integer> implements AndroidIOSVesrionService {
    @Autowired
    public AndroidIOSVesrionDao androidIOSVesrionDao;
    @Autowired
    public void setBaseDao(AndroidIOSVesrionDao dao) {
        this.baseDao = dao;
    }


    @Override
    public List<AndroidIOSVesrion> getLast(Integer type) {
        return androidIOSVesrionDao.findbySQL("select * from AndroidIOSVesrion where type = ? order by createTime desc limit 1",type);
    }

    @Override
    public void add(AndroidIOSVesrion androidIOSVesrion) {
        androidIOSVesrion.initTime();
        androidIOSVesrionDao.save(androidIOSVesrion);
    }

    @Override
    public void edit(AndroidIOSVesrion androidIOSVesrion) {
        androidIOSVesrion.initTime();
        androidIOSVesrionDao.update(androidIOSVesrion);
    }
}
