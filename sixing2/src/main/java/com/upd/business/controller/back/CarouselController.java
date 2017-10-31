package com.upd.business.controller.back;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.upd.auth.entity.Operator;
import com.upd.business.entity.Article;
import com.upd.business.entity.Carousel;
import com.upd.business.entity.FileSave;
import com.upd.business.form.ArticleForm;
import com.upd.business.form.CarouselForm;
import com.upd.business.service.CarouselService;
import com.upd.business.service.FileSaveService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.business.vo.CarouselVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 轮播图后台控制器
 * @author cao.xin
 * 2017年2月9日
 */
@Controller("backCarouselController")
@RequestMapping("/back/carousel")
public class CarouselController extends BaseController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    FileSaveService fileSaveService;

    /**
     * 分页条件查询轮播图列表
     * @param page
     * @param form
     * @return
     */
    @RequestMapping("/list")
    public String carouselList(Pagination page, Model model, CarouselForm form){
        carouselService.getCarouselListByPage(page,form);
        model.addAttribute("page", page);
        model.addAttribute("form", form);
        return "/carousel/list";
    }

    /**
     * 跳转到轮播图添加或修改页面
     * @param id
     * @return
     */
    @RequestMapping("/addOrEdit")
    public String addOrEditCarouselUI(Integer id, Model model){
        if(id != null){
            Carousel carousel = carouselService.get(id);
            model.addAttribute("carousel", carousel);
        }
        return "carousel/addOrEdit";
    }

    /**
     * 添加或修改轮播图
     * @param carousel
     * @return
     */
    @RequestMapping("/ajax/addOrEdit")
    @ResponseBody
    public String addOrEditCarousel(Carousel carousel, MultipartHttpServletRequest request,HttpSession session) throws IOException {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        //图片上传处理
        MultipartFile file = request.getFile("image");
        if(file != null){
            String folder = "/carousel/";
            String image = fileSaveService.add(file,folder).getUrl();
            carousel.setImageUrl(image);
        }
        if (carousel.getId() == null){
            carousel.setOperator(operator);
            carouselService.add(carousel);
        }else {
            carouselService.edit(carousel);
        }
        return new RestResult("添加或修改轮播图",null).toString();

    }

    /**
     * 删除轮播图
     * @param carousel
     * @return
     */
    @RequestMapping("/ajax/delete")
    @ResponseBody
    public String deleteCarousel(Carousel carousel){
        carouselService.delete(carousel);
        return new RestResult("删除轮播图",null).toString();
    }

    /**
     * 批量删除轮播图
     * @param ids
     * @return
     */
    @RequestMapping("/ajax/deleteBatch")
    @ResponseBody
    public String deleteBatchCarousel(String ids){
        carouselService.deleteBatch(ids);
        return new RestResult("批量删除轮播图",null).toString();
    }
    /**
     * 轮播图数量验证
     * @param type
     * @return
     */
    @RequestMapping("/ajax/count")
    @ResponseBody
    public String userInfoOnly(Integer type){
        boolean flag = carouselService.count(type);
        return new RestResult("轮播图数量验证",flag).toString();
    }

}

