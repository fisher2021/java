package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.Appraisement;
import com.upd.business.entity.Choice;
import com.upd.business.entity.User;
import com.upd.business.form.AppraisementForm;
import com.upd.business.form.ChoiceForm;
import com.upd.business.service.AppraisementService;
import com.upd.business.service.ChoiceService;
import com.upd.business.service.UserService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.StringUtil;
import com.upd.common.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 民主评议控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/appr")
public class AppraisementController extends BaseController {
    @Autowired
    AppraisementService appraisementService;
    @Autowired
    ChoiceService choiceService;
    @Autowired
    UserService userService;
    /**
     * 获取民主评议列表
     * @param token
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestHeader("token") String token, Integer id) {
        List<Appraisement> appraisements = new ArrayList<>();
        if (id != null){
            appraisements = appraisementService.search(id);
        }else {
            JWT jwt= TokenUtils.verify(token);
            Claim userId= jwt.getHeaderClaim("id");
            //查看本组织的，或者党组指定的，将本用户已经投过的过滤掉
            appraisements = appraisementService.searchByOrg(userId.asInt());
        }
        return  new RestResult("民主评议列表",appraisements).toString();
    }

    /**
     * 获取民主评议选项列表
     * @param form
     * @return
     */
    @RequestMapping("/choice")
    @ResponseBody
    public String choice(ChoiceForm form) {
        List<Choice> choices = choiceService.search(form);
        return  new RestResult("民主评议选项列表",choices).toString();
    }

    /**
     * 提交民主评议选项
     * @param token
     * @param ids
     * @param results
     * @param apprId
     * @return
     */
    @RequestMapping("/commit")
    @ResponseBody
    public String commit(@RequestHeader("token") String token, @RequestParam(value = "ids") Integer[] ids, @RequestParam(value = "results") Integer[] results,@RequestParam int apprId) {
        //设置选项票数
        for (int i=0;i< ids.length;i++) {
            Choice choice = choiceService.get(ids[i]);
            String evaluates = choice.getEvaluate();
            String[] evas = evaluates.split(",");
            Integer count = Integer.parseInt(evas[results[i]-1]);
            ++count;
            String e = count.toString();
            System.out.println(e);
            evas[results[i]-1] = e;
            choice.setEvaluate(StringUtil.arrayToString(evas));
            choiceService.update(choice);
        }
        //设置已投票
        Appraisement appraisement = appraisementService.get(apprId);
        String users = (null==appraisement.getVoter())?"":appraisement.getVoter()+",";
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        appraisement.setVoter(users+id.asInt());
        appraisementService.update(appraisement);
        return  new RestResult("提交民主评议选项",null).toString();
    }
}
