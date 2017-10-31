package com.upd.business.service;

import com.upd.business.entity.Data;
import com.upd.business.entity.FileSave;
import com.upd.business.form.DataForm;
import com.upd.common.basis.service.BaseService;
import com.upd.common.util.page.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by ljw on 2017/5/8.
 */
public interface DataService extends BaseService<Data,Integer> {

    /**
     * 上传资料
     * @param file 上传文件
     * @param folder 文件夹
     * @return
     */
    Data add(MultipartFile file, String folder, Data data) throws IOException;

    /**
     * 分页查询
     * @param page
     * @return
     */
    Pagination getPage(Pagination page, DataForm form);
    /**
     * 分页查询APP
     * @param page
     * @return
     */
    Pagination getPageAPP(Pagination page, Integer userId);
    /**
     * 编辑资料
     * @param data
     */
    void edit(Data data);
}
