package com.upd.common.decorator.interceptor;

import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.util.TokenUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口拦截器
 * Created by Administrator on 2017/5/4.
 */
public class RestInterceptor extends HandlerInterceptorAdapter {
///login
    private static final String[] IGNORE_URL = {"/login","/download","/callback"};
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURL().toString();
        //不拦截上面定义的路径
        for (String str : IGNORE_URL) {
            if (url.contains(str)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
//            //先查缓存中是否存在 不存在抛出 token已失效
            request.setAttribute("urlPath",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
            if(null!=TokenUtils.verify(request.getHeader("token"))){
                return true;
            }else{
                throw new BusinessException(RestErrorCode.TOKEN_EXPIRED);
            }
        }
        return true;
    }
}
