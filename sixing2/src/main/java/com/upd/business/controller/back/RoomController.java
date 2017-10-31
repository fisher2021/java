package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.MeetingType;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.RoomForm;
import com.upd.business.form.RoomUserForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.ORGService;
import com.upd.business.service.RoomService;
import com.upd.business.service.RoomUserService;
import com.upd.business.service.UserService;
import com.upd.common.basis.base.BaseController;
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
import java.util.List;

/**
 * 会议室管理
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/back/room")
public class RoomController extends BaseController {

    @Autowired
    RoomService roomService;
    @Autowired
    RoomUserService roomUserService;
    @Autowired
    ORGService orgService;
    @Autowired
    UserService userService;

    /**
     * 会议室列表
     * @param page
     * @param form
     * @param model
     * @return
     */
    @RequestMapping("/page")
    public String page(Pagination page, RoomForm form, Model model,HttpSession session){
        //党支部管理员只能查看本支部的会议室
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrg(operator.getOrg().getId());
        }
        roomService.page(page,form);
        String code = getOperatorUnitCodeUnder(null);
        List<ORG> orgs = orgService.getChildrenByCode(code);//所有组织筛选
        model.addAttribute("form", form);
        model.addAttribute("orgs", orgs);
        model.addAttribute("page", page);
        return "room/page";
    }

    /**
     * 跳转到会议室添加或修改页面
     * @param id
     * @return
     */
    @RequestMapping("/addOrEdit")
    public String addOrEdit(Integer id, Model model, HttpSession session){
        Room room = roomService.get(id);

        String code = getOperatorUnitCodeUnder(null);
        List<ORG> orgs = orgService.getChildrenByCode(code);

        model.addAttribute("orgs", orgs);
        //会议类型  党小组不能看到所有类型
        MeetingType[] types = null;
        Operator operator = (Operator)session.getAttribute("logedOperator");
        System.out.println(operator.getOrg().getLevel().name());
        if("PARTY_BRANCH".equals(operator.getOrg().getLevel().name())){
            types = new MeetingType[]{MeetingType.DXZH};
        }else{
            types = MeetingType.values();
        }
        model.addAttribute("types", types);
        model.addAttribute("room", room);
        return "room/addOrEdit";
    }
    /**
     * 判断群主ID是否存在
     * @param owner
     * @return
     */
    @RequestMapping("/ajax/exist")
    @ResponseBody
    public String existMissionId(String owner){
        boolean flag = false;
        if (userService.findByAccount(owner) != null){
            flag = true;
        }
        return new RestResult("判断任务ID是否存在",flag).toString();
    }
    /**
     * 添加或修改会议室
     * @param room
     * @return
     */
    @RequestMapping("/ajax/addOrEdit")
    @ResponseBody
    public String addOrEdit(Room room,HttpSession session) {
        if (room.getId() == null){
            //判断该单位下 类型为 党员大会和党支部委员会只能添加一个
            if(room.getType()==MeetingType.DYDH || room.getType()==MeetingType.DZBWYH){
                String  orgCode = getOperatorUnitCode(null);
                if(roomService.isExitByCodeAndType(orgCode,room.getType())){
                    return new RestResult("", RestErrorCode.ERROR.getCode(), "该类型会议只允许添加一个", null).toString();
                }
            }
            Operator operator = (Operator) session.getAttribute("logedOperator");
            room.setOperator(operator);
            if (room.getOrg().getId()==null){room.setOrg(null);}
            roomService.add(room);
        }
        return new RestResult("添加或修改会议室",null).toString();
    }
    /**
     * 删除会议室
     * @param room
     * @return
     */
    @RequestMapping("/ajax/delete")
    @ResponseBody
    public String delete(Room room){
        roomService.deleteRoom(room);
        return new RestResult("删除会议室",null).toString();
    }
    /**
     * 跳转到编辑群组成员页面
     * @param
     * @return
     */
    @RequestMapping("/userPage")
    public String list(Pagination page, Model model, UserForm form, HttpSession session){
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        userService.page(page,form);
        Room room = roomService.get(form.getRoomId());
        model.addAttribute("page", page);
        model.addAttribute("form", form);
        model.addAttribute("room", room);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
        return "room/userPage";
    }
    /**
     * 批量添加会议室成员
     * @param ids
     * @return
     */
    @RequestMapping("/ajax/addBatch")
    @ResponseBody
    public String addBatch(Integer roomId,Integer[] ids){
        if(ids.length != 0){
            roomUserService.addBatch(roomId,ids);
        }
        return new RestResult("批量添加会议室成员",null).toString();
    }
    /**
     * 删除群组成员
     * @param id
     * @return
     */
    @RequestMapping("/ajax/deleteUser")
    @ResponseBody
    public String deleteUser(Integer roomId,Integer id){
        roomUserService.deleteUser(roomId,id);
        return new RestResult("删除群组成员",null).toString();
    }

    /**
     * 转让群主
     * @param roomId
     * @return
     */
    @RequestMapping("/newGroupOwner")
    @ResponseBody
    public String newGroupOwner(Integer roomId,String account){
        roomService.newGroupOwner(roomId,account);
        return new RestResult("转让群主",null).toString();
    }


    @RequestMapping("/info/{id}")
    public String info(@PathVariable("id") Integer id , Model model){
        Room org=new Room();
        if(null!=id){
            org= roomService.get(id);
        }
        model.addAttribute("info", org);
        return "room/info";
    }

    @RequestMapping("/add/{id}")
    @ResponseBody
    public String add(@PathVariable("id") int id,String ids){
        if(roomService.insert(id,ids)>0){
            return new RestResult("会议室成员新增",null).toString();
        }else{
            return new RestResult("会议室成员新增",null, RestErrorCode.ERROR).toString();
        }
    }

//    @RequestMapping("/delete/{ids}")
//    @ResponseBody
//    public String delete(@PathVariable("ids") String ids,int room){
//        roomService.delete(room,ids);
//        return new RestResult("组织删除",null).toString();
//    }
}
