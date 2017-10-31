package com.upd.common.util;

/**
 * Created by xiao.tao on 2016/5/3.
 */
public class ZTree {
    private int id;//编号
    private int pId;//父节点
    private boolean isParent;   //是否父节点
    private String name;    //节点名称
    private boolean open;   //是否打开
    private int sort;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
