package com.upd.business.service.impl;

import com.upd.business.dao.MessageDao;
import com.upd.business.entity.Message;
import com.upd.business.entity.User;
import com.upd.business.form.MessageForm;
import com.upd.business.service.MessageService;
import com.upd.business.service.UserService;
import com.upd.common.basis.service.impl.BaseServiceImpl;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.xinge.Xinge;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ljw on 2017/5/4.
 */
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message, Integer> implements MessageService {
    @Autowired
    public MessageDao messageDao;
    @Autowired
    private UserService userService;
    @Autowired
    public void setBaseDao(MessageDao dao) {
        this.baseDao = dao;
    }
    @Override
    public Pagination page(Pagination page, MessageForm form) {
        Pagination pageRsult = messageDao.findbypage(page,"from Message where"+form.toQueryDescription(),form.values());
        return pageRsult;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            messageDao.delete(messageDao.get(Integer.parseInt(id)));
        }
    }

    @Override
    public void add(Message message) {
        message.initTime();
        messageDao.save(message);
    }

    @Override
    public void push(Message message,Integer[] orgs) {
        if (orgs != null && orgs.length != 0){
            for (Integer orgId:orgs){
                List<User> users = userService.findByORG(orgId);
                List<String> accounts = new ArrayList<>();
                for (User user : users){
                    if (user.getAccount() != null){
                        accounts.add(user.getAccount());
                    }
                }
                if (accounts.size() < 100){
                    //批量推送
                    Xinge.pushBatchAndroid(accounts,message.getTitle(),message.getContent());
                    Xinge.pushBatchIos(accounts,message.getTitle(),message.getContent());
                }else {
                    //大批量推送
                    Xinge.pushMultipushAndroid(accounts,message.getTitle(),message.getContent());
                    Xinge.pushMultipushIos(accounts,message.getTitle(),message.getContent());
                }
                //按组织标签推送
//                Xinge.pushTagAndroid(message.getTitle(),message.getContent(),org);
//                Xinge.pushTagIos(message.getTitle(),message.getContent(),org);
            }
        }else {
            //全部推送
            Xinge.pushAllAndroid(message.getTitle(),message.getContent());
            Xinge.pushAllIos(message.getTitle(),message.getContent());
        }
    }

    @Override
    public void addBatch(Integer messageId, Integer[] ids) {
        Message message = messageDao.get(messageId);
        String orgs = "";
        if (message.getOrg() != null && !message.getOrg().equals("")){
            orgs = message.getOrg()+",";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(orgs);
        for (int i=0;i<ids.length;i++){
            sb.append(ids[i]);
            if (i != ids.length-1){
                sb.append(",");
            }
        }
        message.setOrg(sb.toString());
        message.initTime();
        messageDao.update(message);
    }

    @Override
    public void deleteORG(Integer messageId, Integer id) {
        Message message = messageDao.get(messageId);
        String orgs = message.getOrg();
        String[] arry = orgs.split(",");
        for (int i=0;i<arry.length;i++) {
            if (arry[i].equals(id.toString())) {
                int index = orgs.indexOf(arry[i]);
                String left = "";
                String right = "";
                if (arry.length != 1){
                    if (i == arry.length-1 ){
                        left = orgs.substring(0, index-1);
                    }else{
                        left = orgs.substring(0, index);
                        right = orgs.substring(index + arry[i].length()+1, orgs.length());
                    }
                }
                message.setOrg(left.concat(right));
                break;
            }
        }
        message.initTime();
        messageDao.update(message);
    }
}
