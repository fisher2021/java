package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.entity.Note;
import com.upd.business.service.NoteService;
import com.upd.business.service.UserService;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.queryParameter.QueryParam;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

/**
 * 笔记控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/note")
public class NoteController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    NoteService noteService;
    @Autowired
    OperatorService operatorService;

    /**
     * 获取我的笔记列表
     * @param page
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public String page(@RequestHeader("token") String token,Pagination page) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        noteService.page(page,id.asInt());
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return  new RestResult("笔记列表",pageVo).toString();
    }

    /**
     * 新增笔记
     * @param token
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestHeader("token") String token, Note note){
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        note.setUserId(id.asInt());
        //设置当前用户所在组织的管理员
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        Operator operator = operatorService.getOperator(unitCode);
        note.setOperator(operator);
        noteService.add(note);
        return  new RestResult("新增笔记","").toString();
    }

    /**
     * 删除笔记
     * @param note
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Note note){
        noteService.delete(note);
        return new RestResult("删除笔记",null).toString();
    }
}
