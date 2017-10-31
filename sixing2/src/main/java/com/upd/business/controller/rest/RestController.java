package com.upd.business.controller.rest;

import com.upd.business.service.ArticleService;
import com.upd.business.vo.ArticleVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hui on 2017/5/2.
 */
@Controller
@RequestMapping("/rest")
public class RestController extends BaseController{
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/test/{id}")
    @ResponseBody
    public String test(@PathVariable("id")Integer id,String type) throws InvocationTargetException, IllegalAccessException {
        System.out.println("========="+id);
        ArticleVo vo=new ArticleVo();
        BeanUtils.copyProperties(vo,articleService.get(id));
        return new RestResult("测试接口",vo).toString();
    }
}
