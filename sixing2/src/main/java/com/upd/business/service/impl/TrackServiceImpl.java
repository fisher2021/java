package com.upd.business.service.impl;

import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.constant.TrackType;
import com.upd.business.dao.TrackDao;
import com.upd.business.entity.*;
import com.upd.business.service.*;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("trackService")
public class TrackServiceImpl extends BaseServiceImpl<Track, Integer> implements TrackService {
    @Autowired
    private TrackDao trackDao;
    @Autowired
    private UserService userService;
    @Autowired
    ExamService examService;
    @Autowired
    AppraisementService appraisementService;
    @Autowired
    VoteService voteService;
    @Autowired
    OperatorService operatorService;

    @Autowired
    private void setBaseDao(TrackDao dao) {
        this.baseDao = dao;
    }


    @Override
    public Pagination page(Pagination page,Integer userId) {
        Pagination pageRsult = trackDao.findbypage(page,"from Track where user.id = ? order by createTime desc",userId);
        return pageRsult;
    }

    @Override
    public void add(String subject, TrackType type,Integer userId, Operator operator) {
        Track track = new Track();
        track.setSubject(subject);
        track.setType(type);
        User user = userService.get(userId);
        track.setUser(user);
        track.setOperator(operator);
        track.initTime();
        trackDao.save(track);
    }

    @Override
    public void edit(Track track) {
        track.initTime();
        trackDao.update(track);
    }

    @Override
    public void tracks(String token, String path, List<Object> args) {
        String subject = "";
        TrackType type = null;
        if(path.indexOf("exam/scoreSave")>0){
            Score score= (Score) args.get(1);//该方法参数的索引
            subject = examService.get(score.getExam().getId()).getTitle();
            type = TrackType.ZXKS;
        }
        if(path.indexOf("job/add")>0){
            JobBack jobBack = (JobBack) args.get(1);
            subject = jobBack.getSubject();
            type = TrackType.GZFK;
        }
        if(path.indexOf("report/add")>0){
            Report report = (Report) args.get(1);
            subject = report.getSubject();
            type = TrackType.SXHB;
        }
        if(path.indexOf("appr/commit")>0){
            Appraisement appraisement = appraisementService.get(Integer.parseInt(args.get(3).toString()));
            subject = appraisement.getTitle();
            type = TrackType.MZPY;
        }
        if(path.indexOf("vote/commit")>0){
            Vote vote = voteService.get(Integer.parseInt(args.get(3).toString()));
            subject = vote.getTitle();
            type = TrackType.ZXTP;
        }
        if (type != null){
            int id = TokenUtils.get(token,"id").asInt();
            //设置当前用户所在组织的管理员
            String unitCode = TokenUtils.get(token,"unitCode").asString();
            Operator operator = operatorService.getOperator(unitCode);
            add(subject,type,id,operator);
        }
    }
}
