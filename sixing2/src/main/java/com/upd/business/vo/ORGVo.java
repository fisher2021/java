package com.upd.business.vo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public class ORGVo {

    private String name;//名称
    private List<ORGUserVo> user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ORGUserVo> getUser() {
        return user;
    }

    public void setUser(List<ORGUserVo> user) {
        this.user = user;
    }
}
