package com.upd.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.upd.auth.entity.Menu;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.MenuService;
import com.upd.business.entity.ORG;
import com.upd.business.vo.ZtreeVo;
import com.upd.common.basis.rest.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 菜单后台管理控制器
 * @author cao.xin
 * 2016年12月29日
 */
@Controller
@RequestMapping("/back/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	/**
	 * 菜单列表页面
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public RestResult menuList(Model model, HttpSession session){
		//获取所有目录
		List<Menu> menuList = menuService.findAll();
		List<ZtreeVo> ztreeVos=new ArrayList<ZtreeVo>();
		//Integer id, Integer pId, String name, boolean open, boolean isParent, String _href
		ztreeVos.add(new ZtreeVo(0,0,"菜单目录",true,true,"../../back/menu/children?id=0"));
		for(Menu menu:menuList){
			//除admin账号外 不能看到全部账号地址
			Operator operator = (Operator)session.getAttribute("logedOperator");
			if (!"admin".equals(operator.getAccount()) && menu.getIsShowAll()==1){
				continue;
			}
			ztreeVos.add(tree(menu));
		}
		return new RestResult("菜单列表",ztreeVos);
	}

	private ZtreeVo tree(Menu menu){
		ZtreeVo vo=new ZtreeVo();
		vo.setId(menu.getId());
		vo.setName(menu.getName());
		if(null!=menu.getParent()){
			vo.setpId(menu.getParent().getId());
		}else{
			vo.setpId(0);
			vo.setOpen(false);
		}
		vo.set_href("../../back/menu/children?id="+menu.getId());
		return vo;
	}
	
	/**
	 * 子菜单列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/children")
	public String children(Menu menu, Model model){
		//查询子菜单
		List<Menu> children = menuService.getChildren(menu);
		model.addAttribute("children", children);
		
		return "menu/children";
	}
	
	/**
	 * 跳转新增或编辑菜单页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEditMenu(Integer id, Integer parentId, Model model){
		if(id !=null){
			Menu menu = menuService.get(id);
			model.addAttribute("menu", menu);
		}
		if(parentId == null){
			parentId = 0;
		}
		model.addAttribute("parentId", parentId);
		
		return "menu/addOrEdit";
	}
	
	
	/**
	 * 新增或编辑菜单
	 * @param menu 
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEditMenu(Menu menu, HttpSession session){
		menuService.addOrEditMenu(menu);
		return new RestResult("新增或编辑菜单信息",null).toString();
	}
	
	/**
	 * 删除菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String deleteMenu(Menu menu, HttpSession session){
		menuService.delete(menu);
		return new RestResult("删除菜单信息",null).toString();
	}
	
}
