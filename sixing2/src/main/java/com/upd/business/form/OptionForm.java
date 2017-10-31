package com.upd.business.form;

import com.upd.common.basis.base.QueryForm;

/**
 * Created by ljw on 2017/5/3.
 */
public class OptionForm extends QueryForm {
    private Integer voteId;//民主评议ID

    public OptionForm() {
    }

    public OptionForm(Integer voteId) {
        this.voteId = voteId;
    }

    protected void doParseInternal() {
        eq("vote.id",voteId);
        orderBy("createTime desc");
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }
}
