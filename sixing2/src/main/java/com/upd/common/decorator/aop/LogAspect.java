package com.upd.common.decorator.aop;

import com.upd.common.util.pay.wxpay.util.XMLUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 日志切面
 * Created by Administrator on 2017/6/2.
 */
@Aspect    //该标签把LogAspect类声明为一个切面
@Order(1)  //设置切面的优先级：如果有多个切面，可通过设置优先级控制切面的执行顺序（数值越小，优先级越高）
@Component
public class LogAspect {

    @Autowired
    private TaskExecutor taskExecutor;

    @Pointcut("execution(public * com.upd.business.controller.rest.*Controller.*(..))")
    public void declearJoinPointExpression(){}


//    @Before("declearJoinPointExpression()") //该标签声明次方法是一个前置通知：在目标方法开始之前执行
//    public void beforMethod(JoinPoint joinPoint){
////        String methodName = joinPoint.getSignature().getName();
////        List<Object> args = Arrays.asList(joinPoint.getArgs());
////        System.out.println("this method "+methodName+" begin. param<"+ args+">");
//    }

    @Autowired
    private  HttpServletRequest request;

    @AfterReturning(value="declearJoinPointExpression()",returning="result")
    public void afterReturnMethod(JoinPoint joinPoint,Object result) throws IOException {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
//        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        this.taskExecutor.execute(new LogTask(args, request.getServletPath(), request.getHeader("token")));
    }
}
