package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.AdviceForm;
import com.upd.business.form.UserForm;
import com.upd.business.form.WorkTaskForm;
import com.upd.business.service.*;
import com.upd.business.vo.*;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.xinge.Xinge;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 工作任务控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/task")
public class WorkTaskController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    WorkTaskService workTaskService;
    @Autowired
    FileSaveService fileSaveService;
    @Autowired
    ORGService orgService;
    @Autowired
    WorkTaskReplyService workTaskReplyService;
    @Autowired
    WorkTaskUserService workTaskUserService;
    @Autowired
    OperatorService operatorService;

    /**
     * 获取列表
     * @param page
     * @param type
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token,Pagination page,Integer type) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        workTaskService.getPage(page,userId.asInt(),type);
        List<WorkTask> workTasks = page.getList();
        List<WorkTaskVo> workTaskVos = new ArrayList<>();
        if (workTasks != null && workTasks.size() != 0){
            for (WorkTask workTask:workTasks){
                WorkTaskVo workTaskVo = new WorkTaskVo();
                BeanUtils.copyProperties(workTaskVo,workTask);
                //创建人
                UserVo createUserVo = new UserVo();
                BeanUtils.copyProperties(createUserVo,workTask.getUser());
                workTaskVo.setCreateUser(createUserVo);
                //获取前8位接收人
                List<User> users = workTask.getUsers();
                List<UserVo> userVos = new ArrayList<>();
                if (users != null && users.size() != 0){
                    workTaskVo.setReceivedUsersCount(users.size());//接收人总数
                    for (int j=0;j<users.size();j++){
                        UserVo userVo = new UserVo();
                        BeanUtils.copyProperties(userVo,users.get(j));
                        userVos.add(userVo);
                        if (userVos.size() == 8){
                            break;
                        }
                    }
                }
                workTaskVo.setReceivedUsers(userVos);
                //获取回复列表
                List<WorkTaskReply> workTaskReplies = workTask.getWorkTaskReplies();
                List<WorkTaskReply> newWorkTaskReplies = new ArrayList<>();
                if (workTaskReplies != null && workTaskReplies.size() != 0){
                    workTaskVo.setReplyCount(workTaskReplies.size());//回复总数
                    //按回复时间排序
                    Collections.sort(workTaskReplies, new Comparator<WorkTaskReply>() {
                        @Override
                        public int compare(WorkTaskReply o1, WorkTaskReply o2) {
                            int i = o2.getCreateTime().compareToIgnoreCase(o1.getCreateTime());
                            return i;
                        }
                    });
                    //取前三条回复
                    for (int i=0;i<workTaskReplies.size();i++){
                        newWorkTaskReplies.add(workTaskReplies.get(i));
                        if (newWorkTaskReplies.size() == 3){
                            break;
                        }
                    }
                    //回复用户
                    for (WorkTaskReply workTaskReply:newWorkTaskReplies){
                        UserVo userVo = new UserVo();
                        BeanUtils.copyProperties(userVo,workTaskReply.getUser());
                        workTaskReply.setReplyUser(userVo);
                    }
                }
                workTaskVo.setWorkTaskReplies(newWorkTaskReplies);
                workTaskVos.add(workTaskVo);
            }
        }
        page.setList(workTaskVos);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("工作任务列表",pageVo).toString();
    }

    /**
     * 新增工作任务
     * @param token
     * @param workTask
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestHeader("token") String token, WorkTask workTask, MultipartHttpServletRequest request, @RequestParam(value = "userIds") JSONArray userIds) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        workTask.setUser(user);
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        workTask.setOperator(operator);
        //图片上传处理
        List<MultipartFile> imgs = request.getFiles("img");
        if (imgs != null && imgs.size() != 0){
            String folder = "/worktask/";
            List<FileSave> images = new ArrayList<>();
            for (MultipartFile img : imgs){
                FileSave fileSave = fileSaveService.add(img,folder);
                images.add(fileSave);
            }
            workTask.setImages(images);
        }
        List<User> users = new ArrayList<>();
        for (int i=0;i< userIds.length();i++) {
            users.add(userService.get(Integer.valueOf(userIds.get(i).toString())));
        }
        workTask.setUsers(users);
        workTaskService.add(workTask);
        //推送工作任务消息
        List<String> accounts = new ArrayList<>();
        for (User u:users){
            accounts.add(u.getAccount());
        }
        String title = "工作任务";
        if (accounts.size() < 100){
            //批量推送
            JSONObject jsonObject = Xinge.pushBatchAndroid(accounts,title,workTask.getContent());
            System.out.println(jsonObject);
            Xinge.pushBatchIos(accounts,title,workTask.getContent());
        }else {
            //大批量推送
            Xinge.pushMultipushAndroid(accounts,title,workTask.getContent());
            Xinge.pushMultipushIos(accounts,title,workTask.getContent());
        }
        return  new RestResult("新增工作任务",null).toString();
    }
    /**
     * 添加指派对象
     * @param token
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/getUsers")
    @ResponseBody
    public String orgList(@RequestHeader("token") String token) throws InvocationTargetException, IllegalAccessException {
        List<ORGVo> result = new ArrayList<ORGVo>();
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        ORG userOrg = user.getHighestOrg();
        if (userOrg != null){
            if (userOrg.getLevel() != ORGType.PARTY_BRANCH){//党组和机关党委的
                List<ORG> ls= orgService.getChildrenByCode(jwt.getHeaderClaim("unitCode").asString()+ "0");
                for (ORG org : ls) {
                    result.add(getResult(org));
                }
            }else {//党支部的
                result.add(getResult(userOrg));
            }
        }
        return new RestResult("添加指派对象",result).toString();
    }
    //获取组织里的用户
    public ORGVo getResult(ORG org) throws InvocationTargetException, IllegalAccessException {
        ORGVo orgVo = new ORGVo();
        BeanUtils.copyProperties(orgVo, org);
        List<ORGUserVo> userVos = new ArrayList<>();//组织用户
        UserForm form = new UserForm();
        form.setOrgId(org.getId());
        form.setLevel(org.getLevel());
        List<User> userList = userService.orgList(form);
        for (User u : userList) {
            ORGUserVo userVo = new ORGUserVo();
            BeanUtils.copyProperties(userVo, u);
            userVos.add(userVo);
        }
        orgVo.setUser(userVos);
        return orgVo;
    }

    /**
     * 接收人列表
     * @param id
     * @return
     */
    @RequestMapping("/receivedUsers/{id}")
    @ResponseBody
    public String receivedUsers(@PathVariable("id") Integer id,Pagination page) throws InvocationTargetException, IllegalAccessException {
        workTaskUserService.pageByWorkTaskId(page,id);
        List<WorkTaskUser> workTaskUsers = page.getList();
        List<UserVo> userVos = new ArrayList<>();
        for (WorkTaskUser workTaskUser:workTaskUsers){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userVo,workTaskUser.getUser());
            userVos.add(userVo);
        }
        page.setList(userVos);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return new RestResult("接收人列表",pageVo).toString();
    }
    /**
     * 回复列表
     * @param id
     * @return
     */
    @RequestMapping("/replies/{id}")
    @ResponseBody
    public String replies(@PathVariable("id") Integer id,Pagination page) throws InvocationTargetException, IllegalAccessException {
        workTaskReplyService.pageByWorkTaskId(page,id);
        List<WorkTaskReply> workTaskReplies = page.getList();
        for (WorkTaskReply workTaskReply:workTaskReplies){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userVo,workTaskReply.getUser());
            workTaskReply.setReplyUser(userVo);
        }
        page.setList(workTaskReplies);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return new RestResult("评论列表",pageVo).toString();
    }
    /**
     * 添加回复
     * @param token
     * @param workTaskReply
     * @return
     */
    @RequestMapping(value = "/addReply",method = RequestMethod.POST)
    @ResponseBody
    public String addReply(@RequestHeader("token") String token, WorkTaskReply workTaskReply, MultipartHttpServletRequest request) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        workTaskReply.setUser(user);
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        workTaskReply.setOperator(operator);
        //图片上传处理
        List<MultipartFile> imgs = request.getFiles("img");
        if (imgs != null && imgs.size() != 0){
            String folder = "/worktaskReply/";
            List<FileSave> images = new ArrayList<>();
            for (MultipartFile img : imgs){
                FileSave fileSave = fileSaveService.add(img,folder);
                images.add(fileSave);
            }
            workTaskReply.setImages(images);
        }
        workTaskReplyService.add(workTaskReply);
        return  new RestResult("添加回复",null).toString();
    }

    /**
     * 删除回复
     * @param workTaskReply
     * @return
     */
    @RequestMapping("/deleteReply")
    @ResponseBody
    public String deleteReply(WorkTaskReply workTaskReply){
        workTaskReplyService.delete(workTaskReply);
        return new RestResult("删除回复",null).toString();
    }
}
