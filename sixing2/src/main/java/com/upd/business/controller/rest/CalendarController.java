package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.business.entity.Calendar;
import com.upd.business.entity.CalendarUser;
import com.upd.business.entity.User;
import com.upd.business.service.CalendarService;
import com.upd.business.service.CalendarUserService;
import com.upd.business.service.UserService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 党务日历控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/calendar")
public class CalendarController extends BaseController {
    @Autowired
    CalendarService calendarService;
    @Autowired
    CalendarUserService calendarUserService;
    @Autowired
    UserService userService;

    /**
     * 获取党务日历列表
     * @param token
     * @param month
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestHeader("token") String token,@RequestParam  String month) {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        List<CalendarUser> calendarUsers = calendarUserService.findByUser(id.asInt());//查询该用户的
        List<Calendar> calendars = new ArrayList<>();
        for (CalendarUser c : calendarUsers) {
            Calendar calendar = calendarService.searchByMonth(month,c.getCalendar().getId());//查询指定月份的
            if (calendar != null){
                calendars.add(calendar);
            }
        }
        return  new RestResult("党务日历列表",calendars).toString();
    }

    /**
     * 用户添加党务日历
     * @param calendar
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String addOrEdit(@RequestHeader("token") String token,Calendar calendar) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        calendar.setOrgId(0);//设置个人添加的党务日历
        List<User> users = new ArrayList<>();
        users.add(userService.get(id.asInt()));
        calendar.setUser(users);
        if (calendar.getId() == null){
            calendarService.add(calendar);
        }else {
            calendarService.edit(calendar);
        }
        return new RestResult("添加或修改党务日历",null).toString();
    }

    /**
     * 删除党务日历
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestHeader("token") String token,Integer id){
        Calendar calendar = calendarService.get(id);
        if (calendar.getOrgId() == 0){//自己添加的
            calendarService.delete(calendar);
        }else {//组织添加的
            JWT jwt= TokenUtils.verify(token);
            Claim userId = jwt.getHeaderClaim("id");
            calendarUserService.deleteUser(id,userId.asInt());
        }
        return new RestResult("删除党务日历",null).toString();
    }
}
