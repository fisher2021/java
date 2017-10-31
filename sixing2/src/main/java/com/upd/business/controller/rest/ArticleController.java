package com.upd.business.controller.rest;

/**
 * Created by ljw on 2017/5/3.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.constant.BacklogType;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.Article;
import com.upd.business.entity.Backlog;
import com.upd.business.entity.BacklogUser;
import com.upd.business.entity.User;
import com.upd.business.form.ArticleForm;
import com.upd.business.service.*;
import com.upd.business.vo.ArticleVo;
import com.upd.business.vo.DictVo;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.entity.Dict;
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

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 文章控制器
 * Created by ljw on 2017/5/3.
 */
@Controller
@RequestMapping("rest/article")
public class ArticleController extends BaseController {

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    DictService dictService;

    //获取Article列表
    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token,Pagination page, ArticleForm form) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        User user = userService.get(userId.asInt());
        //拦截党支部的用户查看通知公告的党支部，三会一课，民主生活会
        String type = form.getType();
        if (user.getHighestOrg().getLevel() == ORGType.PARTY_BRANCH && (type.equals("dzb") || type.equals("mzhytz") || type.equals("mzhyjy") ||
                type.equals("dyhytz") || type.equals("dyhyjy") || type.equals("dzbhytz") || type.equals("dzbhyjy")||
                type.equals("dxzhytz") || type.equals("dxzhyjy")|| type.equals("dkjy") || type.equals("wdkxy"))){
            articleService.pageByOrg(page,form,user.getHighestOrg().getId());
        }else {
            articleService.page(page,form);
        }
        List<Article> ls=  page.getList();
        List result = new ArrayList<Article>();
        if (ls != null){
            for(Article article:ls){
                ArticleVo vo = new ArticleVo();
                BeanUtils.copyProperties(vo,article);
                result.add(vo);
            }
        }
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        pageVo.setList(result);
        return  new RestResult("文章列表",pageVo).toString();
    }

    //获取Article详情
    @RequestMapping("/info/{id}")
    public String info(@RequestHeader("token") String token,@PathVariable("id") Integer id, Model model) throws InvocationTargetException, IllegalAccessException {
        Article article = articleService.get(id);
        Integer count = article.getCount();
        article.setCount(++count);
        articleService.update(article);
        model.addAttribute("info",article);
        if (article.getType().getDictId().equals("gzgf")){
            model.addAttribute("maximum",2);
            model.addAttribute("user",1);
        }else {
            model.addAttribute("maximum",1);
            model.addAttribute("user","no");
        }
        if (article.getTargetOut()){
            String url = article.getTargetUrl();
            return "redirect:"+url;
        }else {
            return  "article/detail";
        }
    }
    /**
     * 获取新闻动态栏目
     * @return
     */
    @RequestMapping("/getColumns")
    @ResponseBody
    public String getColumns()  {
        List<Dict> dicts = dictService.getDictListByParentId(55);
        //排序
        Collections.sort(dicts, new Comparator<Dict>() {
            @Override
            public int compare(Dict a, Dict b) {
                return a.getSort() - b.getSort();
            }
        });
        List<DictVo> result = new ArrayList<>();
        if (dicts != null && dicts.size() !=0){
            for (Dict dict : dicts){
                DictVo vo = new DictVo();
                vo.setDictId(dict.getDictId());
                vo.setDictName(dict.getDictName());
                result.add(vo);
            }
        }
        return  new RestResult("获取新闻动态栏目",result).toString();
    }
}
