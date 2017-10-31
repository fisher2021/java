package com.upd.business.controller.rest;

import com.upd.business.entity.AndroidIOSVesrion;
import com.upd.business.service.AndroidIOSVesrionService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 安卓版本更新控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/version")
public class AndroidVesrionController extends BaseController {
    @Autowired
    AndroidIOSVesrionService androidIOSVesrionService;

    /**
     * 获取安卓更新版本
     * @return
     */
    @RequestMapping("/getVersion")
    @ResponseBody
    public String getVersion(Integer type){
        List<AndroidIOSVesrion> androidIOSVesrions = androidIOSVesrionService.getLast(type);
        AndroidIOSVesrion androidIOSVesrion = new AndroidIOSVesrion();
        if (androidIOSVesrions != null && androidIOSVesrions.size() !=0){
            androidIOSVesrion = androidIOSVesrions.get(0);
        }
        return  new RestResult("获取安卓更新版本",androidIOSVesrion).toString();
    }
}
