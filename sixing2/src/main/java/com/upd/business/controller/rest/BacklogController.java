package com.upd.business.controller.rest;

/**
 * Created by ljw on 2017/5/3.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.entity.Article;
import com.upd.business.entity.Backlog;
import com.upd.business.entity.BacklogUser;
import com.upd.business.form.ArticleForm;
import com.upd.business.form.BacklogForm;
import com.upd.business.service.ArticleService;
import com.upd.business.service.BacklogService;
import com.upd.business.service.BacklogUserService;
import com.upd.business.vo.ArticleVo;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 待办事项控制器
 * Created by ljw on 2017/5/3.
 */
@Controller
@RequestMapping("rest/backlog")
public class BacklogController extends BaseController {

    @Autowired
    BacklogUserService backlogUserService;

    /**
     * 获取待办事项列表
     * @param page
     * @param token
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(Pagination page,@RequestHeader("token") String token) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        backlogUserService.page(page,userId.asInt());
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("个人待办事项列表",pageVo).toString();
    }
}
