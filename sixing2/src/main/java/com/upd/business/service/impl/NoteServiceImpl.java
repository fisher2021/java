package com.upd.business.service.impl;

import com.upd.business.dao.NoteDao;
import com.upd.business.entity.Note;
import com.upd.business.form.AdviceForm;
import com.upd.business.service.NoteService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("noteService")
public class NoteServiceImpl extends BaseServiceImpl<Note, Integer> implements NoteService{
    @Autowired
    public NoteDao noteDao;
    @Autowired
    public void setBaseDao(NoteDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Pagination page(Pagination page, Integer userId) {
        return noteDao.findbypage(page,"from Note where userId = ? order by createTime desc",userId);
    }

    @Override
    public void add(Note note) {
        note.initTime();
        noteDao.save(note);
    }

}
