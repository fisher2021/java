package com.upd.common.util.xinge;


import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.tencent.xinge.ClickAction;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.Style;
import com.tencent.xinge.TimeInterval;
import com.tencent.xinge.XingeApp;
import com.tencent.xinge.TagTokenPair;
/**
 * Created by admin on 2017/6/2.
 */
public class Xinge {
    public static final Long IOS_SUPPLY_ACCESS_ID;
    public static final String IOS_SUPPLY_SECRET_KEY;
    public static final Long ANDROID_SUPPLY_ACCESS_ID;
    public static final String ANDROID_SUPPLY_SECRET_KEY;
    public static final Integer IOS_ENV;
    public static final Integer DIVICE_TYPE;
    public static final Logger logger = LoggerFactory.getLogger(Xinge.class);
    private static ResourceBundle resourceBundle;
    static{
        //读取配置文件
        resourceBundle = ResourceBundle.getBundle("xinge");
        IOS_SUPPLY_ACCESS_ID = Long.valueOf(getConfig("IOS_SUPPLY_ACCESS_ID"));
        IOS_SUPPLY_SECRET_KEY = getConfig("IOS_SUPPLY_SECRET_KEY");
        ANDROID_SUPPLY_ACCESS_ID = Long.valueOf(getConfig("ANDROID_SUPPLY_ACCESS_ID"));
        ANDROID_SUPPLY_SECRET_KEY = getConfig("ANDROID_SUPPLY_SECRET_KEY");
        IOS_ENV = Integer.valueOf(getConfig("IOS_ENV"));
        DIVICE_TYPE = Integer.valueOf(getConfig("DIVICE_TYPE"));
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

    /**
     * 安卓单个设备推送
     * @param title
     * @param content
     * @param token
     */
    public static JSONObject pushTokenAndroid(Long accessId,String secretKey,String title , String content, String token){
        //自定义推送
        MessageIOS message = new MessageIOS();
        message.setAlert(content);
        message.setBadge(1);
        message.setSound("notice.mp3");
        XingeApp xinge = new XingeApp(accessId, secretKey);
        JSONObject ret = xinge.pushSingleDevice(token, message, IOS_ENV);
        return ret;
    }

    /**
     * ios单个设备推送
     * @param content
     * @param token
     * @return
     */
    public static JSONObject pushTokenIos(Long accessId,String secretKey,String content, String token){
        //自定义推送
        MessageIOS message = new MessageIOS();
        message.setAlert(content);
        message.setBadge(1);
        message.setSound("notice.mp3");
        XingeApp xinge = new XingeApp(accessId, secretKey);
        JSONObject ret = xinge.pushSingleDevice(token, message, IOS_ENV);
        return ret;
    }

    /**
     * 安卓全设备推送
     * @param title
     * @param content
     * @return
     */
    public static JSONObject pushAllAndroid(String title,String content){
        System.out.println(ANDROID_SUPPLY_ACCESS_ID);
        System.out.println(ANDROID_SUPPLY_SECRET_KEY);
        return XingeApp.pushAllAndroid(ANDROID_SUPPLY_ACCESS_ID, ANDROID_SUPPLY_SECRET_KEY, title, content);
    }

    /**
     * ios全设备推送
     * @param content
     * @return
     */
    public static JSONObject pushAllIos(String title,String content){
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("body",content);
        MessageIOS message = new MessageIOS();
        JSONObject json = new JSONObject(map);
        message.setAlert(json);
        message.setBadge(1);
        message.setSound("beep.wav");
        XingeApp xinge = new XingeApp(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY);
        JSONObject ret = xinge.pushAllDevice(DIVICE_TYPE, message, IOS_ENV);
        return ret;
    }

    /**
     * 安卓批量账号推送
     * @param title
     * @param content
     * @return
     */
    public static JSONObject pushBatchAndroid(List<String> accounts,String title,String content){
        JSONArray account_list = JSONArray.fromObject(accounts);
        Message message = new Message();
        message.setType(1);
        message.setTitle(title);
        message.setContent(content);
        XingeApp xinge = new XingeApp(ANDROID_SUPPLY_ACCESS_ID, ANDROID_SUPPLY_SECRET_KEY);
        JSONObject ret = xinge.pushAccountList(DIVICE_TYPE,account_list, message);
        return ret;
    }

    /**
     * ios批量账号推送
     * @param content
     * @return
     */
    public static JSONObject pushBatchIos(List<String> accounts,String title,String content){
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("body",content);
        MessageIOS message = new MessageIOS();
        JSONObject json = new JSONObject(map);
        message.setAlert(json);
        message.setBadge(1);
        message.setSound("beep.wav");
        XingeApp xinge = new XingeApp(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY);
        JSONObject ret = xinge.pushAccountList(DIVICE_TYPE,accounts, message, IOS_ENV);
        return ret;
    }
    /**
     * 安卓大批量账号推送
     * @param title
     * @param content
     * @return
     */
    public static JSONObject pushMultipushAndroid(List<String> accounts,String title,String content){
        Message message = new Message();
        message.setType(1);
        message.setTitle(title);
        message.setContent(content);
        XingeApp xinge = new XingeApp(ANDROID_SUPPLY_ACCESS_ID, ANDROID_SUPPLY_SECRET_KEY);
        JSONObject ret = xinge.createMultipush(message);
        if (ret.get("ret_code").equals("0")){
            JSONObject jsonObject = (JSONObject) ret.get("result");
            long push_id = Long.valueOf(jsonObject.get("push_id").toString());
            return xinge.pushAccountListMultiple(push_id,accounts);
        }
        return null;
    }

    /**
     * ios大批量账号推送
     * @param content
     * @return
     */
    public static JSONObject pushMultipushIos(List<String> accounts,String title,String content){
        JSONArray account_list = JSONArray.fromObject(accounts);
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("body",content);
        MessageIOS message = new MessageIOS();
        JSONObject json = new JSONObject(map);
        message.setAlert(json);
        message.setBadge(1);
        message.setSound("beep.wav");
        XingeApp xinge = new XingeApp(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY);
        JSONObject ret = xinge.createMultipush(message,IOS_ENV);
        if (ret.get("ret_code").equals("0")){
            JSONObject jsonObject = (JSONObject) ret.get("result");
            long push_id = Long.valueOf(jsonObject.get("push_id").toString());
            return xinge.pushAccountListMultiple(push_id,account_list);
        }
        return null;
    }
    /**
     * 安卓单个账号推送
     */
    public static JSONObject pushSingleAccountAndroid(String account, String title,String content){
        //自定义推送
        Message message = new Message();
        message.setType(1);
        message.setTitle(title);
        message.setContent(content);
        XingeApp xinge = new XingeApp(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY);
        return xinge.pushSingleAccount(DIVICE_TYPE, account, message);
    }

    /**
     * ios单个设备推送
     * @return
     */
    public static JSONObject pushSingleAccountIos(String account, String title,String content){
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("body",content);
        MessageIOS message = new MessageIOS();
        JSONObject json = new JSONObject(map);
        message.setAlert(json);
        message.setBadge(1);
        message.setSound("beep.wav");
        XingeApp xinge = new XingeApp(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY);
        return xinge.pushSingleAccount(DIVICE_TYPE,account, message, IOS_ENV);
    }

    /**
     * Android 平台推送消息给标签选中设备
     */
    public static JSONObject pushTagAndroid(String title,String content,String tag){
        return XingeApp.pushTagAndroid(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY,title,content,tag);
    }

    /**
     * IOS  平台推送消息给标签选中设备
     */
    public static JSONObject pushTagIos(String title,String content,String tag){
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("body",content);
        MessageIOS message = new MessageIOS();
        JSONObject json = new JSONObject(map);
        message.setAlert(json);
        message.setBadge(1);
        message.setSound("beep.wav");
        ArrayList tagList = new ArrayList();
        tagList.add(tag);
        XingeApp xinge = new XingeApp(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY);
        return xinge.pushTags(DIVICE_TYPE,tagList,"OR", message,IOS_ENV);
    }

    public static void main(String[] args) {
        //安卓推送
//        System.out.println(XingeApp.pushTokenAndroid(ANDROID_SUPPLY_ACCESS_ID, ANDROID_SUPPLY_SECRET_KEY, "标题", "内容", "ad9a08dc06794d29487fab39e6e0db8ccecdd831"));
//        System.out.println(XingeApp.pushAllAndroid(ANDROID_SUPPLY_ACCESS_ID, ANDROID_SUPPLY_SECRET_KEY, "标题", "内容"));
//        System.out.println(pushAllAndroid( "标题1", "内容1"));
//        System.out.println(pushSingleAccountAndroid("15757854518","标题", "内容"));


        //苹果推送
//        System.out.println(XingeApp.pushTokenIos(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY, "内容", "ad9a08dc06794d29487fab39e6e0db8ccecdd831", IOS_ENV));
//        System.out.println(XingeApp.pushAllIos(IOS_SUPPLY_ACCESS_ID, IOS_SUPPLY_SECRET_KEY, "内容", XingeApp.IOSENV_PROD));
        List<String> accounts = new ArrayList<>();
        accounts.add("15757854518");
        System.out.println(pushBatchIos(accounts,"标题2","内容2"));
//        System.out.println(pushMultipushIos(accounts,"标题a","内容a"));
//        System.out.println(pushAllIos("标题a","内容a"));
//        System.out.println(pushTagIos("标题","内容","1"));



    }
}
