package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.*;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 民主评议后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backAppraisementController")
@RequestMapping("/back/appr")
public class AppraisementController extends BaseController {
	@Autowired
	private AppraisementService appraisementService;
	@Autowired
	private ChoiceService choiceService;
	@Autowired
    private ORGService orgService;

	/**
	 * 分页条件查询民主评议列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, AppraisementForm form, HttpSession session){
        //党支部的管理员只能查看本支部的民主评议
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		appraisementService.page(page, form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "appr/page";
	}
	/**
	 * 跳转到民主评议详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public String details(Integer id, Model model){
		Appraisement appraisement = appraisementService.get(id);
        ChoiceForm form = new ChoiceForm(id);
        List<Choice> choices = choiceService.search(form);
		if (appraisement.getVoter() != null && !appraisement.getVoter().equals("")){
			String[] voters = appraisement.getVoter().split(",");
			model.addAttribute("count", voters.length);
		}else {
			model.addAttribute("count", 0);
		}
        if (appraisement.getOrgList() != null && !appraisement.getOrgList().equals("")){
            List<String> orgs = orgService.getOrgListName(appraisement.getOrgList());
            model.addAttribute("orgs", orgs);
        }
		model.addAttribute("choices", choices);
		return "appr/info";
	}

	/**
	 * 跳转到民主评议添加或修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model,HttpSession session){
		if(id != null){
			Appraisement appraisement = appraisementService.get(id);
			model.addAttribute("appr", appraisement);
			String[] orgs = appraisement.getOrgList().split(",");
            List<ORG> orgChecked = new ArrayList<>();
            for (String orgId:orgs){
			    ORG org = orgService.get(Integer.valueOf(orgId));
			    orgChecked.add(org);
            }
            model.addAttribute("orgChecked", orgChecked);
		}
		//获取当前用户的下级组织
        String code = getOperatorUnitCodeUnder(null);
		model.addAttribute("orgList", orgService.getChildrenByCode(code));
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "appr/addOrEdit";
	}

	/**
	 * 添加民主评议
	 * @param appraisement
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(Appraisement appraisement, String[] option, HttpSession session,Integer[] orgs) throws IOException {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        //如果不是党支部添加的文章，将组织ID都设置为0，方便展示给所以用户
        if (operator.getOrg().getLevel() != ORGType.PARTY_BRANCH){
            appraisement.setOrgId(0);
        }else {
            appraisement.setOrgId(operator.getOrg().getId());
        }
        if (orgs != null){
            appraisement.setOrgList(orgService.getOrgList(orgs));
        }
		if (appraisement.getId() == null){
            appraisement.setOperator(operator);//创建人
            appraisementService.add(appraisement);
			choiceService.add(appraisement,option);
		}
		return new RestResult("添加或修改民主评议",null).toString();
	}

	/**
	 * 删除民主评议
	 * @param id
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Integer id){
		Appraisement appraisement = appraisementService.get(id);
		appraisementService.delete(appraisement);
		return new RestResult("删除民主评议",null).toString();
	}

	
}
