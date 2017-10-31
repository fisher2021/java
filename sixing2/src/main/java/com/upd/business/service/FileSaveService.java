package com.upd.business.service;

import com.upd.business.entity.FileSave;
import com.upd.common.basis.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by ljw on 2017/5/8.
 */
public interface FileSaveService extends BaseService<FileSave,Integer> {

    /**
     * 上传图片
     * @param file 上传文件
     * @param folder 文件夹
     * @return imageUrl 文件路径
     */
    FileSave add(MultipartFile file,String folder) throws IOException;
    /**
     * 上传EXCEL
     * @param file 上传文件
     * @param folder 文件夹
     * @return
     */
    File addExcel(MultipartFile file, String folder) throws IOException;

}
