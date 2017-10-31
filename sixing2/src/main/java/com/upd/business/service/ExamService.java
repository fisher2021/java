package com.upd.business.service;

import com.upd.business.entity.Exam;
import com.upd.business.form.ExamForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * Created by Administrator on 2017/5/23.
 */
public interface ExamService extends BaseService<Exam,Integer> {
    Pagination restPage(Pagination page, ExamForm form,String unitCode);

    void insert(int id, String ids);

    void deleteQuestions(Integer exam,String ids);

    void edit(Exam exam);
}
