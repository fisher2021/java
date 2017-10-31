package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.BacklogType;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.Backlog;
import com.upd.business.entity.BacklogUser;
import com.upd.business.entity.ORG;
import com.upd.business.entity.User;
import com.upd.business.form.BacklogForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.*;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 待办事项后台管理控制器
 * @author ljw
 * 2017年1月5日
 */
@Controller("backBacklogController")
@RequestMapping("/back/backlog")
public class BacklogController extends BaseController {
	@Autowired 
	private BacklogService backlogService;
    @Autowired
    private BacklogUserService backlogUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private ORGService orgService;
	/**
	 * 分页条件查询待办事项
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, BacklogForm form, HttpSession session){
	    //获取登录信息,支部管理员只能查看本支部的党务日历
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		backlogService.page(page, form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
        model.addAttribute("type", BacklogType.values());
		return "backlog/page";
	}
	/**
	 * 跳转到待办事项详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public String info(Integer id, Model model){
		Backlog backlog = backlogService.get(id);
        List<BacklogUser> backlogUsers = backlogUserService.findByBacklog(backlog.getId());
		model.addAttribute("backlog", backlog);
        model.addAttribute("backlogUsers", backlogUsers);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "backlog/info";
	}

	/**
	 * 跳转到待办事项添加或修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model, HttpSession session){
		if(id != null){
			Backlog backlog = backlogService.get(id);
			model.addAttribute("backlog", backlog);
		}
        model.addAttribute("type", BacklogType.values());

        String code = getOperatorUnitCodeUnder(null);

        List<ORG> orgList = orgService.getChildrenByCode(code);

        model.addAttribute("orgList", orgList);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "backlog/addOrEdit";
	}

	/**
	 * 添加或修改待办事项
	 * @param backlog
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(Backlog backlog, HttpSession session,Integer[] orgs) {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        backlog.setOrgId(operator.getOrg().getId());
		if (backlog.getId() == null){
            backlog.setOperator(operator);//创建人
            backlog = backlogService.add(backlog);
		}else {
            backlog = backlogService.edit(backlog);
		}
        if (orgs != null){
            for (Integer orgId : orgs) {
                List<User> users = userService.findByORGforBacklog(orgId,backlog.getId());
                for (User user : users) {
                    if (backlogUserService.findByBacklogAndUser(user.getId(),backlog.getId()) == null){
                        BacklogUser backlogUser = new BacklogUser();
                        backlogUser.setBacklog(backlog);
                        backlogUser.setUser(user);
                        backlogUser.setFinish(false);
                        backlogUserService.save(backlogUser);
                    }
                }
            }
        }
        return new RestResult("添加或修改待办事项",null).toString();
	}

	/**
	 * 删除待办事项
	 * @param backlog
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Backlog backlog){
		backlogService.delete(backlog);
		return new RestResult("删除待办事项",null).toString();
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
        model.addAttribute("backlogId", form.getBacklogId());
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
        return "backlog/userPage";
    }
    /**
     * 批量添加指派对象
     * @param ids
     * @return
     */
    @RequestMapping("/ajax/addBatch")
    @ResponseBody
    public String addBatch(Integer backlogId,Integer[] ids){
        if(ids.length != 0){
            backlogUserService.addBatch(backlogId,ids);
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
    public String deleteUser(Integer backlogId,Integer id){
        backlogUserService.deleteUser(backlogId,id);
        return new RestResult("删除指派对象",null).toString();
    }

    /**
     * 判断任务ID是否存在
     * @param type
     * @param missionId
     * @return
     */
    @RequestMapping("/ajax/exist")
    @ResponseBody
    public String existMissionId(BacklogType type, int missionId){
        boolean flag = backlogService.existMissionId(type,missionId);
        return new RestResult("判断任务ID是否存在",flag).toString();
    }
}
