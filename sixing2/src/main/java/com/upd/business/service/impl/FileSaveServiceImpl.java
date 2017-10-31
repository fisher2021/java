package com.upd.business.service.impl;

import com.upd.business.dao.FileSaveDao;
import com.upd.business.entity.FileSave;
import com.upd.business.service.FileSaveService;
import com.upd.business.service.UserService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.FileUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ljw on 2017/5/8.
 */
@Service("fileSaveService")
public class FileSaveServiceImpl extends BaseServiceImpl<FileSave, Integer> implements FileSaveService{
    @Autowired
    private FileSaveDao fileSaveDao;
    @Autowired
    public void setBaseDao(FileSaveDao dao) {
        this.baseDao = dao;
    }
    @Autowired
    private UserService userService;
    @Override
    public FileSave add(MultipartFile file, String folder) throws IOException {
        if(file != null){
            //上传到文件服务器
            String fileName = file.getOriginalFilename();
            String strs[] = fileName.split("[.]");
            String suffix = strs[strs.length - 1];
            if (!FileUtils.checkIsImage("."+ suffix)){
                throw new BusinessException(RestErrorCode.PARAM,"请上传gif、png、jpg、jpeg格式文件！");
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(date);
            String path = OtherConfigBundle.getConfig("upload_url")+folder+time;
            FileSave save = new FileSave();
            UUID uuid = UUID.randomUUID();
            String newFile = uuid + "."+ suffix;
            File targetFile = new File(path, newFile);
            save.setPath(targetFile.toString());
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);
            Thumbnails.of(targetFile).size(1000,1000).toFile(targetFile);//变为400*300,遵循原图比例缩或放到400*某个高度
            fileSaveDao.save(save);
            String imageUrl = "/rest/user/download?fileName=" + save.getId();
            save.setUrl(imageUrl);
            save.initTime();
            fileSaveDao.update(save);
            return save;
        }
        return null;
    }

    @Override
    public File addExcel(MultipartFile file, String folder)throws IOException {
        if(file != null){
            //上传到文件服务器
            String fileName = file.getOriginalFilename();
            String strs[] = fileName.split("[.]");
            String suffix = strs[strs.length - 1];
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(date);
            String path = OtherConfigBundle.getConfig("upload_url")+folder+time;
            FileSave save = new FileSave();
            UUID uuid = UUID.randomUUID();
            String newFile = uuid + "."+ suffix;
            File targetFile = new File(path, newFile);
            save.setPath(targetFile.toString());
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);
            return targetFile;
        }
        return null;
    }


}
