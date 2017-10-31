package com.upd.business.vo;

/**
 * Created by Administrator on 2017/5/11.
 */
public class UserVo {
    private Integer id;
    private String nickname;//用户昵称
    private String image;//头像
    private String duty;//党内职务

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }
}
