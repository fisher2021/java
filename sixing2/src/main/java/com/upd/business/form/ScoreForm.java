package com.upd.business.form;

import com.upd.business.constant.ExamType;
import com.upd.common.basis.base.QueryForm;

/**
 * Created by Administrator on 2017/5/27.
 */
public class ScoreForm extends QueryForm{

    public String title;//标题
    public ExamType type;//类型
    public Double startValue;//分数区间查询，起始
    public Double endValue;//结束
    public Integer orgId;//组织ID

    @Override
    protected void doParseInternal() {
        eq("user.isDeleted",false);
        like("exam.title",title);
        eq("exam.type", type);
        ge("value",startValue);
        le("value",endValue);
//        eq("user.highestOrg.id",orgId);
        if (null != orgId){
            in("user.id","SELECT user.id FROM ORGUser WHERE org.id="+orgId);
        }
        orderBy("createTime DESC");
    }

    public Double getStartValue() {
        return startValue;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
