package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.BacklogType;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.DataForm;
import com.upd.business.form.UserForm;
import com.upd.business.service.DataService;
import com.upd.business.service.DataUserService;
import com.upd.business.service.ORGService;
import com.upd.business.service.UserService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 资料下载后台管理控制器
 * @author ljw
 * 2017年1月5日
 */
@Controller("backDataController")
@RequestMapping("/back/data")
public class DataController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    ORGService orgService;
    @Autowired
    DataService dataService;
    @Autowired
    DataUserService dataUserService;
	/**
	 * 分页条件查询
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, DataForm form, HttpSession session){
	    //获取登录信息,支部管理员只能查看本支部的资料
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		dataService.page(page, form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
        model.addAttribute("type", BacklogType.values());
		return "data/page";
	}
	/**
	 * 跳转到详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public String info(Integer id, Model model){
        Data data = dataService.get(id);
		model.addAttribute("file", data);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "data/info";
	}

	/**
	 * 跳转到添加或修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model){
		if(id != null){
            Data data = dataService.get(id);
			model.addAttribute("file", data);
		}
        List<ORG> orgList = orgService.findAll();
        model.addAttribute("orgList", orgList);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "data/addOrEdit";
	}

	/**
	 * 添加或修改
	 * @param orgs
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(MultipartHttpServletRequest request, HttpSession session,String title, Integer[] orgs) throws IOException {
        Operator operator = (Operator) session.getAttribute("logedOperator");
        MultipartFile file = request.getFile("file");
        String folder = "/data/";
        Data data = new Data();
        data.setOperator(operator);
        data.setOrgId(operator.getOrg().getId());
        data.setTitle(title);
        if (orgs != null){
            for (Integer orgId : orgs) {
                List<User> users = userService.findByORG(orgId);
                data.setUsers(users);
            }
        }
        dataService.add(file,folder,data);
        return new RestResult("添加或修改资料",null).toString();
	}

	/**
	 * 删除
	 * @param data
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Data data){
	    dataService.delete(data);
		return new RestResult("删除资料",null).toString();
	}

    /**
     * 跳转到添加指派对象页面
     * @param page
     * @param model
     * @param form
     * @return
     */
    @RequestMapping("/userPage")
    public String list(Pagination page, Model model, UserForm form, HttpSession session){
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        //该资料指派的用户
        if (form.fileId != null){
            List<User> users = dataService.get(form.fileId).getUsers();
            StringBuffer sb = new StringBuffer();
            for (User user:users){
                sb.append(user.getId()).append(",");
            }
            String ids = sb.substring(0,sb.length()-1);
            form.setIds(ids);
        }
        userService.page(page,form);
        model.addAttribute("page", page);
        model.addAttribute("form", form);
        model.addAttribute("fileId", form.getFileId());
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
        return "data/userPage";
    }
    /**
     * 批量添加指派对象
     * @param ids
     * @return
     */
    @RequestMapping("/ajax/addBatch")
    @ResponseBody
    public String addBatch(Integer fileId,Integer[] ids){
        if(ids.length != 0){
            Data data = dataService.get(fileId);
            for (int i=0;i<ids.length;i++){
                User user = userService.get(ids[i]);
                data.getUsers().add(user);
            }
            dataService.update(data);
        }
        return new RestResult("批量添加指派对象",null).toString();
    }
    /**
     * 删除指派对象
     * @param id
     * @return
     */
    @RequestMapping("/ajax/deleteUser")
    @ResponseBody
    public String deleteUser(Integer fileId,Integer id){
        dataUserService.deleteUser(fileId,id);
        return new RestResult("删除指派对象",null).toString();
    }

}
