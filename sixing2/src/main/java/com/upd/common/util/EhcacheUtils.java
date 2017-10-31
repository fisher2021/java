package com.upd.common.util;

import com.alibaba.fastjson.JSON;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.dao.DataRetrievalFailureException;

/**
 * 缓存工具
 * Created by Administrator on 2017/5/4.
 */
public class EhcacheUtils {

    private final static String USER_CACHE="USER";
    private static Cache userCache= CacheManager.getInstance().getCache(USER_CACHE);

    public synchronized static void putToken(String account, String token){
        Element element=new Element(account, token);
        userCache.put(element);
    }

    public synchronized static String getToken(String account){
        try {
            Element element =userCache.get(account);
            if(null!=element) {
                return element.getObjectValue().toString();
            }
        } catch (CacheException cacheException) {
            return null;
        }
        return null;
    }

}
