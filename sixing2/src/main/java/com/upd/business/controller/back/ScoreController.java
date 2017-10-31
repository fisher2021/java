package com.upd.business.controller.back;

import com.alibaba.fastjson.JSON;
import com.upd.auth.entity.Operator;
import com.upd.business.constant.ExamType;
import com.upd.business.constant.ORGType;
import com.upd.business.form.ScoreForm;
import com.upd.business.service.ScoreService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/27.
 */
@Controller
@RequestMapping("back/score")
public class ScoreController extends BaseController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping("/page")
    public String page(Pagination page, ScoreForm form, Model model, HttpSession session){
        //党支部的管理员只能查看本支部的用户成绩
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        scoreService.page(page,form);
        //合格数
        String passHql="SELECT count(DISTINCT user.id,exam.id) FROM Score WHERE pass=1 and "+form.toQueryDescription();
        Map count=scoreService.passTotal(form);
        System.out.println(JSON.toJSONString(count));
        model.addAttribute("count",count);
        model.addAttribute("type", ExamType.values());
        model.addAttribute("form", form);
        model.addAttribute("page", page);
        return "exam/score/page";
    }


}
