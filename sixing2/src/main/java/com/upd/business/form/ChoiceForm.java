package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

import java.util.List;

/**
 * Created by ljw on 2017/5/3.
 */
public class ChoiceForm extends QueryForm {
    private Integer apprId;//民主评议ID

    public ChoiceForm() {
    }

    public ChoiceForm(Integer apprId) {
        this.apprId = apprId;
    }

    protected void doParseInternal() {
        eq("appr.id",apprId);
    }

    public Integer getApprId() {
        return apprId;
    }

    public void setApprId(Integer apprId) {
        this.apprId = apprId;
    }

}
