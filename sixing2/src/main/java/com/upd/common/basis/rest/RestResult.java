package com.upd.common.basis.rest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Created by hui on 2017/3/14.
 */
public class RestResult<T>{

    private Integer status=0;//状态码 ErrorCode
    private String msg;//消息
    private String desc;//接口描述
    private T data;//返回数据

    public RestResult(String desc, T data) {
        this.status = RestErrorCode.SUCCESS.getCode();
        this.msg = RestErrorCode.SUCCESS.getMsg();
        this.desc = desc;
        this.data = data;
    }

    public RestResult(String desc,Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.desc = desc;
        this.data = data;
    }

    public RestResult(String desc,T data, ErrorCode code,Object ...objects){
        this.status = code.getCode();
        this.msg = MessageFormat.format(code.getMsg(),objects);
        this.desc = desc;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        PropertyFilter profilter = new PropertyFilter(){
            @Override
            public boolean apply(Object object, String name, Object value) {
                if(name.equalsIgnoreCase("password")){
                    return false;
                }
                return true;
            }
        };
        return JSONObject.toJSONString(this,profilter,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteNullListAsEmpty);
    }
}
