package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.entity.Score;
import com.upd.business.entity.User;
import com.upd.business.form.ExamForm;
import com.upd.business.service.ExamService;
import com.upd.business.service.ScoreService;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

/**
 * 考试接口
 * Created by Administrator on 2017/5/23.
 */
@Controller
@RequestMapping("rest/exam")
public class ExamController extends BaseController{

    @Autowired
    ExamService examService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    OperatorService operatorService;

    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token,Pagination page, ExamForm form) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        String unitCode= jwt.getHeaderClaim("unitCode").asString();
        form.setUser(id.asInt());
        examService.restPage(page,form,unitCode);
        PageVo pageVo=new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        pageVo.setList(page.getList());
        return  new RestResult("考试",pageVo).toString();
    }

    @RequestMapping("/info/{id}")
    @ResponseBody
    public String page(@PathVariable("id") Integer id) {
        return  new RestResult("试题",examService.get(id).getQuestions()).toString();
    }

    @RequestMapping("/scorePage")
    @ResponseBody
    public String scorePage(@RequestHeader("token") String token, Pagination page,ExamForm form) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        form.setUser(id.asInt());//查询我的成绩
        scoreService.page(page,form);
        PageVo pageVo=new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        pageVo.setList(page.getList());
        return  new RestResult("成绩列表",pageVo).toString();
    }

    @RequestMapping("/scoreSave")
    @ResponseBody
    public String scoreSave(@RequestHeader("token") String token,Score score){
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user=new User();
        user.setId(id.asInt());
        score.setUser(user);
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        score.setOperator(operator);
        scoreService.save(score);
        return  new RestResult("新增成绩",null).toString();
    }

}
