package com.upd.business.controller.back;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.ORG;
import com.upd.business.entity.User;
import com.upd.business.form.UserForm;
import com.upd.business.service.FileSaveService;
import com.upd.business.service.ORGService;
import com.upd.business.service.UserService;
import com.upd.business.utils.ExcelUtil;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.easemob.Easemob;
import com.upd.common.util.page.Pagination;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * APP用户信息后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backUserController")
@RequestMapping("/back/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
    @Autowired
    private FileSaveService fileSaveService;
    @Autowired
    private ORGService orgService;
	/**
	 * 分页条件查询用户信息列表
	 * @param form
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Pagination page, Model model, UserForm form, HttpSession session){
        //获取登录信息,支部管理员只能查看本支部的用户
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        if (form.getOrgId() != null){
            ORG org = orgService.get(form.getOrgId());
            form.setLevel(org.getLevel());
            model.addAttribute("level", org.getLevel().ordinal());
        }
		userService.page(page,form);
		model.addAttribute("branch", ORGType.PARTY_BRANCH);
		model.addAttribute("page", page);
        model.addAttribute("form", form);
        return "user/list";
	}
	
	/**
	 * 跳转到新增或编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model){
		if(id != null){
			User user = userService.get(id);
			model.addAttribute("user", user);
		}
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "user/addOrEdit";
	}
	
	/**
	 * 新增或保存用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(User user, MultipartHttpServletRequest request,Integer[] org) throws IOException {
		if (user.getId() == null){
			userService.addUser(user,org);
		}else {
            //头像上传处理
            MultipartFile file = request.getFile("file");
            if(file != null){
                //上传到文件服务器
                String folder = "/user/";
                String image = fileSaveService.add(file,folder).getUrl();
                user.setImage(image);
            }
			userService.editUser(user,org);
		}
		return new RestResult("新增或保存用户信息",null).toString();
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Integer id){
        User user = userService.get(id);
        Easemob.deleteUser(user.getAccount());//删除环信
        user.setAccount(null);
        user.setPhone(null);
        user.setDeleted(true);//假删
        userService.update(user);
		return new RestResult("删除用户",null).toString();
	}

	/**
	 * 账号唯一性验证
	 * @param user
	 * @return
	 */
	@RequestMapping("/ajax/only")
	@ResponseBody
	public String userInfoOnly(User user){
		boolean flag = userService.userInfoOnly(user);
		return new RestResult("账号唯一性验证",flag).toString();
	}

	/*
	* 跳转重置密码页面
	* @param id
	* @param model
	* @return
	*/
	@RequestMapping("/resetPwd")
	public String resetPwd(Integer id, Model model){
		if(id != null){
			User user = userService.get(id);
			model.addAttribute("user", user);
		}
		return "user/resetPwd";
	}

	/**
	 * 重置用户密码
	 * @param id
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping("/ajax/resetPwd")
	@ResponseBody
	public String resetPwd(Integer id,String account,String password){
		userService.upPassword(account,password,id);
		return new RestResult("重置用户密码",null).toString();
	}

    /**
     * 跳转到批量添加用户页面
     * @return
     */
    @RequestMapping("/addBatch")
    public String addBatch(){
        return "user/upload";
    }

    /**
     * 批量添加用户
     * @return
     */
    @RequestMapping("/ajax/addBatch")
    @ResponseBody
    public String addBatch(MultipartHttpServletRequest request) throws IOException, InvalidFormatException {
        //excel上传处理
        MultipartFile file = request.getFile("file");
        String message = "";
        if (file != null){
            message = userService.addBatch(file);
        }else {
            message = "请上传文件！";
        }
        return new RestResult("批量添加用户",message).toString();
    }

    /**
     * 下载模板
     * @return
     */
    @RequestMapping("/down")
    public String downloadExcel(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        ExcelUtil.downloadExcel(response,request);
        return new RestResult("下载模板",null).toString();
    }

    /**
     * 跳转到该组织用户页面
     * @param orgId
     * @return
     */
    @RequestMapping("/orgUser")
    public String getUserByOrg(Integer orgId, Model model){
        List<User> users = userService.findByORG(orgId);
        model.addAttribute("users",users);
        return "user/orguser/list";
    }

    /**
     * 排序
     * @param id 用户ID
     * @param type 排序方式
     * @param level 组织级别
     * @return
     */
    @RequestMapping("/rank")
    @ResponseBody
    public String upRank(Integer id,Integer type,Integer orgId,Integer level, HttpSession session){
        UserForm form = new UserForm();
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }else {
            form.setOrgId(orgId);
        }
        form.setLevel(ORGType.valueOf(level));
        List<User> users = userService.orgList(form);
        Integer otherId = null;
        for (int i=0;i<users.size();i++){
            if (users.get(i).getId().equals(id)){
                if (type.equals(1)){//向上排序
                    otherId = users.get(i-1).getId();
                }else if (type.equals(2)){//向下排序
                    otherId = users.get(i+1).getId();
                }
                break;
            }
        }
        userService.rank(id,otherId,level);
        return new RestResult("排序",null).toString();
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping("/export")
    @ResponseBody
    public String export(HttpServletResponse response,Integer orgId){
        userService.export(response,orgId);
        return new RestResult("导出",null).toString();
    }
}
