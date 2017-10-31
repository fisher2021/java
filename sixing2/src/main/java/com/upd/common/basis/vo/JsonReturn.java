package com.upd.common.basis.vo;

import net.sf.json.JSONObject;

/**
 * Created by zhangshao on 2015/12/9.
 * json vo对象
 */
public class JsonReturn  implements java.io.Serializable {
    boolean flag;
    String message;
    Object data;
    //单位毫秒
    long time;
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonReturn() {

    }
    @Override
    public String toString(){
        return JSONObject.fromObject(this).toString();
    }
    public JsonReturn(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }
}
