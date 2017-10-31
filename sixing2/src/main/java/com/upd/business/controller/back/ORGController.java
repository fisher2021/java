package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.ORG;
import com.upd.business.form.ORGForm;
import com.upd.business.service.DictTypeService;
import com.upd.business.service.ORGService;
import com.upd.business.vo.ZtreeVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.rest.ErrorCode;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织关系管理
 * Created by Administrator on 2017/5/17.
 */
@Controller("backORGController")
@RequestMapping("/back/org")
public class ORGController extends BaseController {

    @Autowired
    ORGService orgService;
    @Autowired
    DictTypeService dictTypeService;

    @RequestMapping("/page")
    public String page(Pagination page, ORGForm form, Model model) {
        orgService.page(page, form);
        model.addAttribute("type",  ORGType.values());
        model.addAttribute("form", form);
        model.addAttribute("page", page);
        return "org/list";
    }

//    @RequestMapping("/info/{id}")
//    public String info(@PathVariable("id") Integer id ,Model model){
//        if(null!=id){
//            ORG  org = orgService.get(id);
//            model.addAttribute("info", org);
//        }
//        model.addAttribute("type", ORGType.values());
//        return "org/info";
//    }

    /**
     * 跳转新增或编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/info/{id}")
    public String info(@PathVariable("id") Integer id, Model model) {

        if (id != 0) {
            ORG org = orgService.get(id);
            model.addAttribute("info", org);
        } else {
            ORG org = orgService.findParent();
            model.addAttribute("parent", org);
        }
        return "org/addOrEdit";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String addOrg(ORG param,HttpSession session) {
        if (param.getId() != null) {
            orgService.edit(param);
            return new RestResult("组织新增", null).toString();
        }
        int codeNum = 1;//编码序号。默认从1开始
        String parentCode = "";//父级组织编码
        if (null != param.getParent() && null != param.getParent().getId()) {
            ORG partent = orgService.get(param.getParent().getId());
            //当前添加组织类别=父级+1
            ORGType orgType = ORGType.valueOf(partent.getLevel().ordinal() + 1);
            param.setLevel(orgType);
            //获取父级组织下子组织最大编码
            parentCode = partent.getCode();
            String lastCode = orgService.getLastCode(param.getParent().getId());
            if (null != lastCode) {
                codeNum = Integer.parseInt(lastCode.substring(parentCode.length())) + 1;
            } else {

            }
        } else {
            return new RestResult("组织新增",null, RestErrorCode.PARAM,"未选择上级组织").toString();
        }
        //根据组织级别编码序号长度生成组织编码
        int codeLength = param.getLevel().getCodeLength();
        String orgCode = codeNum + "";
        while (orgCode.length() < codeLength) {
            orgCode = "0" + orgCode;
        }
        orgCode = parentCode + orgCode;
        param.setCode(orgCode);
        orgService.add(param);
        return new RestResult("组织新增", null).toString();
    }
/*

    @RequestMapping("/add")
    @ResponseBody
    public String add(ORG org) {
        if (org.getId() == null) {
            org.setLevel(ORGType.PARTY_BRANCH);
            orgService.add(org);
        } else {
            orgService.edit(org);
        }
        return new RestResult("组织新增", null).toString();
    }
*/

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String delete(@PathVariable("ids") String ids) {
        orgService.deletemore(ids);
        //修改组织版本
        DictType dictType = dictTypeService.getDictTypeByDictTypeId("orgversion");
        dictType.setValue(String.valueOf(new Date().getTime()));
        dictTypeService.update(dictType);
        return new RestResult("组织删除", null).toString();
    }

    /**
     * 获取当前用户的组织信息
     * @param session
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public RestResult getList(HttpSession session) {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        ORG org = orgService.get(operator.getOrg().getId());
        List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
        tree(ztreeVos,org);
        return new RestResult("组织", ztreeVos);
    }

    private ZtreeVo tree(List<ZtreeVo> ztreeVos,ORG org) {
        ZtreeVo vo = new ZtreeVo();
        vo.setId(org.getId());
        vo.setName(org.getName());
        if (null != org.getParent()) {
            vo.setpId(org.getParent().getId());
        } else {
            vo.setpId(0);
            vo.setOpen(true);
        }
        ztreeVos.add(vo);
        if(null!=org.getChildren()){
            for (ORG child : org.getChildren()) {
                tree(ztreeVos,child);
            }
        }
        return vo;
    }

    /**
     * 子组织列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/children")
    public String children(ORG org, Model model) {
        //查询子组织
        List<ORG> children = orgService.getChildren(org);
        model.addAttribute("children", children);
        return "user/orguser/list";
    }
}
