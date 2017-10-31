package com.upd.business.service.impl;

import com.upd.business.dao.ChoiceDao;
import com.upd.business.entity.Appraisement;
import com.upd.business.entity.Choice;
import com.upd.business.form.AppraisementForm;
import com.upd.business.form.ChoiceForm;
import com.upd.business.service.ChoiceService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("choiceService")
public class ChoiceServiceImpl extends BaseServiceImpl<Choice, Integer> implements ChoiceService {
    @Autowired
    public ChoiceDao choiceDao;
    @Autowired
    public void setBaseDao(ChoiceDao dao) {
        this.baseDao = dao;
    }

    /**
     * 分页查询选项
     * @param form 民主评议ID
     * @return
     */
    @Override
    public List<Choice> search(ChoiceForm form) {
        List<Choice> choices = choiceDao.find("from Choice where"+form.toQueryDescription(),form.values());
        return choices;
    }

    @Override
    public void add(Appraisement appraisement,String[] option) {
        for (int i=0;i<option.length;i++){
            Choice choice = new Choice();
            choice.setAppr(appraisement);
            choice.setChoice("优秀,良好,一般");
            choice.setEvaluate("0,0,0");
            choice.setUserName(option[i]);
            choice.initTime();
            choiceDao.save(choice);
        }
    }

    @Override
    public Choice edit(Choice choice) {
        choice.initTime();
        choiceDao.update(choice);
        return choice;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            choiceDao.delete(choiceDao.get(Integer.parseInt(id)));
        }
    }
}
