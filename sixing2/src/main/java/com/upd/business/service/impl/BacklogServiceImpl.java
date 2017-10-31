package com.upd.business.service.impl;

import com.upd.business.constant.BacklogType;
import com.upd.business.constant.TrackType;
import com.upd.business.dao.BacklogDao;
import com.upd.business.dao.CalendarDao;
import com.upd.business.entity.*;
import com.upd.business.form.BacklogForm;
import com.upd.business.form.CalendarForm;
import com.upd.business.service.*;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.easemob.Easemob;
import com.upd.common.util.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("backlogService")
public class BacklogServiceImpl extends BaseServiceImpl<Backlog, Integer> implements BacklogService {
    @Autowired
    private BacklogDao backlogDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ExamService examService;
    @Autowired
    private AppraisementService appraisementService;
    @Autowired
    private VoteService voteService;

    @Autowired
    private void setBaseDao(BacklogDao dao) {
        this.baseDao = dao;
    }

    @Override
    public List<Backlog> search(String month, Integer userId) {
        List<Backlog> backlogs = backlogDao.find("from Backlog where date_format(doDate,'%Y-%m') = ? and user.id = ?",month,userId);
        return backlogs;
    }

    @Override
    public List<Backlog> searchMission(Integer missionId) {
        List<Backlog> backlogs = backlogDao.find("from Backlog where missionId = ?",missionId);
        return backlogs;
    }

    @Override
    public Pagination page(Pagination page, BacklogForm form) {
        Pagination pageRsult = backlogDao.findbypage(page,"from Backlog where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public Backlog add(Backlog backlog) {
        backlog.initTime();
        backlogDao.save(backlog);
        return backlog;
    }

    @Override
    public Backlog edit(Backlog backlog) {
        backlog.initTime();
        backlogDao.update(backlog);
        return backlog;
    }

    @Override
    public boolean existMissionId(BacklogType type, int missionId) {
        if (type.name() == "XWCK"){
            if (articleService.get(missionId) != null){
                return true;
            }
        }else if (type.name() == "ZXKS"){
            if (examService.get(missionId) != null){
                return true;
            }
        }else if (type.name() == "MZPY"){
            if (appraisementService.get(missionId) != null){
                return true;
            }
        }else if (type.name() == "ZXTP"){
            if (voteService.get(missionId) != null){
                return true;
            }
        }
        return false;
    }


}
