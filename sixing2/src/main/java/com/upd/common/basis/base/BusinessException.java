package com.upd.common.basis.base;

import com.upd.common.basis.rest.ErrorCode;

import java.text.MessageFormat;

/**
 * Created by hui on 2017/3/23.
 */
public class BusinessException extends RuntimeException{

    private Integer code;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code=code;
    }

    public BusinessException(ErrorCode errorCode, Object ...params) {
        super(MessageFormat.format(errorCode.getMsg(),params));
        this.code = errorCode.getCode();
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
