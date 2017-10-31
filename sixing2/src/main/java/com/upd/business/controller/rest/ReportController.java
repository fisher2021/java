package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.entity.Report;
import com.upd.business.entity.User;
import com.upd.business.form.ReportForm;
import com.upd.business.service.ReportService;
import com.upd.business.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

/**
 * 思想汇报控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/report")
public class ReportController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    OperatorService operatorService;

    /**
     * 获取思想汇报列表
     * @param page
     * @param form 用户ID
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token, Pagination page, @RequestParam Boolean mine,ReportForm form) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        if (mine){
            form.setUserId(id.asInt());
            reportService.page(page,form);
        }else{
            //查阅列表,根据用户最高等级，只有领导可以查看本组成员以及下级领导
            User user=userService.get(id.asInt());
//            String hql="FROM Report WHERE user.org.parent.id=? AND user.leader=? OR user.org.id=? AND user.id!=? order by createTime desc";
//            reportService.findbypage(page,hql,user.getOrg().getId(),user.getLeader(),user.getOrg().getId(),user.getId());
            String hql="FROM Report WHERE user.highestOrg.parent.id=? AND user.leader=? OR user.highestOrg.id=? AND user.id!=? order by createTime desc";
            Integer orgId = user.getHighestOrg().getId();
            if (user.getOrgs() != null && user.getOrgs().size() != 0){
                reportService.findbypage(page,hql,orgId,user.getLeader(),orgId,user.getId());
            }
        }
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("思想汇报内容列表",pageVo).toString();
    }

    /**
     * 提交思想汇报
     * @param token
     * @param report
     * @param backlogId
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestHeader("token") String token, Report report,Integer backlogId  ){
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user=userService.get(id.asInt());
        report.setUser(user);
        report.initTime();
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        report.setOperator(operator);
        reportService.save(report);
        return  new RestResult("提交思想汇报",null).toString();
    }
}
