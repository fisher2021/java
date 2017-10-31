package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.UserForm;
import com.upd.business.service.*;
import com.upd.business.vo.*;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.DateUtil;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.xinge.Xinge;
import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.aspectj.bridge.Version.getTime;

/**
 * 预约会议控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/conference")
public class ConferenceController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    ConferenceService conferenceService;
    @Autowired
    ORGService orgService;
    @Autowired
    ConferenceUserService conferenceUserService;
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
    public String page(@RequestHeader("token") String token,Pagination page,Integer type) throws InvocationTargetException, IllegalAccessException, ParseException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        conferenceService.getPage(page,userId.asInt(),type);
        List<Conference> conferences = page.getList();
        List<ConferenceVo> conferenceVos = new ArrayList<>();
        if (conferences != null && !conferences.isEmpty()){
            for (Conference conference:conferences){
                ConferenceVo conferenceVo = new ConferenceVo();
                BeanUtils.copyProperties(conferenceVo,conference);
                //创建人
                UserVo createUserVo = new UserVo();
                BeanUtils.copyProperties(createUserVo,conference.getUser());
                conferenceVo.setCreateUser(createUserVo);
                //获取前8位接收人
                List<User> users = conference.getUsers();
                List<UserVo> userVos = new ArrayList<>();
                if (users != null && users.size() != 0){
                    conferenceVo.setReceivedUsersCount(users.size());//接收人总数
                    for (int j=0;j<users.size();j++){
                        UserVo userVo = new UserVo();
                        BeanUtils.copyProperties(userVo,users.get(j));
                        userVos.add(userVo);
                        if (userVos.size() == 8){
                            break;
                        }
                    }
                }
                //设置会议状态
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = format.parse(conference.getTime());
                if (date.before(new Date())){
                    conferenceVo.setEnd(true);
                }
                conferenceVo.setReceivedUsers(userVos);
                conferenceVos.add(conferenceVo);
            }
        }
        page.setList(conferenceVos);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("预约会议列表",pageVo).toString();
    }

    /**
     * 新增预约会议
     * @param token
     * @param conference
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestHeader("token") String token, Conference conference, @RequestParam(value = "userIds") JSONArray userIds) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        conference.setUser(user);
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        conference.setOperator(operator);
        List<User> users = new ArrayList<>();
        for (int i=0;i< userIds.length();i++) {
            users.add(userService.get(Integer.valueOf(userIds.get(i).toString())));
        }
        conference.setUsers(users);
        conferenceService.add(conference);
        //推送预约会议消息
        List<String> accounts = new ArrayList<>();
        for (User u:users){
            accounts.add(u.getAccount());
        }
        String title = "预约会议";
        String content = "时间："+conference.getTime()+"\n"+"地点："+conference.getAddress();
        if (accounts.size() < 100){
            //批量推送
            JSONObject jsonObject = Xinge.pushBatchAndroid(accounts,title,content);
            System.out.println(jsonObject);
            Xinge.pushBatchIos(accounts,title,content);
        }else {
            //大批量推送
            Xinge.pushMultipushAndroid(accounts,title,content);
            Xinge.pushMultipushIos(accounts,title,content);
        }
        return  new RestResult("新增预约会议",null).toString();
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
        List<ORGVo> result = new ArrayList<>();
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        ORG userOrg = user.getHighestOrg();
        if (userOrg != null){
            if (userOrg.getLevel() != ORGType.PARTY_BRANCH){//党组和机关党委的
                List<ORG> ls= orgService.getChildrenByCode(jwt.getHeaderClaim("unitCode").asString()+ "0");
                for (ORG org : ls) {
                    result.add(getORGVo(org));
                }
            }else {//党支部的
                result.add(getORGVo(userOrg));
            }
        }
        return new RestResult("添加指派对象",result).toString();
    }
    //获取组织里的用户
    public ORGVo getORGVo(ORG org) throws InvocationTargetException, IllegalAccessException {
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
        conferenceUserService.pageByConferenceId(page,id);
        List<ConferenceUser> conferenceUsers = page.getList();
        List<UserVo> userVos = new ArrayList<>();
        for (ConferenceUser conferenceUser:conferenceUsers){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userVo,conferenceUser.getUser());
            userVos.add(userVo);
        }
        page.setList(userVos);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return new RestResult("接收人列表",pageVo).toString();
    }


}
