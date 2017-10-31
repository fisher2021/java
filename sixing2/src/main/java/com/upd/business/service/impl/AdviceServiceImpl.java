package com.upd.business.service.impl;

import com.upd.business.dao.AdviceDao;
import com.upd.business.entity.Advice;
import com.upd.business.form.AdviceForm;
import com.upd.business.service.AdviceService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("adviceService")
public class AdviceServiceImpl extends BaseServiceImpl<Advice, Integer> implements AdviceService{
    @Autowired
    public AdviceDao adviceDao;
    @Autowired
    public void setBaseDao(AdviceDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination page(Pagination page, AdviceForm form) {
        Pagination pageRsult = adviceDao.findbypage(page,"from Advice where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            adviceDao.delete(adviceDao.get(Integer.parseInt(id)));
        }
    }
}
