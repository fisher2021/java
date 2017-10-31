package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.EducationType;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.FileSave;
import com.upd.business.entity.ORG;
import com.upd.business.entity.User;
import com.upd.business.form.UserForm;
import com.upd.business.service.FileSaveService;
import com.upd.business.service.ORGService;
import com.upd.business.service.UserService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
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
 * 党员信息后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller
@RequestMapping("/back/partymember")
public class PartyMemberController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private ORGService orgService;
	@Autowired
	FileSaveService fileSaveService;
	/**
	 * 分页条件查询党员信息列表
	 * @param form
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, UserForm form, HttpSession session){
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		userService.page(page,form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "partymember/page";
	}
	
	/**
	 * 跳转到新增或编辑党员信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model){
		if(id != null){
			User user = userService.get(id);
			model.addAttribute("user", user);
		}
        model.addAttribute("education", EducationType.values());
		return "partymember/edit";
	}
	
	/**
	 * 新增或修改党员信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(User user, MultipartHttpServletRequest request) throws IOException {
		//图片上传处理
		MultipartFile file = request.getFile("file");
		if(file != null){
			//上传到文件服务器
            String folder = "/user/";
            String image = fileSaveService.add(file,folder).getUrl();
			user.setImage(image);
		}
        if (user.getId() != null){userService.editUser(user,null);
        }
		return new RestResult("新增或修改党员信息",null).toString();
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(User user){
		userService.delete(user);
		return new RestResult("删除党员信息",null).toString();
	}

}
