package com.threey.guard.base.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class HttpserviceFilter implements Filter {
	Logger logger=Logger.getLogger(HttpserviceFilter.class);
	
	
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = request.getServletPath();
        logger.info("HttpserviceFilter接口URL="+url);
        
        //在请求后拼接参数 start
        String para = null;
        String uri = "";
        @SuppressWarnings("unchecked")
        Enumeration<String> e = request.getParameterNames();
        while(e.hasMoreElements()) { 
            para = e.nextElement(); 
            if(para != null){
            	uri += para+"="+request.getParameter(para)+"&";
            } 
        }
        if(!"".equals(uri)){
        	uri = uri.substring(0, uri.length()-1);
        	uri = url+"?"+uri;
        }else {
        	uri = url;
		}
        logger.info("请求URL="+uri);
        //在请求后拼接参数 end
        
        chain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {}
}
