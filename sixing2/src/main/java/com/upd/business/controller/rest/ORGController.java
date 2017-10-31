package com.upd.business.controller.rest;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.ORG;
import com.upd.business.entity.User;
import com.upd.business.form.ORGForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.ORGService;
import com.upd.business.service.UserService;
import com.upd.business.vo.ORGUserVo;
import com.upd.business.vo.ORGVo;
import com.upd.business.vo.ORGlistVo;
import com.upd.business.vo.PageVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 组织关系控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/org")
public class ORGController extends BaseController {
    @Autowired
    private ORGService orgService;
    @Autowired
    private UserService userService;

    /**
     * 获取组织关系列表
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestHeader("token") String token) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        ORGlistVo vo = new ORGlistVo();
        ORG org = orgService.getAll(ORGType.PARTY_GROUP,unitCode);
        if(null!=org) {
            BeanUtils.copyProperties(vo, org);
            getChild(org.getChildren(), vo);
        }
        return new RestResult("组织关系",vo).toString();
    }
    public void getChild(List<ORG> children,ORGlistVo vo) throws InvocationTargetException, IllegalAccessException {
        if(null!=children) {
            List<ORGlistVo> childrenVos = new ArrayList<ORGlistVo>();
            for (ORG org1 : children) {
                ORGlistVo childrenvo = new ORGlistVo();
                BeanUtils.copyProperties(childrenvo, org1);
                childrenVos.add(childrenvo);
                if(null!=org1.getChildren()){
                    getChild(org1.getChildren(),childrenvo);
                }
            }
            vo.setChildrenVo(childrenVos);
        }
    }
    /**
     * 获取党支部组织关系列表
     * @return
     */
    @RequestMapping("/branchList")
    @ResponseBody
    public String branchList(@RequestHeader("token") String token) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        String unitCode = jwt.getHeaderClaim("unitCode").asString();
        ORGlistVo vo = new ORGlistVo();
        ORG org = orgService.getAll(ORGType.PARTY_COMMITTEE,unitCode);//获取机关党委组织
        if(null!=org) {
            BeanUtils.copyProperties(vo, org);
            getChild(org.getChildren(), vo);
        }
        List<ORGlistVo> orgList = vo.getChildrenVo();
        return new RestResult("获取党支部组织关系列表",orgList).toString();
    }

    /**
     * 查询该级组织党员
     * @param page
     * @param form
     * @return
     */
    @RequestMapping("/userList")
    @ResponseBody
    public String userList(Pagination page, UserForm form) throws InvocationTargetException, IllegalAccessException {
        ORG org = orgService.get(form.getOrgId());
        String type = form.getType();
        if (type != null){
            if (type.equals("woman")){//女党员
                userService.findWomen(page,form.getOrgId(),org.getLevel());
            }else if (type.equals("min")){//少数民族
                userService.findMin(page,form.getOrgId(),org.getLevel());
            }else {//其他
                form.setLevel(org.getLevel());
                userService.page(page,form);
            }
        }else {//查询全部
            form.setLevel(org.getLevel());
            userService.page(page,form);
        }
        //组织用户
        List<User> userList = page.getList();

        List<ORGUserVo> userVos = new ArrayList<ORGUserVo>();
        if (userList != null && userList.size() != 0){
            for (User user : userList) {
                ORGUserVo userVo = new ORGUserVo();
                BeanUtils.copyProperties(userVo, user);
                userVos.add(userVo);
            }
            page.setList(userVos);
        }
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        return new RestResult("组织关系",pageVo).toString();
    }

    /**
     * 统计该级组织党员信息
     * @param orgId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/userCount")
    @ResponseBody
    public String userCount(Integer orgId) throws InvocationTargetException, IllegalAccessException {
        Map<String,Integer> map = userService.countUser(orgId);
        return new RestResult("组织关系",map).toString();
    }

}
