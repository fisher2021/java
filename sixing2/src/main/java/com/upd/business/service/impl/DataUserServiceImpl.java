package com.upd.business.service.impl;

import com.upd.business.constant.BacklogType;
import com.upd.business.dao.BacklogUserDao;
import com.upd.business.dao.DataUserDao;
import com.upd.business.entity.*;
import com.upd.business.service.BacklogService;
import com.upd.business.service.BacklogUserService;
import com.upd.business.service.DataUserService;
import com.upd.business.service.UserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/23.
 */
@Service("dataUserService")
public class DataUserServiceImpl extends BaseServiceImpl<DataUser, Integer> implements DataUserService {
    @Autowired
    public DataUserDao dataUserDao;
    @Autowired
    UserService userService;
    @Autowired
    public void setBaseDao(DataUserDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination pageByUser(Pagination page, Integer userId) {
        Pagination pageRsult = dataUserDao.findbypage(page,"select new map (data.title as title,data.url as url) from DataUser where user.id = ? order by data.createTime desc",userId);
        return pageRsult;
        }

    @Override
    public void deleteUser(Integer dataId, Integer id) {
        DataUser dataUser = dataUserDao.findOne("from DataUser where data.id = ? and user.id  = ?",dataId,id);
        if (dataUser != null){
            dataUserDao.delete(dataUser);
        }
    }

}
