package com.upd.business.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.ORG;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public class ORGlistVo {
    protected Integer id;
    private String name;//名称
    private List<ORGlistVo> childrenVo;//子
    private ORGType level;//组织级别描述
    private String changeDate;//下届换届时间
    private Integer orgLevel;//组织级别

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ORGlistVo> getChildrenVo() {
        return childrenVo;
    }

    public void setChildrenVo(List<ORGlistVo> childrenVo) {
        this.childrenVo = childrenVo;
    }

    public ORGType getLevel() {
        return level;
    }

    public void setLevel(ORGType level) {
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChangeDate() {
        if (changeDate != null){
            return changeDate.substring(0,10);
        }
        return "";
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public Integer getOrgLevel() {
        return level.ordinal();
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }
}
