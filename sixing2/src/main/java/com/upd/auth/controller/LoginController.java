package com.upd.auth.controller;

import com.upd.auth.entity.Menu;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 后台登陆控制器
 * @author cao.xin
 * 2016年12月30日
 */
@Controller
@RequestMapping("back/admin")
public class LoginController extends BaseController {
	@Autowired
	private OperatorService operatorService;
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response){
		 Captcha.create(request,response);
	}
	
	/**
	 * 登录操作
	 * @param operator
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(Operator operator, HttpSession session,HttpServletRequest request){
		if(Captcha.validate(request)&&operator.getAccount() !=null && operator.getPassword() != null){
			operator = operatorService.doLogin(operator);
			if(operator != null){
				//登录成功，进入首页
				session.setAttribute("logedOperator", operator);
				return new RestResult("登录",null).toString();
			}
		}
		throw new BusinessException(RestErrorCode.PARAM,"用户名或密码错误！");
	}
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("/changePwd")
	public String changePwd(){
		return "login/changePwd";
	}
	
	/**
	 * 修改密码操作
	 * @param password
	 * @return
	 */
	@RequestMapping("/ajax/changePwd")
	@ResponseBody
	public String changePwd(String password, String oldPwd, HttpSession session){
		Operator operator = (Operator) session.getAttribute("logedOperator");
		boolean flag = operatorService.changePwd(operator.getId(),password, oldPwd);
		return new RestResult("修改密码操作",flag).toString();
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		//清除登录信息
		System.out.println("======================");
		session.removeAttribute("logedOperator");
		return "redirect:/login.html";
	}
	
	/**
	 * 进入首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpSession session, Model model){
		//获取登录信息
		Operator operator = (Operator) session.getAttribute("logedOperator");
		//获取菜单
		if(null!=operator.getRole()) {
			List<Menu> menuList = operator.getRole().getMenuList();
            //菜单排序
            Collections.sort(menuList, new Comparator<Menu>() {
                @Override
                public int compare(Menu menu1, Menu menu2) {
                    return menu1.getRank() - menu2.getRank();
                }
            });
            System.out.println("menu:"+menuList.size());
            model.addAttribute("menuList", menuList);
		}
		return "index";
	}
	
	/**
	 * 进入欢迎页
	 * @return
	 */
	@RequestMapping("/welcome")
	public String welcome(){
		return "login/welcome";
	}

}
