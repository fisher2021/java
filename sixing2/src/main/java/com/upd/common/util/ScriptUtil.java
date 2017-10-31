package com.upd.common.util;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14.
 * 页面操作的脚步操作
 */
public class ScriptUtil {
    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
           Cookie cookie = new Cookie(name,value);
           cookie.setPath("/");
        	    if(maxAge>0)  cookie.setMaxAge(maxAge);
        	    response.addCookie(cookie);
        	}

    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }
    }
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static String getCookieValueByName(HttpServletRequest request,String name){
        Cookie cookie=  getCookieByName(request,name);
        if(cookie==null){
        return null;
        }else{
            return cookie.getValue();
        }
    }

    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
