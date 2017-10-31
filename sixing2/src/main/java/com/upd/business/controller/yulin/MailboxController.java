package com.upd.business.controller.yulin;

import com.upd.business.entity.Mailbox;
import com.upd.business.entity.MailboxReply;
import com.upd.business.entity.MailboxUser;
import com.upd.business.entity.User;
import com.upd.business.service.MailboxReplyService;
import com.upd.business.service.MailboxService;
import com.upd.business.service.MailboxUserService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.business.vo.MailboxVo;
import com.upd.business.vo.PageVo;
import com.upd.business.vo.UserVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 书记信箱控制器
 * Created by ljw on 2017/5/5.
 */
@Controller("yulinMailboxController")
@RequestMapping("yulin/mailbox")
public class MailboxController extends BaseController {
    @Autowired
    MailboxService mailboxService;
    @Autowired
    MailboxReplyService mailboxReplyService;
    @Autowired
    MailboxUserService mailboxUserService;

    /**
     * 获取列表
     * @param page
     * @param type
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(Pagination page, Integer type, String callback) throws InvocationTargetException, IllegalAccessException {
        int userId = Integer.valueOf(OtherConfigBundle.getConfig("yulin_user_id"));
        mailboxService.getPage(page,userId,type);
        List<Mailbox> mailboxes = page.getList();
        List<MailboxVo> result =  new ArrayList<>();
        if (mailboxes != null && mailboxes.size() != 0){
            for (Mailbox mailbox:mailboxes){
                MailboxVo mailboxVo = new MailboxVo();
                BeanUtils.copyProperties(mailboxVo,mailbox);
                if (type.equals(0)){//收件箱
                    //阅读状态
                    MailboxUser mailboxUser = mailboxUserService.findByMailboxAndUser(mailbox.getId(),userId);
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
        return  callback + "("+ new RestResult("书记信箱列表",pageVo).toString() + ")";
    }

    /**
     * 信箱详情
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    public String info(@PathVariable("id") Integer id, String callback) throws InvocationTargetException, IllegalAccessException {
        int userId = Integer.valueOf(OtherConfigBundle.getConfig("yulin_user_id"));
        MailboxUser mailboxUser = mailboxUserService.findByMailboxAndUser(id,userId);
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
        return callback + "("+ new RestResult("信箱详情",mailboxVo).toString() + ")";
    }

    /**
     * 接收人列表
     * @param id
     * @return
     */
    @RequestMapping("/receivedUsers/{id}")
    @ResponseBody
    public String receivedUsers(@PathVariable("id") Integer id, Pagination page, String callback) throws InvocationTargetException, IllegalAccessException {
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
        return callback + "("+ new RestResult("接收人列表",pageVo).toString() + ")";
    }
    /**
     * 回复列表
     * @param id
     * @return
     */
    @RequestMapping("/replies/{id}")
    @ResponseBody
    public String replies(@PathVariable("id") Integer id, Pagination page, String callback) throws InvocationTargetException, IllegalAccessException {
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
        return callback + "("+ new RestResult("回复列表",pageVo).toString() + ")";
    }
}
