package com.upd.business.service.impl;

import com.upd.business.constant.BacklogType;
import com.upd.business.constant.TrackType;
import com.upd.business.dao.ArticleDao;
import com.upd.business.dao.BacklogUserDao;
import com.upd.business.entity.*;
import com.upd.business.service.*;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.pay.alipay.config.AlipayConfig;
import com.upd.common.util.pay.wxpay.config.WxpayConfig;
import com.upd.common.util.pay.wxpay.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ljw on 2017/5/23.
 */
@Service("backlogUserService")
public class BacklogUserServiceImpl extends BaseServiceImpl<BacklogUser, Integer> implements BacklogUserService {
    @Autowired
    public BacklogUserDao backlogUserDao;
    @Autowired
    UserService userService;
    @Autowired
    BacklogService backlogService;
    @Autowired
    public void setBaseDao(BacklogUserDao dao) {
        this.baseDao = dao;
    }

    @Override
    public boolean mission(Integer missionId, Integer userId) {
        int count = backlogUserDao.findTotalCount("select count(*) from BacklogUser where backlog_id = ? and user_id = ?",missionId,userId);
        return count > 0;
    }

    @Override
    public void finishByMission(Integer missionId, Integer userId,BacklogType type) {
        List<BacklogUser> backlogUsers = backlogUserDao.find("from BacklogUser where backlog.missionId = ? and user.id = ? and backlog.type = ?",missionId,userId,type);
        if (backlogUsers.size()  != 0){
            for (BacklogUser b:backlogUsers){
                b.initTime();
                b.setFinish(true);
                backlogUserDao.update(b);
            }
        }
    }

    @Override
    public void finishByBacklog(Integer userId,Integer backlogId,BacklogType type) {
        List<BacklogUser> backlogUsers = backlogUserDao.find("from BacklogUser where user.id = ? and backlog.id = ? and backlog.type = ?",userId,backlogId,type);
        if (backlogUsers != null && !backlogUsers.isEmpty()){
            for (BacklogUser b:backlogUsers){
                b.initTime();
                b.setFinish(true);
                backlogUserDao.update(b);
            }
        }
    }

    @Override
    public BacklogUser findByBacklogAndUser(Integer userId, Integer backlogId) {
        return backlogUserDao.findOne("from BacklogUser where user.id = ? and backlog.id = ?",userId,backlogId);
    }


    @Override
    public List<BacklogUser> findByBacklog(Integer backlogId) {
        List<BacklogUser> backlogUsers = backlogUserDao.find("from BacklogUser where backlog.id = ?",backlogId);
        return backlogUsers;
    }

    @Override
    public Pagination page(Pagination page, Integer userId) {
        Pagination pageRsult = backlogUserDao.findbypage(page,"SELECT new map(DATE_FORMAT(backlog.doDate,'%Y-%m-%d') as doDate," +
                "backlog.subject as subject,backlog.id as id,backlog.missionId as missionId,backlog.type as type," +
                "backlog.year as year)from BacklogUser where user.id = ? and finish = 0 order by backlog.doDate desc",userId);
        return pageRsult;
        }

    @Override
    public void deleteUser(Integer backlogId, Integer id) {
        BacklogUser user = backlogUserDao.findOne("from BacklogUser where backlog.id = ? and user.id  = ?",backlogId,id);
        if (user != null){
            backlogUserDao.delete(user);
        }
    }

    @Override
    public void backlogs(String token, String path, List<Object> args) {
        Integer missionId = null;
        Integer backlogId = null;
        BacklogType type = null;
        Integer userId = null;
        if(path.indexOf("exam/scoreSave")>0){
            Score score= (Score) args.get(1);//该方法参数的索引
            missionId = score.getExam().getId();
            type = BacklogType.ZXKS;
        }
        if(path.indexOf("job/add")>0){
            if (args.get(2) != null) {
                backlogId = Integer.parseInt(args.get(2).toString());
                type = BacklogType.GZFK;
            }
        }
        if(path.indexOf("report/add")>0){
            if (args.get(2) != null) {
                backlogId = Integer.parseInt(args.get(2).toString());
                type = BacklogType.SXHB;
            }
        }
        if(path.indexOf("appr/commit")>0){
            missionId = Integer.parseInt(args.get(3).toString());
            type = BacklogType.MZPY;
        }
        if(path.indexOf("vote/commit")>0){
            missionId = Integer.parseInt(args.get(3).toString());
            type = BacklogType.ZXTP;
        }
        if(path.indexOf("article/info")>0){
            missionId = Integer.parseInt(args.get(1).toString());
            type = BacklogType.XWCK;
        }
        if (type != null){
            if (type != BacklogType.ZXJF){
                userId = TokenUtils.get(token,"id").asInt();
            }
            if (missionId != null){
                finishByMission(missionId,userId,type);
            }else if (backlogId != null){
                finishByBacklog(userId,backlogId,type);
            }
        }
    }
    @Override
    public void addBatch(Integer backlogId,Integer[] ids) {
        for (int i=0;i<ids.length;i++){
            User user = userService.get(ids[i]);
            Backlog backlog = backlogService.get(backlogId);
            BacklogUser backlogUser = new BacklogUser();
            backlogUser.setUser(user);
            backlogUser.setBacklog(backlog);
            backlogUser.setFinish(false);
            backlogUserDao.save(backlogUser);
        }
    }
}
