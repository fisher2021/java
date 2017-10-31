package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.entity.AndroidIOSVesrion;
import com.upd.business.form.AndroidIOSVesrionForm;
import com.upd.business.service.AndroidIOSVesrionService;
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

/**
 * 安卓版本更新管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backAndroidVesrionController")
@RequestMapping("/back/appVersion")
public class AndroidIOSVesrionController extends BaseController {
	@Autowired 
	private AndroidIOSVesrionService androidIOSVesrionService;
	
	/**
	 * 安卓版本列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, AndroidIOSVesrionForm form){
		androidIOSVesrionService.page(page,form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "appVersion/page";
	}

	/**
	 * 删除
	 * @param androidVesrion
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(AndroidIOSVesrion androidVesrion){
		androidIOSVesrionService.delete(androidVesrion);
		return new RestResult("删除版本",null).toString();
	}

    /**
     * 跳转到添加或修改页面
     * @param id
     * @return
     */
    @RequestMapping("/addOrEdit")
    public String addOrEdit(Integer id, Model model){
        if(id != null){
            AndroidIOSVesrion androidIOSVesrion = androidIOSVesrionService.get(id);
            model.addAttribute("appVersion", androidIOSVesrion);
        }
        return "appVersion/addOrEdit";
    }

    /**
     * 添加或修改
     * @param androidIOSVesrion
     * @return
     */
    @RequestMapping("/ajax/addOrEdit")
    @ResponseBody
    public String addOrEdit(AndroidIOSVesrion androidIOSVesrion, HttpSession session) throws IOException {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        androidIOSVesrion.setOperator(operator);
        if (androidIOSVesrion.getId() == null){
            androidIOSVesrionService.add(androidIOSVesrion);
        }
        return new RestResult("添加或修改版本",null).toString();
    }

}
