package com.upd.business.service.impl;

import com.upd.business.dao.ReportDao;
import com.upd.business.entity.Report;
import com.upd.business.form.ReportForm;
import com.upd.business.service.ReportService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("reportService")
public class ReportServiceImpl extends BaseServiceImpl<Report, Integer> implements ReportService {
    @Autowired
    public ReportDao reportDao;
    @Autowired
    public void setBaseDao(ReportDao dao) {
        this.baseDao = dao;
    }
    @Override
    public Pagination page(Pagination page, ReportForm form) {
        Pagination pageRsult = reportDao.findbypage(page,"from Report where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            reportDao.delete(reportDao.get(Integer.parseInt(id)));
        }
    }
}
