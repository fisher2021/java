package com.upd.business.controller.yulin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.entity.JobBack;
import com.upd.business.entity.JobBackReply;
import com.upd.business.entity.JobBackUser;
import com.upd.business.entity.User;
import com.upd.business.form.JobForm;
import com.upd.business.service.JobBackReplyService;
import com.upd.business.service.JobBackService;
import com.upd.business.service.JobBackUserService;
import com.upd.business.service.UserService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.business.vo.ORGUserVo;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 工作反馈(展示)控制器
 * Created by ljw on 2017/5/4.
 */
@Controller("yulinJobBackController")
@RequestMapping("yulin/job")
public class JobBackController extends BaseController {
    @Autowired
    JobBackService jobBackService;
    @Autowired
    UserService userService;
    @Autowired
    JobBackReplyService jobBackReplyService;
    @Autowired
    JobBackUserService jobBackUserService;

    /**
     * 获取工作展示列表
     * @param page
     * @param form 用户ID
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(Pagination page, @RequestParam Boolean mine, JobForm form, String callback) throws InvocationTargetException, IllegalAccessException, ParseException {
        int userId = Integer.valueOf(OtherConfigBundle.getConfig("yulin_user_id"));
        if (mine){//我的展示
            form.setUserId(userId);
        }
        jobBackService.page(page,form);
        List<JobBack> jobBacks = page.getList();
        List<JobBack> result = new ArrayList<>();
        if (jobBacks != null && jobBacks.size() != 0){
            for (JobBack jobBack:jobBacks){
                List<JobBackReply> jobBackReplies = jobBack.getJobBackReplies();
                List<JobBackReply> newJobBackReplies = new ArrayList<>();
                if (jobBackUserService.findByUserAndJob(userId,jobBack.getId()) != null){
                    jobBack.setZan(true);//已点赞
                }
                jobBack.setZanCount(jobBack.getUsers().size());//点赞数
                if (jobBackReplies != null && jobBackReplies.size() != 0){
                    jobBack.setReplyCount(jobBackReplies.size());//回复总数
                    //按回复时间排序
                    Collections.sort(jobBackReplies, new Comparator<JobBackReply>() {
                        @Override
                        public int compare(JobBackReply o1, JobBackReply o2) {
                            int i = o2.getCreateTime().compareToIgnoreCase(o1.getCreateTime());
                            return i;
                        }
                    });
                    //取前三条回复
                    for (int i=0;i<jobBackReplies.size();i++){
                        newJobBackReplies.add(jobBackReplies.get(i));
                        if (newJobBackReplies.size() == 3){
                            break;
                        }
                    }
                    //回复用户
                    for (JobBackReply jobBackReply:newJobBackReplies){
                        ORGUserVo orgUserVo = new ORGUserVo();
                        BeanUtils.copyProperties(orgUserVo,jobBack.getUser());
                        jobBackReply.setReplyUser(orgUserVo);
                    }
                }
                jobBack.setJobBackReplies(newJobBackReplies);
                result.add(jobBack);
            }
        }
        page.setList(result);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return callback + "("+ new RestResult("展示内容列表",pageVo).toString() + ")";
    }

    /**
     * 回复列表
     * @param id
     * @return
     */
    @RequestMapping("/replies/{id}")
    @ResponseBody
    public String replies(@PathVariable("id") Integer id, Pagination page, String callback) throws InvocationTargetException, IllegalAccessException {
        jobBackReplyService.pageByJobBackId(page,id);
        List<JobBackReply> jobBackReplies = page.getList();
        for (JobBackReply jobBackReply:jobBackReplies){
            ORGUserVo orgUserVo = new ORGUserVo();
            BeanUtils.copyProperties(orgUserVo,jobBackReply.getUser());
            jobBackReply.setReplyUser(orgUserVo);
        }
        page.setList(jobBackReplies);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return callback + "("+ new RestResult("评论列表",pageVo).toString() + ")";
    }

    /**
     * 点赞
     * @param type 0:取消赞 1:点赞
     * @return
     */
    @RequestMapping("/zan/{id}")
    @ResponseBody
    public String zan(@PathVariable("id") Integer id,@RequestHeader("token") String token,Integer type, String callback){
        JWT jwt= TokenUtils.verify(token);
        Claim userId = jwt.getHeaderClaim("id");
        User user = userService.get(userId.asInt());
        if (type.equals(1)){//点赞
            JobBackUser jobBackUser = new JobBackUser();
            JobBack jobBack = jobBackService.get(id);
            jobBackUser.setJobBack(jobBack);
            jobBackUser.setUser(user);
            jobBackUserService.add(jobBackUser);
        }else if(type.equals(0)){//取消赞
            JobBackUser jobBackUser = jobBackUserService.findByUserAndJob(userId.asInt(),id);
            jobBackUserService.delete(jobBackUser);
        }
        return callback + "("+ new RestResult("点赞",null).toString() + ")";
    }
}
