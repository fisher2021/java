package com.upd.common.util.queryParameter;

/**
 * Created by zhangshao on 2015/5/12.
 */
public class QueryParam {
    //属性名称
    String name;
    //属性值
    String value;
    //操作符 '= ' or 'like','< ' ' > '等
    String operater;

    public QueryParam(String name, String value, String operater) {
        this.name = name;
        this.value = value;
        this.operater = operater;
    }

    public QueryParam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }
}
