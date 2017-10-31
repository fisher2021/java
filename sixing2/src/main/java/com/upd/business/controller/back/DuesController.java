package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.PartyMembershipDues;
import com.upd.business.form.PartyMembershipDuesForm;
import com.upd.business.service.PartyMembershipDuesService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  党费
 * Created by Administrator on 2017/5/31.
 */
@Controller("backDuesController")
@RequestMapping("/back/dues")
public class DuesController extends BaseController {
    @Autowired
    PartyMembershipDuesService partyMembershipDuesService;

    /**
     * 分页查询党费
     * @param page
     * @param form
     * @param model
     * @return
     */
    @RequestMapping("/page")
    public String page(Pagination page, PartyMembershipDuesForm form, Model model, HttpSession session){
        //党支部的管理员只能查看本支部的用户党费
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        partyMembershipDuesService.page(page,form);
        model.addAttribute("form", form);
        model.addAttribute("page", page);
        //总人数，已缴费总数
        Map pay= (Map) partyMembershipDuesService.findOne("select new map(sum(amount) as amount,sum(feeReceived) as feeReceived)  From PartyMembershipDues WHERE "+form.toQueryDescription(),form.values());
        model.addAttribute("pay", pay);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
        return "dues/page";
    }

    /**
     * 跳转到批量添加党费页面
     * @return
     */
    @RequestMapping("/addBatch")
    public String addBatch(){
        return "dues/upload";
    }

    /**
     * 批量添加党费
     * @return
     */
    @RequestMapping("/ajax/addBatch")
    @ResponseBody
    public String addBatch(MultipartHttpServletRequest request,Integer type,String year) throws IOException, InvalidFormatException {
        //excel上传处理
        MultipartFile file = request.getFile("file");
        String message = "";
        if (file != null){
            message = partyMembershipDuesService.addBatch(file,type,year);
        }else {
            message = "请上传文件！";
        }
        return new RestResult("批量添加党费",message).toString();
    }

    /**
     * 跳转到党费添加或修改页面
     * @param id
     * @return
     */
    @RequestMapping("/addOrEdit")
    public String addOrEdit(Integer id, Model model){
        if(id != null){
            PartyMembershipDues dues = partyMembershipDuesService.get(id);
            model.addAttribute("dues", dues);
        }
        return "dues/addOrEdit";
    }

    /**
     * 修改党费
     * @param partyMembershipDues
     * @return
     */
    @RequestMapping("/ajax/addOrEdit")
    @ResponseBody
    public String addOrEdit(PartyMembershipDues partyMembershipDues) {
        if (partyMembershipDues.getId() != null){
            partyMembershipDuesService.edit(partyMembershipDues);
        }
        return new RestResult("添加或修改党费",null).toString();
    }

    /**
     * 删除党费
     * @param ids
     * @return
     */
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String delete(@PathVariable("ids") String ids){
        partyMembershipDuesService.deletemore(ids);
        return new RestResult("党费删除",null).toString();
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping("/export")
    @ResponseBody
    public String export(HttpServletResponse response, Integer orgId,String month, Boolean status){
        partyMembershipDuesService.export(response,orgId,month,status);
        return new RestResult("导出",null).toString();
    }
}
