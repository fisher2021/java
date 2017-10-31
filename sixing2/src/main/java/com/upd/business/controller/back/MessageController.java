package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.Message;
import com.upd.business.entity.ORG;
import com.upd.business.form.MessageForm;
import com.upd.business.form.ORGForm;
import com.upd.business.service.MessageService;
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
import java.io.IOException;
import java.util.List;

/**
 * 系统消息管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backMessageController")
@RequestMapping("/back/message")
public class MessageController extends BaseController {
	@Autowired 
	private MessageService messageService;
    @Autowired
    ORGService orgService;
	
	/**
	 * 分页条件查询系统消息列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, MessageForm form, HttpSession session){
        //获取登录信息,支部管理员只能查看本支部的系统消息
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		messageService.page(page, form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "message/page";
	}
	/**
	 * 跳转到系统消息详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public String info(Integer id, Model model){
		Message message = messageService.get(id);
		model.addAttribute("message", message);
        if (message.getOrgList() != null && !message.getOrgList().equals("")){
            List<String> orgs = orgService.getOrgListName(message.getOrgList());
            model.addAttribute("orgs", orgs);
        }
		return "message/info";
	}

	/**
	 * 删除系统消息
	 * @param message
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Message message){
		messageService.delete(message);
		return new RestResult("删除系统消息",null).toString();
	}

	/**
	 * 批量删除系统消息
	 * @param ids
	 * @return
	 */
	@RequestMapping("/ajax/deleteBatch")
	@ResponseBody
	public String deleteBatch(String ids){
		if(StringUtils.isNotBlank(ids)){
			messageService.deleteBatch(ids);
		}
		return new RestResult("批量删除系统消息",null).toString();
	}

    /**
     * 跳转到系统消息添加或修改页面
     * @param id
     * @return
     */
    @RequestMapping("/addOrEdit")
    public String addOrEdit(Integer id, Model model,HttpSession session){
        if(id != null){
            Message message = messageService.get(id);
            model.addAttribute("vote", message);
        }
        //获取当前用户的下级组织
        Operator operator = (Operator) session.getAttribute("logedOperator");
        model.addAttribute("orgList", orgService.getChildrenByCode(operator.getOrg().getCode()));
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
        return "message/addOrEdit";
    }

    /**
     * 添加或修改系统消息
     * @param message
     * @return
     */
    @RequestMapping("/ajax/addOrEdit")
    @ResponseBody
    public String addOrEdit(Message message, HttpSession session,Integer[] orgs) throws IOException {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        message.setOrgId(operator.getOrg().getId());
        message.setOrgList(orgService.getOrgList(orgs));
        if (message.getId() == null){
            message.setOperator(operator);
            messageService.add(message);
            messageService.push(message,orgs);
        }
        return new RestResult("添加或修改系统消息",null).toString();
    }

    /**
     * 跳转到添加指派组织页面
     * @param page
     * @param model
     * @param form
     * @return
     */
    @RequestMapping("/orgPage")
    public String list(Pagination page, Model model, ORGForm form, HttpSession session){
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        orgService.pageForMessage(page,form);
        model.addAttribute("type", ORGType.values());
        model.addAttribute("page", page);
        model.addAttribute("form", form);
        model.addAttribute("messageId", form.getMessageId());
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
        return "message/orgPage";
    }
    /**
     * 批量添加指派组织
     * @param ids
     * @return
     */
    @RequestMapping("/ajax/addBatch")
    @ResponseBody
    public String addBatch(Integer messageId,Integer[] ids){
        if(ids.length != 0){
            messageService.addBatch(messageId,ids);
        }
        return new RestResult("批量添加指派组织",null).toString();
    }
    /**
     * 删除指派组织
     * @param id
     * @return
     */
    @RequestMapping("/ajax/deleteORG")
    @ResponseBody
    public String deleteORG(Integer messageId,Integer id){
        messageService.deleteORG(messageId,id);
        return new RestResult("删除指派组织",null).toString();
    }
}
