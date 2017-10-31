package com.upd.common.annotation;

import java.lang.reflect.Method;

public class ParseAnnotation {
    public String  parseMethod(Method method){
	   NeedInterceptor needInterceptor = method.getAnnotation(NeedInterceptor.class);
	   if(needInterceptor==null){
		   return null;
	   }
       String value = "";
       if(needInterceptor != null){
    	   value = needInterceptor.value();
       }
       return value;
     }
}
