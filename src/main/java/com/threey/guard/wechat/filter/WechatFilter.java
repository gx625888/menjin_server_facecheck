package com.threey.guard.wechat.filter;


import com.threey.guard.base.util.GETProperties;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class WechatFilter implements Filter {
	Logger logger=Logger.getLogger(WechatFilter.class);


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)throws IOException, ServletException {
		if ("debug".equals(GETProperties.readValue("wechat.state"))){//debug模式下,该过滤器不做任何操作
			if (null !=chain && null !=servletRequest && null !=servletResponse){
				chain.doFilter(servletRequest, servletResponse);
			}
			return ;
		}

    	HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = request.getServletPath();
        logger.info("WechatFilter请求URL="+url);
        try {
            String userAgent = ((HttpServletRequest) request).getHeader("user-agent" ).toLowerCase(); 
            if (userAgent .indexOf("micromessenger") < 0) {// 不是微信浏览器
            	String str = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
                        + "<html>"
                        + "<head>"
                       + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
                       + "<script type='text/javascript'>"
                       + "alert('请在微信端扫描！');</script>"
                       + "</head>"
                       + "</html>";
                        response.setCharacterEncoding( "utf8");
                          response .setContentType("text/html;charset=utf-8");
                       PrintWriter out = response.getWriter();
                        out.print( str);
                        out.flush();
                        return;
            }

        
        //在请求后拼接参数 start
        String para = null;
        String uri = "";
        @SuppressWarnings("unchecked")
		Enumeration<String> e = request.getParameterNames(); 
        while(e.hasMoreElements()) { 
            para = e.nextElement(); 
            if(para != null){
            	//过滤微信端回调我们时所带的code参数，避免获取code时出现多个code，造成错误!
            	if(!"code".equals(para)){
            		uri += para+"="+request.getParameter(para)+"&";
            	}
            } 
        }
        if(!"".equals(uri)){
        	uri = uri.substring(0, uri.length()-1);
        	uri = url+"?"+uri;
        }else {
        	uri = url;
		}
        logger.info("请求URL="+uri);

        boolean flag = true;
        String[] urlAttr = new String[]{"/wechat/index.act"
        	};
        for(String value:urlAttr){
        	if(value.equals(url)){
        		 flag = false;
        	}
        }

        if (flag){//
        	String userOpenId = (String) request.getSession().getAttribute("wechat_user_open_id");
        	if (StringUtils.isEmpty(userOpenId)){
        		response.sendRedirect("/wechat/index.act");
			}
		}



        //refresh token
        if (null !=chain && null !=servletRequest && null !=servletResponse){
       	   chain.doFilter(servletRequest, servletResponse);
        }
       }catch (Exception e) {
    	   logger.error("错误日志："+e);
    	   e.printStackTrace();
	   }
    }
    
    


    private static String REQUEST_GET_TOKEN_URL2="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	public String getAccessToken(String appid, String appSecret) throws Exception {
		String urlStr = String.format(REQUEST_GET_TOKEN_URL2, appid, appSecret);
		logger.info("tokenurl:"+urlStr);
		URL url = new URL(urlStr);
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer stringBuffer = new StringBuffer(255);
			synchronized (stringBuffer) {
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line);
					stringBuffer.append("\n");
				}
				return stringBuffer.toString();
			}
		} catch (Exception e) {
			logger.info("获取微信token失败！");
			e.printStackTrace();
			return "-1";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
    public void destroy() {}
}
