package com.upd.business.service.impl;

import com.upd.business.dao.OptionsDao;
import com.upd.business.entity.Options;
import com.upd.business.form.OptionForm;
import com.upd.business.service.OptionsService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("optionsService")
public class OptionsServiceImpl extends BaseServiceImpl<Options, Integer> implements OptionsService {
    @Autowired
    public OptionsDao optionsDao;
    @Autowired
    public void setBaseDao(OptionsDao dao) {
        this.baseDao = dao;
    }

    /**
     * 查询选项
     * @param form 在线投票ID
     * @return
     */
    @Override
    public List<Options> search(OptionForm form) {
        List<Options> options = optionsDao.find("from Options where"+form.toQueryDescription(),form.values());
        return options;
    }

    @Override
    public Options add(Options options,String[] option) {
        StringBuffer choice = new StringBuffer();
        StringBuffer evaluate = new StringBuffer();
        for (String s : option){
            choice.append(s).append(",");
            evaluate.append(0).append(",");
        }
        choice.delete(choice.length()-1,choice.length());
        evaluate.delete(evaluate.length()-1,evaluate.length());
        options.setEvaluate(evaluate.toString());
        options.setChoice(choice.toString());
        options.initTime();
        optionsDao.save(options);
        return options;
    }

    @Override
    public Options edit(Options options) {
        options.initTime();
        optionsDao.update(options);
        return options;
    }

}
