package com.upd.business.service.impl;

import com.upd.business.dao.DataDao;
import com.upd.business.entity.Data;
import com.upd.business.form.DataForm;
import com.upd.business.service.DataService;
import com.upd.business.service.UserService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
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
@Service("dataService")
public class DataServiceImpl extends BaseServiceImpl<Data, Integer> implements DataService {
    @Autowired
    private DataDao dataDao;
    @Autowired
    public void setBaseDao(DataDao dao) {
        this.baseDao = dao;
    }

    @Override
    public Data add(MultipartFile file, String folder,Data data) throws IOException {
        if(file != null){
            //上传到文件服务器
            String fileName = file.getOriginalFilename();
            String strs[] = fileName.split("[.]");
            String suffix = strs[strs.length - 1];
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(date);
            String path = OtherConfigBundle.getConfig("upload_url")+folder+time;
            UUID uuid = UUID.randomUUID();
            String newFile = uuid + "."+ suffix;
            File targetFile = new File(path, newFile);
            data.setPath(targetFile.toString());
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);
            dataDao.save(data);
            String imageUrl = "/rest/user/download?fileName=" + data.getId();
            data.setUrl(imageUrl);
            data.initTime();
            dataDao.update(data);
            return data;
        }
        return null;
    }

    @Override
    public Pagination getPage(Pagination page, DataForm form) {
        return dataDao.findbypage(page,"from Data where"+form.toQueryDescription(),form.values());
    }

    @Override
    public Pagination getPageAPP(Pagination page, Integer userId) {
        return dataDao.findbyPageSQL(page,"select data_id from data_user where user_id = 1");
    }
    @Override
    public void edit(Data data) {
        data.initTime();
        dataDao.update(data);
    }

}
