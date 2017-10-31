package com.upd.business.controller.back;

import com.upd.business.entity.Advice;
import com.upd.business.entity.ORG;
import com.upd.business.form.AdviceForm;
import com.upd.business.service.AdviceService;
import com.upd.business.service.ORGService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 意见反馈后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backAdviceController")
@RequestMapping("/back/advice")
public class AdviceController extends BaseController {
	@Autowired 
	private AdviceService adviceService;
	@Autowired
	private ORGService orgService;
	
	/**
	 * 分页条件查询意见反馈列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
    public String list(Pagination page, Model model, AdviceForm form){
        adviceService.page(page, form);
        model.addAttribute("page", page);
        model.addAttribute("form", form);
		return "advice/list";
	}

	/**
	 * 删除意见反馈
	 * @param advice
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Advice advice){
        adviceService.delete(advice);
        return new RestResult("删除意见反馈",null).toString();
	}

	
}
