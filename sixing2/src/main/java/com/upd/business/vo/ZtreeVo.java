package com.upd.business.vo;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created by hui on 2017/3/24.
 */
public class ZtreeVo {

    private Integer id;
    private Integer pId;
    private String name;
    private boolean open;
    private boolean isParent;
    private String _href;

    public ZtreeVo() {
    }

    public ZtreeVo(Integer id, Integer pId, String name, boolean open, boolean isParent, String _href) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.isParent = isParent;
        this._href = _href;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String get_href() {
        return _href;
    }

    public void set_href(String _href) {
        this._href = _href;
    }
}
