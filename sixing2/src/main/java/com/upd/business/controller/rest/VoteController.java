package com.upd.business.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.Options;
import com.upd.business.entity.User;
import com.upd.business.entity.Vote;
import com.upd.business.form.OptionForm;
import com.upd.business.form.VoteForm;
import com.upd.business.service.OptionsService;
import com.upd.business.service.UserService;
import com.upd.business.service.VoteService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.BigDecimalKit;
import com.upd.common.util.StringUtil;
import com.upd.common.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线投票控制器
 * Created by ljw on 2017/5/5.
 */
@Controller
@RequestMapping("rest/vote")
public class VoteController extends BaseController {
    @Autowired
    VoteService voteService;
    @Autowired
    OptionsService optionsService;
    @Autowired
    UserService userService;

    /**
     * 获取在线投票列表
     * @param token
     * @param id
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestHeader("token") String token, Integer id) {
        JWT jwt= TokenUtils.verify(token);
        Claim userId= jwt.getHeaderClaim("id");
        List<Vote> votes = new ArrayList<>();
        if (id != null){
            votes = voteService.search(id);
        }else {
            //查看本组织的，或者党组指定的
            votes = voteService.searchByOrg(userId.asInt());
        }
        if (votes != null && votes.size() != 0){
            //判断用户是否已投
            for (Vote vote:votes){
                if (vote.getVoter() != null){
                    String[] ids = vote.getVoter().split(",");
                    for (String s:ids){
                        if (s.equals(userId.asInt().toString())){
                            vote.setVoted(1);
                            break;
                        }else {
                            vote.setVoted(0);
                        }
                    }
                }else {
                    vote.setVoted(0);
                }
            }
        }
        return  new RestResult("在线投票列表",votes).toString();
    }

    /**
     * 获取选项列表
     * @param form
     * @return
     */
    @RequestMapping("/options")
    @ResponseBody
    public String option(OptionForm form) {
        List<Options> options = optionsService.search(form);
        for (Options option :options){
            if (option.getEvaluate() != null && !option.getEvaluate().equals("")){
                String[] evaluate = option.getEvaluate().split(",");
                int sum = 0;
                for(String e :evaluate){
                    sum += Integer.valueOf(e);
                }
                StringBuffer sb = new StringBuffer();
                for(String e :evaluate){
                    BigDecimal num = new BigDecimal(0);
                    if (sum != 0 && Integer.valueOf(e) != 0){
                        num = BigDecimalKit.div(Integer.valueOf(e),sum,3);
                        BigDecimal num1 = new BigDecimal(100);
                        num = num.multiply(num1).setScale(1);
                    }
                    sb.append(num).append("%").append(",");
                }
                option.setRate(sb.substring(0,sb.length()-1));
            }
        }
        return  new RestResult("在线投票选项列表",options).toString();
    }

    /**
     * 提交在线投票选项
     * @param token
     * @param ids
     * @param results
     * @param voteId
     * @return
     */
    @RequestMapping("/commit")
    @ResponseBody
    public String commit(@RequestHeader("token") String token, @RequestParam(value = "ids") Integer[] ids, @RequestParam(value = "results") Integer[] results,@RequestParam int voteId) {
        for (int i=0;i< ids.length;i++) {
            Options options = optionsService.get(ids[i]);
            String evaluates = options.getEvaluate();
            String[] evas = evaluates.split(",");
            for (int j=0;j<results.length;j++){
                Integer count = Integer.parseInt(evas[results[j]-1]);
                ++count;
                String e = count.toString();
                evas[results[j]-1] = e;
            }
            options.setEvaluate(StringUtil.arrayToString(evas));
            options.initTime();
            optionsService.update(options);
        }
        Vote vote = voteService.get(voteId);
        String users = (null==vote.getVoter())?"":vote.getVoter()+",";
        JWT jwt= TokenUtils.verify(token);
        Claim id= jwt.getHeaderClaim("id");
        vote.setVoter(users+id.asInt());
        vote.initTime();
        voteService.update(vote);
        return  new RestResult("提交在线投票选项",null).toString();
    }

}
