package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ExamType;
import com.upd.business.entity.Exam;
import com.upd.business.form.ExamForm;
import com.upd.business.service.ExamService;
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

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/25.
 */
@Controller("backExamController")
@RequestMapping("back/exam")
public class ExamController extends BaseController{

    @Autowired
    ExamService examService;

    @RequestMapping("/page")
    public String page(Pagination page, ExamForm form, Model model){
        examService.page(page,form);
        model.addAttribute("type", ExamType.values());
        model.addAttribute("form", form);
        model.addAttribute("page", page);
        return "exam/page";
    }

    @RequestMapping("/info/{id}")
    public String info(@PathVariable("id") Integer id , Model model){
        Exam info=new Exam();
        if(null!=id){
            info= examService.get(id);
        }
        model.addAttribute("type", ExamType.values());
        model.addAttribute("info", info);
        return "exam/info";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(Exam param, HttpSession session){
        if(null==param.getId()){
            Operator operator = (Operator) session.getAttribute("logedOperator");
            param.setOperator(operator);
            param.initTime();
            if(param.getTotal()==null){
                param.setTotal(0D);
            }
            examService.save(param);
        }else{
//            param.setUpdateTime(DateUtil.parseDateTimeToSec(new Date()));
//            examService.update(param);
            examService.edit(param);
        }
        return new RestResult("新增试卷",null).toString();
    }

    @RequestMapping("/add/{id}")
    @ResponseBody
    public String add(@PathVariable("id") int id,String ids){
        examService.insert(id,ids);
        return new RestResult("新增试题",null).toString();
    }

    @RequestMapping("/delete/{emam}/{ids}")
    @ResponseBody
    public String deleteQuestions(@PathVariable("emam") Integer emam,@PathVariable("ids") String ids){
        examService.deleteQuestions(emam,ids);
        return new RestResult("删除试题",null).toString();
    }

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String delete(@PathVariable("ids") String ids){
        examService.deletemore(ids);
        return new RestResult("试卷删除",null).toString();
    }
}
