package com.upd.business.controller.rest;

import com.upd.business.entity.Carousel;
import com.upd.business.form.CarouselForm;
import com.upd.business.service.CarouselService;
import com.upd.business.vo.CarouselVo;
import com.upd.common.basis.rest.RestResult;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 轮播图控制器
 * Created by ljw on 2017/5/2.
 */
@Controller
@RequestMapping("rest/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;
    /**
     * 分页条件查询轮播图列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(CarouselForm form) throws InvocationTargetException, IllegalAccessException {
        List<Carousel> carousels = carouselService.getCarouselList(form);
        List<CarouselVo> carousel = new ArrayList<>();//轮播图
        List<CarouselVo> ztxx = new ArrayList<>();//专题学习
        CarouselVo ztxxVo = new CarouselVo();
        for (Carousel c:carousels) {
            CarouselVo vo = new CarouselVo();
            BeanUtils.copyProperties(vo,c);
            carousel.add(vo);
            if (carousel.size() == 5){
                break;
            }
        }
        //如果是党建要闻同时返回专题学习
        if (form.getType().equals("djyw") ){
            Carousel carousel1 = carouselService.getZTXX();
            if (carousel1 != null){
                BeanUtils.copyProperties(ztxxVo,carousel1);
                ztxx.add(ztxxVo);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("carousel",carousel);
        map.put("ztxx",ztxx);
        return new RestResult("轮播图列表",map).toString();
    }

    /**
     * 获取轮播图详情
     * @param token
     * @param id
     * @param model
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/info/{id}")
    public String info(@RequestHeader("token") String token, @PathVariable("id") Integer id, Model model) throws InvocationTargetException, IllegalAccessException {
        Carousel carousel = carouselService.get(id);
        model.addAttribute("info",carousel);
        model.addAttribute("maximum",1);//设置页面不能放大
        model.addAttribute("user","no");
        if (carousel.getTargetOut()){
            String url = carousel.getTargetUrl();
            return "redirect:"+url;
        }else {
            return  "article/detail";
        }
    }

}
