package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.ArticleForm;
import com.upd.business.form.CalendarForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.*;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 党务日历后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backCalendarController")
@RequestMapping("/back/calendar")
public class CalendarController extends BaseController {
	@Autowired 
	private CalendarService calendarService;
    @Autowired
    private CalendarUserService calendarUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private ORGService orgService;

	/**
	 * 分页条件查询党务日历列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, CalendarForm form, HttpSession session){
        //获取登录信息,支部管理员只能查看本支部的党务日历
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		calendarService.page(page, form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "calendar/page";
	}
	/**
	 * 跳转到党务日历详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public String info(Integer id, Model model){
		Calendar calendar = calendarService.get(id);
        List<CalendarUser> calendarUsers = calendarUserService.findByCalendar(calendar.getId());
		model.addAttribute("calendar", calendar);
        model.addAttribute("calendarUsers", calendarUsers);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "calendar/info";
	}

	/**
	 * 跳转到党务日历添加或修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model,HttpSession session){
		if(id != null){
			Calendar calendar = calendarService.get(id);
			model.addAttribute("calendar", calendar);
		}
		//获取当前用户的下级组织

		String code = getOperatorUnitCodeUnder(null);

		List<ORG> orgList = orgService.getChildrenByCode(code);
		model.addAttribute("orgList", orgList);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "calendar/addOrEdit";
	}

	/**
	 * 添加或修改党务日历
	 * @param calendar
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(Calendar calendar, HttpSession session,Integer[] orgs) throws IOException {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        calendar.setOrgId(operator.getOrg().getId());
        if (calendar.getId() == null){
            calendar.setOperator(operator);
            calendarService.add(calendar);
        }else {
            calendarService.edit(calendar);
        }
        if (orgs != null){
            for (Integer orgId : orgs) {
                List<User> users = userService.findByORG(orgId);
                for (User user : users) {
                    if (calendarUserService.findByUserAndCalendar(calendar.getId(),user.getId()) == null){
                        CalendarUser calendarUser = new CalendarUser();
                        calendarUser.setCalendar(calendar);
                        calendarUser.setUser(user);
                        calendarUserService.save(calendarUser);
                    }
                }
            }
        }
		return new RestResult("添加或修改党务日历",null).toString();
	}

	/**
	 * 删除党务日历
	 * @param calendar
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Calendar calendar){
		calendarService.delete(calendar);
		return new RestResult("删除党务日历",null).toString();
	}

	/**
	 * 批量删除党务日历
	 * @param ids
	 * @return
	 */
	@RequestMapping("/ajax/deleteBatch")
	@ResponseBody
	public String deleteBatch(String ids){
			if(StringUtils.isNotBlank(ids)){
				calendarService.deleteBatch(ids);
			}
		return new RestResult("批量删除党务日历",null).toString();
	}
    /**
     * 跳转到添加指派对象页面
     * @param page
     * @param model
     * @param form
     * @return
     */
    @RequestMapping("/userPage")
    public String list(Pagination page, Model model, UserForm form, HttpSession session){
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        userService.page(page,form);
        model.addAttribute("page", page);
        model.addAttribute("form", form);
        model.addAttribute("calendarId", form.calendarId);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
        return "calendar/userPage";
    }
    /**
     * 批量添加指派对象
     * @param ids
     * @return
     */
    @RequestMapping("/ajax/addBatch")
    @ResponseBody
    public String addBatch(int calendarId,Integer[] ids){
        if(ids.length != 0){
            calendarService.addBatch(calendarId,ids);
        }
        return new RestResult("批量添加指派对象",null).toString();
    }
    /**
     * 删除指派对象
     * @param id
     * @return
     */
    @RequestMapping("/ajax/deleteUser")
    @ResponseBody
    public String addBatch(Integer calendarId,Integer id){
        calendarUserService.deleteUser(calendarId,id);
        return new RestResult("删除指派对象",null).toString();
    }
	
}
