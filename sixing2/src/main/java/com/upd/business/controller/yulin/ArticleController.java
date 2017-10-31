package com.upd.business.controller.yulin;

/**
 * Created by ljw on 2017/5/3.
 */

import com.upd.business.constant.ORGType;
import com.upd.business.entity.Article;
import com.upd.business.entity.User;
import com.upd.business.form.ArticleForm;
import com.upd.business.service.ArticleService;
import com.upd.business.service.UserService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.business.vo.ArticleVo;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章控制器
 * Created by ljw on 2017/5/3.
 */
@Controller("yulinArticleController")
@RequestMapping("yulin/article")
public class ArticleController extends BaseController {

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    //获取Article列表
    @RequestMapping("/page")
    @ResponseBody
    public String page(Pagination page, ArticleForm form, String callback) throws InvocationTargetException, IllegalAccessException {
        int userId = Integer.valueOf(OtherConfigBundle.getConfig("yulin_user_id"));
        User user = userService.get(userId);
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
        return  callback + "("+new RestResult("文章列表",pageVo).toString()+")";
    }

    //获取Article详情
    @RequestMapping("/info/{id}")
    public String info(@PathVariable("id") Integer id, Model model) throws InvocationTargetException, IllegalAccessException {
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
}
