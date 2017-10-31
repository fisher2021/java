package com.upd.business.controller.rest;

/**
 * Created by ljw on 2017/5/3.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.service.BacklogService;
import com.upd.business.service.BacklogUserService;
import com.upd.business.service.TrackService;
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

/**
 * 党迹控制器
 * Created by ljw on 2017/5/3.
 */
@Controller
@RequestMapping("rest/track")
public class TrackController extends BaseController {

    @Autowired
    TrackService trackService;

    /**
     * 获取党迹列表
     * @param page
     * @param token
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(Pagination page,@RequestHeader("token") String token) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        trackService.page(page,userId.asInt());
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("个人党迹",pageVo).toString();
    }
}
