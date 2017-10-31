package com.upd.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.upd.auth.entity.Menu;
import com.upd.auth.entity.Operator;
import com.upd.auth.entity.Role;
import com.upd.auth.service.MenuService;
import com.upd.auth.service.RoleService;
import com.upd.business.form.RoleForm;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色后台管理控制器
 * @author cao.xin
 * 2016年12月29日
 */
@Controller
@RequestMapping("/back/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	
	/**
	 * 角色列表页面
	 * @param page
	 * @param form
	 * @return
	 */
	@RequestMapping("/list")
	public String roleList(Pagination page, Model model, RoleForm form){
		roleService.page(page,form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		
		return "role/list";
	}
	
	/**
	 * 跳转到添加或编辑角色页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEditRole(Integer id, Model model){
		if(id != null){
			Role role = roleService.get(id);
			model.addAttribute("role", role);
		}
		return "role/addOrEdit";
	}
	
	/**
	 * 添加或编辑角色
	 * @param role
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public  String addOrEditRole(Role role, HttpSession session){
		roleService.addOrEditRole(role);
		return new RestResult("添加或编辑角色信息",null).toString();
	}
	
	/**
	 * 跳转到角色权限编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/editRoleMenu")
	public String editRoleMenu(Integer id, Model model,HttpSession session){
		Role role = roleService.getByEmerg(id);
		model.addAttribute("role", role);
		//获取菜单列表
		//除admin账号外 不能看到全部账号地址
		Operator operator = (Operator)session.getAttribute("logedOperator");
		List<Menu> menuList = menuService.findAll();
		List<Menu> menuList_new = new ArrayList<>();
		for (Menu menu : menuList){
			if (!"admin".equals(operator.getAccount()) && menu.getIsShowAll()==1){
				continue;
			}
			menuList_new.add(menu);
		}

		model.addAttribute("menuList", menuList_new);
		return "role/editRoleMenu";
	}
	
	/**
	 * 角色权限编辑
	 * @param id
	 * @param menus
	 * @return
	 */
	@RequestMapping("/ajax/editRoleMenu")
	@ResponseBody
	public String editRoleMenu(Integer id, String menus, HttpSession session){
		roleService.editRoleMenu(id, menus);
		return new RestResult("角色权限编辑",null).toString();

	}
	
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String deleteRole(Role role, HttpSession session){
		roleService.delete(role);
		return new RestResult("删除角色信息",null).toString();
	}
}
