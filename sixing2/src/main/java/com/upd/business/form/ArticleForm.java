package com.upd.business.form;

import com.alibaba.fastjson.JSON;
import com.upd.auth.entity.Operator;
import com.upd.common.basis.base.QueryForm;
import com.upd.common.util.queryParameter.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ljw on 2017/5/3.
 */
public class ArticleForm extends QueryForm{
    private String title;//文章标题
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String type;//词典类型
    private String ids;//频道id组合
    private String channelDictId1;//频道ID
    private String channelDictId2;//频道ID
    private Integer orgId;//组织ID

    @Override
    protected void doParseInternal() {
        Operator operator = (Operator) getSession().getAttribute("logedOperator");

        ge("DATE_FORMAT(createTime,'%Y-%m-%d')",startTime);
        le("DATE_FORMAT(createTime,'%Y-%m-%d')",endTime);
        like("title",title);
        eq("type.dictId",type);
        in("type.id",ids);
        if (channelDictId2 == null || channelDictId2.equals("")){
            eq("type.dictId",channelDictId1);
        }else {
            eq("type.dictId",channelDictId2);
        }
        eq("orgId",orgId);
        orderBy("rank desc,createTime desc");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getChannelDictId1() {
        return channelDictId1;
    }

    public void setChannelDictId1(String channelDictId1) {
        this.channelDictId1 = channelDictId1;
    }

    public String getChannelDictId2() {
        return channelDictId2;
    }

    public void setChannelDictId2(String channelDictId2) {
        this.channelDictId2 = channelDictId2;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
