package com.upd.business.controller.back;

import com.alibaba.fastjson.JSON;
import com.upd.business.entity.IntegralSetting;
import com.upd.business.service.DictService;
import com.upd.business.service.DictTypeService;
import com.upd.business.service.IntegralSettingService;
import com.upd.business.vo.PointsVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.rest.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */
@Controller("backPointsController")
@RequestMapping("/back/points")
public class PointsController extends BaseController {

    @Autowired
    DictTypeService dictTypeService;
    @Autowired
    DictService dictService;
    @Autowired
    private IntegralSettingService integralSettingService;

    @RequestMapping("/info")
    public String info(Model model){
//        DictType type=dictTypeService.getDictTypeByDictTypeName(" 积分配置");
//        System.out.println(JSON.toJSONString(type));
        String code = getOperatorUnitCode(null);
        List<IntegralSetting> integralSettingList = integralSettingService.getList(code);
        model.addAttribute("list", integralSettingList);
        return "points/info";
    }

    /**
     * 修改积分配置
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(PointsVo vo) throws IllegalAccessException {
        List<Dict> dictList = dictService.getDictListByTypeId("points");
        String code = getOperatorUnitCode(null);
        for(Dict dict:dictList){
            Field[] fields = vo.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                if(dict.getDictId().equals(field.getName())){

                    if(null!=field.get(vo)) {
                        dictService.update(dict);
                        IntegralSetting integralSetting = integralSettingService.getByCodeAndType(code,field.getName());
                        integralSetting.setValue(field.get(vo).toString());
                        integralSettingService.update(integralSetting);
                    }
                }
            }
        }
        return new RestResult("积分配置",null).toString();
    }
}
