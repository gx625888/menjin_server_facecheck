package com.threey.guard.base.filter;

import com.threey.guard.base.util.Constants;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebappFilter implements Filter {
	Logger logger = Logger.getLogger(WebappFilter.class);


	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String url = request.getServletPath();
		logger.info("------请求URL地址：" + url + "------");
        String[] urlAttr = new String[]{"/login/toLoginPage.shtml"
        		,"/login/systemOut.shtml"
        		,"/login/systemLogin.shtml"
        		,"/crm/doCRMLogin.shtml"};
		if (!inGroup(urlAttr, url)) {
			if (request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER) == null) {
				response.sendRedirect("../login/toLoginPage.shtml");
				return;
			}
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	private boolean inGroup(String[] so, String tar) {
		for (int a = 0; a < so.length; a++) {
			if (ifEquals(so[a], tar)) {
				return true;
			}
		}
		return false;
	}

	private boolean ifEquals(String ff, String tt) {
		if (ff.endsWith(".shtml")) {
			return ff.equals(tt);
		} else {
			String temp = tt;
			temp = temp.substring(1, temp.length());
			String packageName = "/";
			if (temp.indexOf("/") >= 0) {
				packageName = temp.substring(0, temp.indexOf("/"));
			}
			return packageName.equals(ff);
		}
	}

	/**
	 * 
	 * @相关说明：重cookie中获取mid
	 * @开发者：汤云涛
	 * @时间：2015年8月20日 下午3:54:25
	 */
	private String getMidFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		String mid = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (Constants.CookieCode.PC_MID.equals(cookie.getName())) {
					mid = cookie.getValue();
				}
			}
		}
		return mid;
	}

	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
