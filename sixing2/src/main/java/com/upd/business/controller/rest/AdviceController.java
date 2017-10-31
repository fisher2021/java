package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.entity.Advice;
import com.upd.business.entity.JobBack;
import com.upd.business.entity.User;
import com.upd.business.form.AdviceForm;
import com.upd.business.service.AdviceService;
import com.upd.business.service.UserService;
import com.upd.business.vo.AdviceVo;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 意见反馈控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/advice")
public class AdviceController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    AdviceService adviceService;
    @Autowired
    OperatorService operatorService;

    /**
     * 获取意见反馈列表
     * @param page
     * @param form
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(Pagination page, AdviceForm form) throws InvocationTargetException, IllegalAccessException {
        adviceService.page(page,form);
        List<Advice> ls = page.getList();
        List result = new ArrayList();
        for (Advice advice:ls) {
            AdviceVo vo = new AdviceVo();
            BeanUtils.copyProperties(vo,advice);
            result.add(vo);
        }
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        pageVo.setList(result);
        return  new RestResult("意见反馈列表",pageVo).toString();
    }

    /**
     * 提交意见反馈
     * @param token
     * @param advice
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestHeader("token") String token, Advice advice ){
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user=userService.get(id.asInt());
        advice.setUser(user);
        advice.initTime();
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        advice.setOperator(operator);
        adviceService.save(advice);
        return  new RestResult("提交意见反馈",null).toString();
    }
}
