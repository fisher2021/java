package com.upd.common.decorator.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//过滤器 对请求进行过滤 及其相应操作
public class AuthFilter implements Filter {	
	
	private final String errorPath = "/login.html";
    static final String[] MAIN_URL= {"/auth/"};
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("AuthFilter 调用init");
	}

	public void destroy() {
		System.out.println("AuthFilter 调用destroy");
	}


	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
	       boolean innerroot=false;
	       String path = request.getRequestURI();
	       
	    	for (String tmpurl: MAIN_URL){
	    		if (path.contains(tmpurl) || path.contains(tmpurl.toUpperCase()) || path.contains(tmpurl.toLowerCase())){
	    			innerroot=true;
	    			}
	    		}

				 chain.doFilter(request, response);


	}
}
