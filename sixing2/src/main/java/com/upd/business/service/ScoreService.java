package com.upd.business.service;

import com.upd.business.entity.Score;
import com.upd.business.form.ScoreForm;
import com.upd.common.basis.service.BaseService;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/24.
 */
public interface ScoreService extends BaseService<Score,Integer> {
    Map passTotal(ScoreForm form);
}
