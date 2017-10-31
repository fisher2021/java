package com.upd.common.util.queryParameter;

/**
 * Created by zhangshao on 2015/5/28.
 * 查询条件类
 */
public class SearchCondition {
    //查询字段(不能为集合属性)
    private String[] propertys;
    //查询条件
    private QueryParam[] params;
    //排序条件
    private Order[] orders;
    public String[] getPropertys() {
        return propertys;
    }

    public void setPropertys(String[] propertys) {
        this.propertys = propertys;
    }

    public QueryParam[] getParams() {
        return params;
    }

    public void setParams(QueryParam[] params) {
        this.params = params;
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }
}
