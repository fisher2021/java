package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.constant.BacklogType;
import com.upd.business.constant.TrackType;
import com.upd.business.entity.JobBack;
import com.upd.business.entity.JobBackReply;
import com.upd.business.entity.JobBackUser;
import com.upd.business.entity.User;
import com.upd.business.form.JobForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.*;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作反馈(展示)控制器
 * Created by ljw on 2017/5/4.
 */
@Controller
@RequestMapping("rest/job")
public class JobBackController extends BaseController {
    @Autowired
    JobBackService jobBackService;
    @Autowired
    UserService userService;
    @Autowired
    FileSaveService fileSaveService;
    @Autowired
    JobBackReplyService jobBackReplyService;
    @Autowired
    JobBackUserService jobBackUserService;
    @Autowired
    OperatorService operatorService;

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
    public String page(@RequestHeader("token") String token,Pagination page,@RequestParam Boolean mine,JobForm form) throws InvocationTargetException, IllegalAccessException, ParseException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        if (mine){//我的展示
            form.setUserId(userId.asInt());
        }
        jobBackService.page(page,form);
        List<JobBack> jobBacks = page.getList();
        List<JobBack> result = new ArrayList<>();
        if (jobBacks != null && jobBacks.size() != 0){
            for (JobBack jobBack:jobBacks){
                getJobBack(jobBack,userId.asInt());
                result.add(jobBack);
            }
        }
        page.setList(result);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("展示内容列表",pageVo).toString();
    }

    /**
     * 工作展示详情
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    public String info(@RequestHeader("token") String token,@PathVariable("id") Integer id) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        JobBack jobBack = jobBackService.get(id);
        getJobBack(jobBack,userId.asInt());
        return  new RestResult("工作展示详情",jobBack).toString();
    }

    public JobBack getJobBack(JobBack jobBack,int userId) throws InvocationTargetException, IllegalAccessException {
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
                BeanUtils.copyProperties(orgUserVo,jobBackReply.getUser());
                jobBackReply.setReplyUser(orgUserVo);
            }
        }
        jobBack.setJobBackReplies(newJobBackReplies);
        return jobBack;
    }
    /**
     * 提交工作展示
     * @param token
     * @param jobBack
     * @param backlogId
     * @param files
     * @return
     * @throws IOException
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestHeader("token") String token,  JobBack jobBack, Integer backlogId ,@RequestParam("file") CommonsMultipartFile files[]) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user=userService.get(id.asInt());
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        jobBack.setOperator(operator);
        jobBack.setUser(user);
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {
                String folder = "/jobBack/";
                String image = fileSaveService.add(files[i],folder).getUrl();
                if (i == 0){
                    jobBack.setImage1(image);
                }else {
                    jobBack.setImage2(image);
                }
            }
        }
        jobBackService.add(jobBack);
        return  new RestResult("提交工作展示内容",null).toString();
    }
    /**
     * 添加回复
     * @param token
     * @param jobBackReply
     * @return
     */
    @RequestMapping(value = "/addReply",method = RequestMethod.POST)
    @ResponseBody
    public String addReply(@RequestHeader("token") String token, JobBackReply jobBackReply) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        jobBackReply.setUser(user);
        jobBackReplyService.add(jobBackReply);
        return  new RestResult("添加回复",null).toString();
    }
    /**
     * 回复列表
     * @param id
     * @return
     */
    @RequestMapping("/replies/{id}")
    @ResponseBody
    public String replies(@PathVariable("id") Integer id,Pagination page) throws InvocationTargetException, IllegalAccessException {
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
        return new RestResult("评论列表",pageVo).toString();
    }
    /**
     * 删除回复
     * @param jobBackReply
     * @return
     */
    @RequestMapping("/deleteReply")
    @ResponseBody
    public String deleteReply(JobBackReply jobBackReply){
        jobBackReplyService.delete(jobBackReply);
        return new RestResult("删除回复",null).toString();
    }
    /**
     * 点赞
     * @param type 0:取消赞 1:点赞
     * @return
     */
    @RequestMapping("/zan/{id}")
    @ResponseBody
    public String zan(@PathVariable("id") Integer id,@RequestHeader("token") String token,Integer type){
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
        return new RestResult("点赞",null).toString();
    }
}
