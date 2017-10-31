package com.upd.business.service.impl;

import com.upd.business.dao.VoteDao;
import com.upd.business.entity.ORG;
import com.upd.business.entity.User;
import com.upd.business.entity.Vote;
import com.upd.business.form.VoteForm;
import com.upd.business.service.UserService;
import com.upd.business.service.VoteService;
import com.upd.common.basis.base.QueryForm;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("voteService")
public class VoteServiceImpl extends BaseServiceImpl<Vote, Integer> implements VoteService{
    @Autowired
    public VoteDao voteDao;
    @Autowired
    UserService userService;
    @Autowired
    public void setBaseDao(VoteDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Vote add(Vote vote) {
        vote.initTime();
        voteDao.save(vote);
        return vote;
    }

    @Override
    public Vote edit(Vote vote) {
        vote.initTime();
        voteDao.update(vote);
        return vote;
    }

    @Override
    public List<Vote> search(Integer id) {
        List<Vote> votes = voteDao.find("from Vote where id = ?",id);
        return votes;
    }

    @Override
    public List<Vote>  searchByOrg(Integer userId) {
        User user = userService.get(userId);
        List<ORG> orgs = user.getOrgs();
        List<Vote> result = new ArrayList<>();
        for (ORG org : orgs){
            List<Vote> votes = new ArrayList<>();
            Integer orgId = org.getId();
            votes = voteDao.find("from Vote where orgId = ? or id in (SELECT id FROM Vote where FIND_IN_SET(?,orgList)>0) order by createTime desc",orgId,orgId);//所在组织的或者党组指定的
            if (votes != null && votes.size() > 0){
                for (Vote vote:votes){
                    if (result.indexOf(vote) < 0){//去重
                        result.add(vote);
                    }
                }
            }
        }
        //查询该类型的同支部的，或者党组的添加的
//        List<Vote> votes = voteDao.find("from Vote where (orgId = ? OR id in (SELECT id FROM Vote where FIND_IN_SET("+orgId+",orgList)>0))" +
//                "and id not in (SELECT id FROM Vote where FIND_IN_SET("+userId+",voter)>0) order by createTime desc",orgId);
        return result;
    }
}
