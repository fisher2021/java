package com.upd.business.constant;

import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;

/**
 * Created by Administrator on 2017/5/17.
 */
public enum ORGType {

    SYS_DEFAULT("初始组织",3),UNIT("单位",4),PARTY_GROUP("党组",4),PARTY_COMMITTEE("机关党委",4),PARTY_BRANCH("党支部",4);

    public static ORGType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new BusinessException(RestErrorCode.PARAM,"不能再创建下一级组织！");
        }
        return values()[ordinal];
    }

    ORGType(String desc,int codeLength) {
        this.desc = desc;
        this.codeLength = codeLength;
    }

    private String desc;
    private int codeLength;
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }
}
