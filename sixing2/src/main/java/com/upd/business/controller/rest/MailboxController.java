package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.service.*;
import com.upd.business.vo.MailboxVo;
import com.upd.business.vo.ORGUserVo;
import com.upd.business.vo.PageVo;
import com.upd.business.vo.UserVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 工作任务控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/mailbox")
public class MailboxController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    MailboxService mailboxService;
    @Autowired
    MailboxReplyService mailboxReplyService;
    @Autowired
    MailboxUserService mailboxUserService;
    @Autowired
    OperatorService operatorService;

    /**
     * 获取列表
     * @param page
     * @param type
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token,Pagination page,Integer type) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        mailboxService.getPage(page,userId.asInt(),type);
        List<Mailbox> mailboxes = page.getList();
        List<MailboxVo> result =  new ArrayList<>();
        if (mailboxes != null && mailboxes.size() != 0){
            for (Mailbox mailbox:mailboxes){
                MailboxVo mailboxVo = new MailboxVo();
                BeanUtils.copyProperties(mailboxVo,mailbox);
                if (type.equals(0)){//收件箱
                    //阅读状态
                    MailboxUser mailboxUser = mailboxUserService.findByMailboxAndUser(mailbox.getId(),userId.asInt());
                    mailboxVo.setRead(mailboxUser.isRead());
                    //创建人
                    UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(userVo,mailbox.getUser());
                    mailboxVo.setCreateUser(userVo);
                }else if (type.equals(1)){//发件箱
                    //接收人
                    List<User> users = mailbox.getUsers();
                    List<UserVo> userVos = new ArrayList<>();
                    if (users != null && users.size() != 0){
                        for (User user:users){
                            UserVo userVo = new UserVo();
                            BeanUtils.copyProperties(userVo,user);
                            userVos.add(userVo);
                        }
                    }
                    mailboxVo.setReceivedUsers(userVos);
                }
                mailboxVo.setMailboxReplies(null);//不返回回复
                result.add(mailboxVo);
            }
        }
        page.setList(result);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("书记信箱列表",pageVo).toString();
    }

    /**
     * 信箱详情
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    public String info(@RequestHeader("token") String token,@PathVariable("id") Integer id) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        MailboxUser mailboxUser = mailboxUserService.findByMailboxAndUser(id,userId.asInt());
        if (mailboxUser != null && !mailboxUser.isRead()){
            mailboxUser.setRead(true);//设置状态已读
            mailboxUserService.edit(mailboxUser);
        }
        Mailbox mailbox = mailboxService.get(id);
        //获取回复列表
        List<MailboxReply> mailboxReplies = mailbox.getMailboxReplies();
        if (mailboxReplies != null && mailboxReplies.size() != 0){
            //按回复时间倒序排序
            Collections.sort(mailboxReplies, new Comparator<MailboxReply>() {
                @Override
                public int compare(MailboxReply o1, MailboxReply o2) {
                    int i = o2.getCreateTime().compareToIgnoreCase(o1.getCreateTime());
                    return i;
                }
            });
            //回复人
            for (MailboxReply mailboxReply:mailboxReplies){
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(userVo,mailboxReply.getUser());
                mailboxReply.setReplyUser(userVo);
            }
        }
        MailboxVo mailboxVo = new MailboxVo();
        BeanUtils.copyProperties(mailboxVo,mailbox);
        //创建人
        UserVo createUserVo = new UserVo();
        BeanUtils.copyProperties(createUserVo,mailbox.getUser());
        mailboxVo.setCreateUser(createUserVo);
        //接收人
        List<User> users = mailbox.getUsers();
        List<UserVo> userVos = new ArrayList<>();
        if (users != null && users.size() != 0){
            for (User user:users){
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(userVo,user);
                userVos.add(userVo);
            }
        }
        mailboxVo.setReceivedUsers(userVos);
        return new RestResult("信箱详情",mailboxVo).toString();
    }
    /**
     * 写信
     * @param token
     * @param mailbox
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestHeader("token") String token, Mailbox mailbox, @RequestParam(value = "userIds") JSONArray userIds) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        mailbox.setUser(user);//创建人
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        mailbox.setOperator(operator);
        for (int i=0;i< userIds.length();i++) {
            mailbox.getUsers().add(userService.get(Integer.valueOf(userIds.get(i).toString())));//接收人
        }
        mailboxService.add(mailbox);
        return  new RestResult("写信",null).toString();
    }
    /**
     * 获取书记（领导）列表
     * @return
     */
    @RequestMapping(value = "/getUsers")
    @ResponseBody
    public String getUsers(@RequestHeader("token") String token) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        List<User> users = userService.getLeaders(unitCode);
        List<UserVo> result = new ArrayList<>();
        if (users != null && users.size() != 0){
            for (User user:users){
                if (user.getId().equals(jwt.getHeaderClaim("id").asInt())){
                    continue;//不能发给自己
                }
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(userVo,user);
                result.add(userVo);
            }
        }
        return new RestResult("获取书记列表",result).toString();
    }

    /**
     * 接收人列表
     * @param id
     * @return
     */
    @RequestMapping("/receivedUsers/{id}")
    @ResponseBody
    public String receivedUsers(@PathVariable("id") Integer id,Pagination page) throws InvocationTargetException, IllegalAccessException {
        mailboxUserService.pageByMailboxId(page,id);
        List<MailboxUser> mailboxUsers = page.getList();
        List<UserVo> result = new ArrayList<>();
        for (MailboxUser mailboxUser:mailboxUsers){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userVo,mailboxUser.getUser());
            result.add(userVo);
        }
        page.setList(result);
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
        mailboxReplyService.pageByMailboxId(page,id);
        List<MailboxReply> mailboxReplies = page.getList();
        for (MailboxReply mailboxReply:mailboxReplies){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userVo,mailboxReply.getUser());
            mailboxReply.setReplyUser(userVo);
        }
        page.setList(mailboxReplies);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return new RestResult("回复列表",pageVo).toString();
    }
    /**
     * 添加回复
     * @param token
     * @param mailboxReply
     * @return
     */
    @RequestMapping(value = "/addReply",method = RequestMethod.POST)
    @ResponseBody
    public String addReply(@RequestHeader("token") String token, MailboxReply mailboxReply) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        mailboxReply.setUser(user);
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        mailboxReply.setOperator(operator);
        mailboxReplyService.add(mailboxReply);
        return  new RestResult("添加回复",null).toString();
    }

    /**
     * 删除回复
     * @param mailboxReply
     * @return
     */
    @RequestMapping("/deleteReply")
    @ResponseBody
    public String deleteReply(MailboxReply mailboxReply){
        mailboxReplyService.delete(mailboxReply);
        return new RestResult("删除回复",null).toString();
    }
    /**
     * 删除信
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestHeader("token") String token,Integer id,Integer type){
        if (type.equals(0)){//收件箱
            JWT jwt= TokenUtils.verify(token);
            Claim userId= jwt.getHeaderClaim("id");
            MailboxUser mailboxUser = mailboxUserService.findByMailboxAndUser(id,userId.asInt());
            mailboxUser.setDeleted(true);
            mailboxUserService.edit(mailboxUser);
        }else if (type.equals(1)){//发件箱
            Mailbox mailbox = mailboxService.get(id);
            mailbox.setDeleted(true);
            mailboxService.edit(mailbox);
        }
        return new RestResult("删除信",null).toString();
    }
}
