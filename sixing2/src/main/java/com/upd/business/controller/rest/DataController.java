package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.entity.Data;
import com.upd.business.entity.DataUser;
import com.upd.business.entity.FileSave;
import com.upd.business.service.DataService;
import com.upd.business.service.DataUserService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 资料下载控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/data")
public class DataController extends BaseController {
    @Autowired
    DataUserService dataUserService;
    @Autowired
    UserService userService;
    /**
     * 获取列表
     * @param page
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token,Pagination page) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        dataUserService.pageByUser(page,userId.asInt());
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("资料下载列表",pageVo).toString();
    }

}
