package com.upd.business.service.impl;

import com.upd.business.dao.JobBackDao;
import com.upd.business.entity.JobBack;
import com.upd.business.form.JobForm;
import com.upd.business.service.JobBackService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("jobBackService")
public class JobBackServiceImpl extends BaseServiceImpl<JobBack, Integer> implements JobBackService {
    @Autowired
    public JobBackDao jobBackDao;
    @Autowired
    public void setBaseDao(JobBackDao dao) {
        this.baseDao = dao;
    }
    @Override
    public Pagination page(Pagination page, JobForm form) {
        Pagination pageRsult = jobBackDao.findbypage(page,"from JobBack where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }
    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            jobBackDao.delete(jobBackDao.get(Integer.parseInt(id)));
        }
    }

    @Override
    public void add(JobBack jobBack) {
        jobBack.initTime();
        jobBackDao.save(jobBack);
    }

    @Override
    public JobBack edit(JobBack jobBack) {
        JobBack jobBack1 = jobBackDao.get(jobBack.getId());
        if (jobBack.getImage1() != null && !jobBack.getImage1().equals("")){
            jobBack1.setImage1(jobBack.getImage1());
        }
        if (jobBack.getImage2() != null && !jobBack.getImage2().equals("")){
            jobBack1.setImage2(jobBack.getImage2());
        }
        if (jobBack.getDesc1() != null && !jobBack.getDesc1().equals("")){
            jobBack1.setDesc1(jobBack.getDesc1());
        }
        if (jobBack.getDesc2() != null && !jobBack.getDesc2().equals("")){
            jobBack1.setDesc2(jobBack.getDesc2());
        }
        if (jobBack.getActivityDate() != null && !jobBack.getActivityDate().equals("")){
            jobBack1.setActivityDate(jobBack.getActivityDate());
        }
        if (jobBack.getSubject() != null && !jobBack.getSubject().equals("")){
            jobBack1.setSubject(jobBack.getSubject());
        }
        if (jobBack.getContent() != null && !jobBack.getContent().equals("")){
            jobBack1.setContent(jobBack.getContent());
        }
        jobBack1.initTime();
        jobBackDao.update(jobBack1);
        return jobBack1;
    }
}
