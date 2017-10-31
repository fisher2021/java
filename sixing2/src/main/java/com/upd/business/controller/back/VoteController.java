package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.ORG;
import com.upd.business.entity.Options;
import com.upd.business.entity.Vote;
import com.upd.business.form.*;
import com.upd.business.service.*;
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
 * 在线投票后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backVoteController")
@RequestMapping("/back/vote")
public class VoteController extends BaseController {
	@Autowired
	private VoteService voteService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private FileSaveService fileSaveService;
    @Autowired
    private ORGService orgService;

	/**
	 * 分页条件查询在线投票列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/page")
	public String page(Pagination page, Model model, VoteForm form, HttpSession session){
        Operator operator = (Operator) session.getAttribute("logedOperator");
        //党支部的管理员只能查看本支部的在线投票
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		voteService.page(page, form);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "vote/page";
	}
	/**
	 * 跳转到在线投票详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public String info(Integer id, Model model){
		Vote vote = voteService.get(id);
		String voter = vote.getVoter();
		if (voter != null){
			String[] voters = voter.split(",");
			model.addAttribute("count", voters.length);
		}else {
			model.addAttribute("count", 0);
		}
		OptionForm form = new OptionForm(id);
		List<Options> options = optionsService.search(form);
        if (vote.getOrgList() != null && !vote.getOrgList().equals("")){
            List<String> orgs = orgService.getOrgListName(vote.getOrgList());
            model.addAttribute("orgs", orgs);
        }
		if (options.size() != 0){
			model.addAttribute("options", options.get(0));
		}
		return "vote/info";
	}

	/**
	 * 跳转到在线投票添加或修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model,HttpSession session){
		if(id != null){
			Vote vote = voteService.get(id);
			model.addAttribute("vote", vote);
		}
		//获取当前用户的下级组织
        String code = getOperatorUnitCodeUnder(null);
        model.addAttribute("orgList", orgService.getChildrenByCode(code));
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "vote/addOrEdit";
	}

	/**
	 * 添加或修改在线投票
	 * @param options
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(Options options, MultipartHttpServletRequest request, String voteTitle, String[] option, HttpSession session,Integer[] orgs) throws IOException {
	    Vote vote = new Vote();
		vote.setContent(options.getContent());
		vote.setTitle(voteTitle);
        Operator operator = (Operator) session.getAttribute("logedOperator");
        //如果不是党支部添加的，将组织ID都设置为0，方便展示给所有用户
        if (operator.getOrg().getLevel() != ORGType.PARTY_BRANCH){
            vote.setOrgId(0);
        }else {
            vote.setOrgId(operator.getOrg().getId());
        }
		//图片上传处理
		MultipartFile file = request.getFile("file");
        if(file != null){
            String folder = "/vote/";
            String image = fileSaveService.add(file,folder).getUrl();
            options.setImage(image);
        }
        if (orgs != null){
            vote.setOrgList(orgService.getOrgList(orgs));
        }
		if (options.getId() == null){
            vote.setOperator(operator);
			voteService.add(vote);
			options.setVote(vote);
			optionsService.add(options,option);
		}
		return new RestResult("添加或修改在线投票",null).toString();
	}

	/**
	 * 删除在线投票
	 * @param id
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Integer id){
		Vote vote = voteService.get(id);
		voteService.delete(vote);
		return new RestResult("删除在线投票",null).toString();
	}

	
}
