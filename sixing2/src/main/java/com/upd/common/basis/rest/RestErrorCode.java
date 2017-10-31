package com.upd.common.basis.rest;

/**
 * Created by hui on 2017/3/14.
 */
public enum RestErrorCode implements ErrorCode {

    SUCCESS(1,"操作成功"),
    ERROR(0,"操作失败"),
    SYSTEM_ERROR(3,"系统异常，请稍后重试..."),
    PARAM(4,"{0}"),
    TOKEN(5,"身份令牌不合法"),
    TOKEN_EXPIRED(6,"身份令牌已过期");

    private Integer code;
    private String msg;

    RestErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
