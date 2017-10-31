package com.upd.auth.controller;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpSession;

import com.upd.auth.entity.Operator;
import com.upd.auth.entity.Role;
import com.upd.auth.service.OperatorService;
import com.upd.auth.service.RoleService;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.IntegralSetting;
import com.upd.business.entity.ORG;
import com.upd.business.form.OperatorForm;
import com.upd.business.service.DictService;
import com.upd.business.service.IntegralSettingService;
import com.upd.business.service.ORGService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.queryParameter.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理员后台管理控制器
 * @author cao.xin
 * 2016年12月30日
 */
@Controller
@RequestMapping("/back/operator")
public class OperatorController extends BaseController{
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private DictService dictService;
	@Autowired
	private IntegralSettingService integralSettingService;
	@Autowired
    private ORGService orgService;
	/**
	 * 分页条件查询管理员账号
	 * @param page
	 * @param
	 * @return
	 */
	@RequestMapping("/list")
	public String operatorList(Pagination page, Model model, OperatorForm form,HttpSession session){
		Operator logedOpe = (Operator) session.getAttribute("logedOperator");
		if(logedOpe.getRole().getId()!=1) {
			String unitCode = getOperatorUnitCode(null);
			form.setOrgCode(unitCode);
		}
		operatorService.page(page, form);

		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "operator/list";
	}
	
	/**
	 * 跳转到新增或编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEditOperator(Integer id, Model model,HttpSession session){
		if(id != null){
			Operator operator = operatorService.get(id);
			model.addAttribute("operator", operator);
		}
		Operator logedOpe = (Operator) session.getAttribute("logedOperator");
		List<Role> roleList = roleService.findAll();
		//获取所有角色列表
		if(logedOpe.getRole().getId()!=1) {
			String unitCode = logedOpe.getOrg().getCode().substring(0,ORGType.SYS_DEFAULT.getCodeLength()
					+ ORGType.UNIT.getCodeLength())+"%";

			roleList = roleService.find(new QueryParam("operator.org.code",unitCode,"like"));
		}

		model.addAttribute("roleList", roleList);
		
		return "operator/addOrEdit";
	}
	
	/**
	 * 新增或保存管理员信息
	 * @param operator
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEditOperator(final Operator operator, HttpSession session){
        if (operator.getId() == null){
            operatorService.addOperator(operator);
			//如果添加的是单位 新增积分配置
            ORG org = orgService.get(operator.getOrg().getId()) ;
			if(org.getLevel().equals(ORGType.UNIT)){
				taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						List<Dict> dicts = dictService.getDictListByTypeId("points");
						Dict standard = dictService.getDictByDictId("standard");//达标积分
						IntegralSetting parent = integralSettingService.save(standard, operator, null);
						for (Dict dict : dicts) {
							if("standard".equals(dict.getDictId())){
								continue;
							}

							integralSettingService.save(dict, operator, parent);
						}
					}
				});
			}
        }else {
            operatorService.editOperator(operator);
        }

		return new RestResult("新增或保存管理员信息",null).toString();
	}
	
	/**
	 * 删除管理员
	 * @param operator
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String deleteOperator(Operator operator){
		operator = operatorService.get(operator.getId());
		operator.setDeleted(1);
		operatorService.update(operator);
		return new RestResult("删除管理员",null).toString();
	}
	
	/**
	 * 跳转重置密码页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(Integer id, Model model){
		if(id != null){
			Operator operator = operatorService.get(id);
			model.addAttribute("operator", operator);
		}
		return "operator/resetPwd";
	}
	
	/**
	 * 重置管理员密码
	 * @param operator
	 * @return
	 */
	@RequestMapping("/ajax/resetPwd")
	@ResponseBody
	public String resetPwd(Operator operator){
		operatorService.resetPwd(operator.getId(), operator.getPassword());
		return new RestResult("重置管理员密码",null).toString();
	}
	
	/**
	 * 账号唯一性验证
	 * @param operator
	 * @return
	 */
	@RequestMapping("/ajax/only")
	@ResponseBody
	public String operatorOnly(Operator operator){
		boolean flag = operatorService.operatorOnly(operator);
		return new RestResult("账号唯一性验证",flag).toString();
	}
}
