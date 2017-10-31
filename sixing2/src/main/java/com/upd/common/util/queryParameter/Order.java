package com.upd.common.util.queryParameter;

/**
 * Created by zhangshao on 2015/12/9.
 */
public class Order {
    //排序属性
    String property;
    //排序方式
    String mode="";

    public Order() {
    }
    public Order(String property, String mode) {
        this.property = property;
        this.mode = mode;
    }

    public Order(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
