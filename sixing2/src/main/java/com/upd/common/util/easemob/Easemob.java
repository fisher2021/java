package com.upd.common.util.easemob;

import com.alibaba.fastjson.JSON;
import com.upd.common.util.HttpKit;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/5/10.
 */
public class Easemob {

    public static final String ORG_NAME;
    public static final String APP_NAME;
    public static final String GRANT_TYPE;
    public static final String CLIENT_ID;
    public static final String CLIENT_SECRET;
    public static final String URL;
    public static final String PARTY_MEMBER_ID;
    public static final String PARTY_BRANCH_ID;
    public static final String PARTY_GROUP_ID;
    public static final String DEMOCRATIC_LIFE_ID;
    private static Map<String, String> headers=new HashMap<String, String>();

    public static final Logger logger = LoggerFactory.getLogger(Easemob.class);
    private static ResourceBundle resourceBundle;

    static{
        //读取配置文件
        resourceBundle = ResourceBundle.getBundle("easemob");
        ORG_NAME = getConfig("ORG_NAME");
        APP_NAME = getConfig("APP_NAME");
        GRANT_TYPE =getConfig("GRANT_TYPE");
        CLIENT_ID =getConfig("CLIENT_ID");
        CLIENT_SECRET =getConfig("CLIENT_SECRET");
        URL =getConfig("URL");
        PARTY_MEMBER_ID = getConfig("PARTY_MEMBER_ID");
        PARTY_BRANCH_ID = getConfig("PARTY_BRANCH_ID");
        PARTY_GROUP_ID = getConfig("PARTY_GROUP_ID");
        DEMOCRATIC_LIFE_ID = getConfig("DEMOCRATIC_LIFE_ID");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+token());
    }
    
    /**
     * 通过key获取properties中对应的值
     * @param key
     * @return
     */
    public static String getConfig(String key){
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }

    public static String token(){
        String url= MessageFormat.format(URL+getConfig("TOKEN"),ORG_NAME,APP_NAME);
        Map<String,String> data=new HashedMap();
        data.put("grant_type",GRANT_TYPE);
        data.put("client_id",CLIENT_ID);
        data.put("client_secret",CLIENT_SECRET);
        String result=HttpKit.post(url, JSON.toJSONString(data));
        System.out.println("token:"+result);
        Map<String,String> map=JSON.parseObject(result,Map.class);
        return map.get("access_token");
    }


    /**
     * 创建用户
     * @param userList 用户集合[{username,password}]
     */
    public static void createUser(List userList) {
        String url= MessageFormat.format(URL+getConfig("USER"),ORG_NAME,APP_NAME);
        System.out.println(userList);
        HttpKit.post(url, JSON.toJSONString(userList),headers);
    }


    /**
     * 修改密码
     * @param username
     * @param pwd
     */
    public static void newPassword(String username,String pwd) {
        String url= MessageFormat.format(URL+getConfig("USER_NEWPASSWORD"),ORG_NAME,APP_NAME,username);
        System.out.println("url:"+url);
        Map<String, String> data=new HashMap<String, String>();
        data.put("newpassword",pwd);
        System.out.println("pwd:"+pwd);
        HttpKit.post(url, JSON.toJSONString(data),headers);
    }

    public static void upNickName(String username,String nickname) {
        String url= MessageFormat.format(URL+getConfig("USER_UPNICKNAME"),ORG_NAME,APP_NAME,username);
        Map<String, String> data=new HashMap<String, String>();
        data.put("nickname",nickname);
        HttpKit.put(url, JSON.toJSONString(data),headers);
    }

    public static void deleteUser(String username) {
        String url= MessageFormat.format(URL+getConfig("USER_UPNICKNAME"),ORG_NAME,APP_NAME,username);
        System.out.println(url);
        Map<String, String> data=new HashMap<String, String>();
        HttpKit.delete(url,JSON.toJSONString(data),headers);
    }

    /**
     * 判断用户是否存在
     * @param username
     */
    public static boolean existUser(String username) {
        String url= MessageFormat.format(URL+getConfig("USER_UPNICKNAME"),ORG_NAME,APP_NAME,username);
        System.out.println(url);
        try {
            HttpKit.get(url,headers);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**===============================================群组================================================*/
    /**
     * 新增群
     * @param name 群名称
     * @param desc 描述
     * @param p 是否公开
     * @param maxusers 成员最大数
     * @param owner 管理员
     */
    public static String createGroup(String name,String desc,Boolean p,Integer maxusers,String owner){
        Map<String, Object> data=new HashMap<String, Object>();
        data.put("name",name);
        data.put("desc",desc);
        data.put("public",p);
        data.put("maxusers",maxusers);
        data.put("owner",owner);
        String url = MessageFormat.format(URL +getConfig("GROUP"), ORG_NAME, APP_NAME);
        String result = HttpKit.post(url, JSON.toJSONString(data), headers);
        System.out.println(result);
        return result;
    }

    public static void deleteGroup(String group_id){
        String url = MessageFormat.format(URL +getConfig("GROUP_NEWOWNER"), ORG_NAME, APP_NAME,group_id);
        HttpKit.delete(url, "", headers);
    }
    /**
     * 新增群组成员
     * @param group_id 群id
     * @param users 用户
     */
    public static void addGroupUser(String group_id,Map users) {
        System.out.println(JSON.toJSONString(users));
        String url = MessageFormat.format(URL +getConfig("GROUP_BATCH_ADDUSER"), ORG_NAME, APP_NAME, group_id);
        HttpKit.post(url, JSON.toJSONString(users), headers);
    }
    /**
     * 删除群组成员
     * @param group_id 群id
     * @param username 用户
     */
    public static void removeUser(String group_id,String username) {
        String url = MessageFormat.format(URL +getConfig("GROUP_ADDUSER"), ORG_NAME, APP_NAME, group_id,username);
        HttpKit.delete(url, "", headers);
    }
    /**
     * 批量删除群组成员
     * @param group_id 群id
     * @param users 用户
     */
    public static void deleteGroupUser(String group_id,String users) {
        String url = MessageFormat.format(URL +getConfig("GROUP_BATCH_DELETEUSER")+users, ORG_NAME, APP_NAME, group_id);
        HttpKit.delete(url, "", headers);
    }
    /**
     * 转让群主
     * @param group_id
     * @param username
     */
    public static void newGroupOwner(String group_id,String username) {
        Map<String, Object> data=new HashMap<String, Object>();
        data.put("newowner",username);
        String url = MessageFormat.format(URL +getConfig("GROUP_NEWOWNER"), ORG_NAME, APP_NAME, group_id);
        String result = HttpKit.put(url, JSON.toJSONString(data), headers);
        System.out.println(result);
    }
}
