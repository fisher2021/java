package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.ORG;
import com.upd.business.entity.Report;
import com.upd.business.form.ReportForm;
import com.upd.business.service.ORGService;
import com.upd.business.service.ReportService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.word.WordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 思想汇报后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backReportController")
@RequestMapping("/back/report")
public class ReportController extends BaseController {
	@Autowired 
	private ReportService reportService;
	@Autowired
	private ORGService orgService;
	
	/**
	 * 分页条件查询思想汇报列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Pagination page, Model model, ReportForm form, HttpSession session){
        //党支部的管理员只能查看本支部的用户工作展示
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		reportService.page(page, form);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "report/list";
	}
	/**
	 * 跳转到思想汇报详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/details")
	public String details(Integer id, Model model){
		Report report = reportService.get(id);
		model.addAttribute("report", report);
		return "report/details";
	}

	/**
	 * 删除思想汇报
	 * @param report
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Report report){
		reportService.delete(report);
		return new RestResult("删除思想汇报",null).toString();
	}

	/**
	 * 批量删除思想汇报
	 * @param ids
	 * @return
	 */
	@RequestMapping("/ajax/deleteBatch")
	@ResponseBody
	public String deleteBatch(String ids){
		if(StringUtils.isNotBlank(ids)){
			reportService.deleteBatch(ids);
		}
		return new RestResult("批量删除思想汇报",null).toString();
	}
    /**
     * 导出思想汇报Word
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/exportword")
    @ResponseBody
    public String export(HttpServletResponse response, Integer id) throws UnsupportedEncodingException {
        //获取导出数据
        Report report = reportService.get(id);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title",report.getSubject());
        dataMap.put("content",report.getContent());
        //导出文件
        String fileName="思想汇报.doc";
        WordUtil wordUtil = new WordUtil();
        String path = OtherConfigBundle.getConfig("upload_url")+"/doc/"+fileName;
        wordUtil.createDoc(dataMap,path,response,"report.ftl");
        return new RestResult("导出思想汇报",null).toString();
    }
	
}
