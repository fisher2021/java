package com.upd.business.service;

import com.upd.business.entity.Advice;
import com.upd.business.entity.Note;
import com.upd.business.form.AdviceForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;

/**
 * 笔记类业务层
 * Created by ljw on 2017/5/4.
 */
public interface NoteService extends BaseService<Note,Integer> {
    /**
     * 分页查询笔记
     * @param page 分页数据
     * @return
     */
    Pagination page(Pagination page, Integer userId);

    /**
     * 新增笔记
     * @param note
     * @return
     */
    void add(Note note);
}
