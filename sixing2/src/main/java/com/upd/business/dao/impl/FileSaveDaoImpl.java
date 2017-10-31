package com.upd.business.dao.impl;

import com.upd.business.dao.FileSaveDao;
import com.upd.business.entity.FileSave;
import com.upd.common.basis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ljw on 2017/5/8.
 */
@Repository("fileSaveDao")
public class FileSaveDaoImpl extends BaseDaoImpl<FileSave,Integer> implements FileSaveDao {
}
