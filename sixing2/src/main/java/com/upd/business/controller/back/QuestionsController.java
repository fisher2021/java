package com.upd.business.controller.back;

import com.upd.business.constant.ExamQuestionsType;
import com.upd.business.constant.ExamType;
import com.upd.business.entity.Questions;
import com.upd.business.form.QuestionsForm;
import com.upd.business.service.QuestionsService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.DateUtil;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/25.
 */
@Controller
@RequestMapping("/back/questions")
public class QuestionsController extends BaseController{

    @Autowired
    QuestionsService questionsService;

    @RequestMapping("/page")
    public String page(Pagination page, QuestionsForm form, Model model){
        questionsService.page(page,form);
        model.addAttribute("questionsType", ExamQuestionsType.values());
        model.addAttribute("type", ExamType.values());
        model.addAttribute("form", form);
        model.addAttribute("page", page);
        return "exam/questions/page";
    }

    @RequestMapping("/info/{id}")
    public String info(@PathVariable("id") Integer id , Model model){
        Questions info=new Questions();
        if(null!=id){
            info= questionsService.get(id);
        }
        model.addAttribute("questionsType", ExamQuestionsType.values());
        model.addAttribute("type", ExamType.values());
        model.addAttribute("info", info);
        return "exam/questions/info";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(Questions param){
        if(null==param.getId()){
            param.initTime();
            questionsService.save(param);
        }else{
//            param.setUpdateTime(DateUtil.parseDateTimeToSec(new Date()));
            param.initTime();
            questionsService.update(param);
        }
        return new RestResult("试题新增",null).toString();
    }

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String delete(@PathVariable("ids") String ids){
        questionsService.deletemore(ids);
        return new RestResult("试题删除",null).toString();
    }
}
