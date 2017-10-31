package com.upd.auth.service.impl;

import com.upd.auth.dao.LogDao;
import com.upd.auth.entity.Log;
import com.upd.auth.entity.Operator;
import com.upd.auth.service.LogService;
import com.upd.auth.service.OperatorService;
import com.upd.business.constant.BacklogType;
import com.upd.business.entity.Article;
import com.upd.business.entity.IntegralSetting;
import com.upd.business.entity.Score;
import com.upd.business.entity.User;
import com.upd.business.service.ArticleService;
import com.upd.business.service.DictService;
import com.upd.business.service.IntegralSettingService;
import com.upd.business.service.UserService;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.rest.LogType;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.pay.alipay.config.AlipayConfig;
import com.upd.common.util.pay.wxpay.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/2.
 */
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log, Integer> implements LogService {

    private LogDao dao;
    @Autowired
    ArticleService articleService;
    @Autowired
    DictService dictService;
    @Autowired
    UserService userService;
    @Autowired
    OperatorService operatorService;
    @Autowired
    private IntegralSettingService integralSettingService;

    @Autowired
    public void setDao(LogDao dao) {
        this.baseDao = dao;
        this.dao = dao;
    }

    @Override
    public void points(String token, String path, List<Object> args){
        Dict dict=null;
        Integer other = null;
        if(path.indexOf("article/info")>0){
            Article article= articleService.get(Integer.parseInt(args.get(1).toString()));
            Dict type = article.getType().getParent();
            if (type != null){
                String dictId = type.getDictId();
                if (dictId.equals("djyw") || dictId.equals("llxx") || dictId.equals("tzgg")){
                    dict = dictService.getDictByDictId(dictId+"jf");
                    other = article.getId();
                }
            }
        }
        if(path.indexOf("vote/commit")>0){
            other=Integer.parseInt(args.get(3).toString());
            dict= dictService.getDictByDictId("zxtpjf");
        }
        if(path.indexOf("appr/commit")>0){
            other=Integer.parseInt(args.get(3).toString());
            dict= dictService.getDictByDictId("mzpyjf");
        }
        if(path.indexOf("exam/scoreSave")>0){
            Score score= (Score) args.get(1);
            other=score.getExam().getId();
            dict= dictService.getDictByDictId("zxksjf");
        }
        if(path.indexOf("job/add")>0){
            dict= dictService.getDictByDictId("gzfkjf");
        }
        if(path.indexOf("report/add")>0){
            dict= dictService.getDictByDictId("sxhbjf");
        }
        if(null!=dict){
            Integer userId = TokenUtils.get(token,"id").asInt();
            editPoint(userId,other,dict);
        }
    }

    @Override
    public void editPoint(Integer userId,Integer other,Dict dict) {
        User user = userService.get(userId);
        //判断是否已记录
        Log log=dao.findOne("FROM Log WHERE user=? AND other=? AND type=? AND description=?",userId,other, LogType.POINTS,dict.getDictName());
        if(null==log) {
            //设置当前用户所在组织的管理员
            String code = user.getHighestOrg().getCode().substring(0,7);
            Operator operator = operatorService.getOperator(code);
            IntegralSetting integralSetting = integralSettingService.getByCodeAndType(code,dict.getDictId());
            save(new Log(userId, other, LogType.POINTS, dict.getDictName(), integralSetting.getValue(),operator));
            user.setPoints(user.getPoints() + Integer.parseInt(integralSetting.getValue()));//修改积分
            userService.update(user);
        }
    }

}
