package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.service.LogService;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.LogForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.*;
import com.upd.business.vo.ORGUserVo;
import com.upd.business.vo.ORGVo;
import com.upd.business.vo.PageVo;
import com.upd.business.vo.RoomUserVo;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.rest.LogType;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.EhcacheUtils;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.page.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
@Controller
@RequestMapping("/rest/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    FileSaveService fileSaveService;
    @Autowired
    ORGService orgService;
    @Autowired
    DictTypeService dictTypeService;
    @Autowired
    LogService logService;
    @Autowired
    RoomService roomService;
    @Autowired
    private IntegralSettingService integralSettingService;
    /**
     * 登录
     * @param account 账号
     * @param password 密码
     * @return 返回token
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(@RequestParam String account,@RequestParam String password) {
        User user=userService.info(new UserForm(account,password));
        if (null==user){
            UserForm form = new UserForm();
            form.setPhone(account);
            form.setPassword(password);
            user = userService.info(form);
        }
        if(null!=user) {
            String token = userService.getToken(user);
            EhcacheUtils.putToken(account,token);
            user.setToken(token);
            return new RestResult("登录", user).toString();
        }
        return new RestResult("登录",null , RestErrorCode.PARAM, "用户名或密码错误！").toString();
    }

    /**
     * 修改密码
     * @param token 身份令牌
     * @param newPassword 新密码
     * @return
     */
    @RequestMapping(value = "/upPassword")
    @ResponseBody
    public String upPassword(@RequestHeader("token") String token,@RequestParam String password, @RequestParam String newPassword) {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        Claim account= jwt.getHeaderClaim("account");
        Claim pwd= jwt.getHeaderClaim("password");
        if(!pwd.asString().equals(password)){
            throw new BusinessException(RestErrorCode.PARAM,"原密码错误！");
        }if(userService.upPassword(account.asString(),newPassword,id.asInt())) {
            //新 token 并加入缓存
            User user=userService.get(id.asInt());
            String ntoken= userService.getToken(user);
            EhcacheUtils.putToken(account.asString(),ntoken);
            return new RestResult("修改密码", ntoken).toString();
        }
        return new RestResult("修改密码",null , RestErrorCode.PARAM, "用户名或密码错误！").toString();
    }
    /**
     * 查询个人信息
     * @param token 身份令牌
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String info(@RequestHeader("token") String token) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        User user = userService.get(id.asInt());
        return new RestResult("查询个人信息",user).toString();
    }

    /**
     * 修改个人信息
     * @param token 身份令牌
     * @param user 用户信息
     * @return
     */
    @RequestMapping(value = "/upInfo")
    @ResponseBody
    public String upInfo(@RequestHeader("token") String token, User user,  MultipartHttpServletRequest request) throws IOException {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        user.setId(id.asInt());
        //头像上传处理
        MultipartFile file = request.getFile("file");
        if(file != null){
            //上传到文件服务器
            String folder = "/user/";
            String image = fileSaveService.add(file,folder).getUrl();
            user.setImage(image);
        }
        user = userService.editUser(user,null);
        return new RestResult("修改个人信息",user).toString();
    }
    /**
     * 下载图片
     * @param
     * @return
     */
    @RequestMapping(value = "/download")
    @ResponseBody
    public void downloadFile(Integer fileName, HttpServletResponse response) {
        if (fileName != null) {
            FileSave save = fileSaveService.get(fileName);
            String path = save.getPath();
            String strs[] = path.split("[.]");
            String suffix = strs[strs.length - 1];
            String newFile = fileName + "."+ suffix;
            File file = new File(path);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition","attachment;fileName=" + newFile);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                             e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 联系人列表
     * @param version 版本
     * @return
     */
    @RequestMapping(value = "/orgList")
    @ResponseBody
    public String orgList(@RequestHeader("token") String token,String version) throws InvocationTargetException, IllegalAccessException {
        JWT jwt= TokenUtils.verify(token);
        String code = jwt.getHeaderClaim("unitCode").asString();
        String unitCode = code.substring(0,ORGType.SYS_DEFAULT.getCodeLength() +ORGType.UNIT.getCodeLength())+ "0";
        List<ORG> ls= orgService.getChildrenByCode(unitCode);
        DictType dictType=dictTypeService.getDictTypeByDictTypeId("orgversion");
        List<ORGVo> result = new ArrayList<ORGVo>();
        if(!dictType.getValue().equals(version)) {
            for (ORG org : ls) {
                ORGVo GroupVo = new ORGVo();
                BeanUtils.copyProperties(GroupVo, org);
                //组织用户
                List<ORGUserVo> userVos = new ArrayList<ORGUserVo>();
                UserForm form = new UserForm();
                form.setOrgId(org.getId());
                form.setLevel(org.getLevel());
                List<User> userList = userService.orgList(form);
                for (User user : userList) {
                    ORGUserVo userVo = new ORGUserVo();
                    BeanUtils.copyProperties(userVo, user);
                    userVos.add(userVo);
                }
                GroupVo.setUser(userVos);
                result.add(GroupVo);
            }
        }
        Map map=new HashMap();
        map.put("list",result);
        map.put("version",dictType.getValue());
        return new RestResult("通讯录",map).toString();
    }

    /**
     * 组织关系列表
     * @param version 版本
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/orgPage")
    @ResponseBody
    public String orgPage(String version) throws InvocationTargetException, IllegalAccessException {
        List<ORG> ls= orgService.findAll();
        DictType dictType=dictTypeService.getDictTypeByDictTypeId("orgversion");
        List<ORGVo> result = new ArrayList<ORGVo>();
        if(!dictType.getValue().equals(version)) {
            for (ORG org : ls) {
                ORGVo GroupVo = new ORGVo();
                BeanUtils.copyProperties(GroupVo, org);
                //组织用户
                List<ORGUserVo> userVos = new ArrayList<ORGUserVo>();
                List<User> userList = org.getUser();
                for (User user : userList) {
                    ORGUserVo userVo = new ORGUserVo();
                    BeanUtils.copyProperties(userVo, user);
                    userVos.add(userVo);
                }
                GroupVo.setUser(userVos);
                result.add(GroupVo);
            }
        }
        Map map=new HashMap();
        map.put("list",result);
        map.put("version",dictType.getValue());
        return new RestResult("组织关系",map).toString();
    }

    /**
     * 用户积分
     * @param token
     * @param page
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/pointsPage")
    @ResponseBody
    public String pointsPage(@RequestHeader("token") String token, Pagination page) throws InvocationTargetException, IllegalAccessException {
        LogForm form=new LogForm();
        int userId = TokenUtils.get(token,"id").asInt();
        form.setUser(userId);
        form.setType(LogType.POINTS);
        System.out.println(LogType.POINTS.ordinal());
        logService.page(page,form);//积分列表
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageVo,page);
        Map map=new HashMap();
        map.put("page",pageVo);
        //获取当前用户所在单位的达标积分
        User user = userService.get(userId);
        String code = getOperatorUnitCode(user.getOperator());
        IntegralSetting standard = integralSettingService.getByCodeAndType(code,"standard");

        double points=userService.get(form.getUser()).getPoints()/Double.parseDouble(standard.getValue());
        System.out.println("v:"+points);
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("points",points>=1?1:df.format(points));
        return new RestResult("用户积分",map).toString();
    }

    /**
     * 党员信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/partyMemberInfo/{id}")
    public String partyMember(@PathVariable("id") Integer id, Model model){
        User user = userService.get(id);
        model.addAttribute("info",user);
        return  "user/party_member_info";
    }

    /**
     * 积分配置
     * @param model
     * @return
     */
    @RequestMapping(value = "/pointsDesc")
    public String pointsDesc(@RequestHeader("token") String token, Model model){
        JWT jwt= TokenUtils.verify(token);
        String code= jwt.getHeaderClaim("unitCode").asString();
        List<IntegralSetting> integralSettingList = integralSettingService.getList(code);
        model.addAttribute("list", integralSettingList);
        if(integralSettingList!=null){
            model.addAttribute("standard", integralSettingList.get(0).getValue());
        }else{
            model.addAttribute("standard", 0);
        }
        return  "user/points_desc";
    }

    /**
     * 查询个人会议室
     * @param token 身份令牌
     * @return
     */
    @RequestMapping(value = "/room")
    @ResponseBody
    public String getRoom(@RequestHeader("token") String token,String level) {
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        List<Room> rooms = roomService.getUserRoom(id.asInt(),level);
        return new RestResult("查询个人会议室",rooms).toString();
    }
    /**
     * 查询会议室成员
     * @param roomId
     * @return
     */
    @RequestMapping(value = "/roomUser")
    @ResponseBody
    public String getRoomUser(int roomId) {
        List<User> users = roomService.get(roomId).getUser();
        List<RoomUserVo> result = new ArrayList<>();
        for (User user : users){
            if (!user.isDeleted()){
                RoomUserVo vo = new RoomUserVo();
                vo.setId(user.getId());
                vo.setName(user.getNickname());
                vo.setImage(user.getImage());
                result.add(vo);
            }
        }
        return new RestResult("查询会议室成员",result).toString();
    }


}
