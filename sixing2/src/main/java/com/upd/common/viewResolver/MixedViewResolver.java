/**
 * <b>文件名：</b>MixedViewResolver.java<br/>  
 * <b>版本信息：</b><br/>  
 * <b>日期：2015-05-14
 */
package com.upd.common.viewResolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

/**
 * <b>类名称：</b>MixedViewResolver<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：张守兵
 * <b>修改时间：<2015/5/14.
 * @version 1.0.0<br/>
 */
public class MixedViewResolver implements ViewResolver {
	private Map<String, ViewResolver> resolvers;

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		int n = viewName.lastIndexOf(".");
		if (n != -1) {
			// 取出扩展名
			String suffix = viewName.substring(n + 1);
			// 取出对应的ViewResolver
			ViewResolver resolver = resolvers.get(suffix);
			if (resolver == null) {
				throw new RuntimeException("No viewResolver for " + suffix);
			}
			viewName=viewName.substring(0,n);
			return resolver.resolveViewName(viewName, locale);
		} else {
			ViewResolver resolver = resolvers.get("jsp");
			return resolver.resolveViewName(viewName, locale);
		}
	}
}
