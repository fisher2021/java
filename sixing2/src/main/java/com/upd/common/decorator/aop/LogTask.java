package com.upd.common.decorator.aop;

import com.alibaba.fastjson.JSON;
import com.upd.auth.entity.Log;
import com.upd.auth.service.LogService;
import com.upd.business.entity.Article;
import com.upd.business.entity.Score;
import com.upd.business.entity.User;
import com.upd.business.service.*;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.rest.LogType;
import com.upd.common.util.TokenUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 异步执行
 * 日志任务
 * Created by hui on 2017/3/23.
 */
public class LogTask implements Runnable{

    private static LogService logService;
    private static TrackService trackService;
    private static BacklogUserService backlogUserService;

    private List<Object> args;
    private String path;
    private String token;

    public LogTask(List<Object> args, String path, String token) {
        this.args=args;
        this.path=path;
        this.token=token;
    }

    //手动注入service
    static {
        logService=(LogService) SpringBeanUtil.getBeanByName("logService");
        trackService=(TrackService) SpringBeanUtil.getBeanByName("trackService");
        backlogUserService=(BacklogUserService) SpringBeanUtil.getBeanByName("backlogUserService");
    }

    @Override
    public void run() {
        //积分
        logService.points(token, path, args);
        //党迹
        trackService.tracks(token, path, args);
        //待办事项
        backlogUserService.backlogs(token, path, args);
    }
}
