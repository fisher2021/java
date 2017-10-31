package com.upd.business.dao.impl;

import com.upd.business.dao.NoteDao;
import com.upd.business.entity.Note;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/4.
 */
@Repository("noteDao")
public class NoteDaoImpl extends BaseDaoImpl<Note,Integer> implements NoteDao{
}
