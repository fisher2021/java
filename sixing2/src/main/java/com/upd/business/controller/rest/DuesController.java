package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.form.PartyMembershipDuesForm;
import com.upd.business.service.PartyMembershipDuesService;
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
 *  党费
 * Created by Administrator on 2017/5/31.
 */
@Controller
@RequestMapping("/rest/dues")
public class DuesController extends BaseController {

    @Autowired
    PartyMembershipDuesService partyMembershipDuesService;

    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token, Pagination page, PartyMembershipDuesForm form) throws InvocationTargetException, IllegalAccessException {
        if(form.isMine()){
            JWT jwt= TokenUtils.verify(token);
            Claim id= jwt.getHeaderClaim("id");
            form.setUser(id.asInt());
            form.setStatus(false);
        }
        partyMembershipDuesService.page(page,form);
        PageVo pageVo=new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        pageVo.setList(page.getList());
        return new RestResult("党费列表",pageVo).toString();
    }

}
