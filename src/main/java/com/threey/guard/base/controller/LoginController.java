package com.threey.guard.base.controller;

import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.domain.Menu;
import com.threey.guard.base.service.LoginService;
import com.threey.guard.base.service.RedisService;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.DateTimeUtil;
import com.threey.guard.base.util.GETProperties;
import com.threey.guard.base.util.JsonUtil;
import com.threey.guard.manage.dao.ManagerUserDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/login/*")
public class LoginController extends BaseController {
	 private static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ManagerUserDAO dao;

	@RequestMapping("toLoginPage.shtml")
	public String toLoginPage() {
		return "login";
	}

	/**相关说明：用户登录
	 * 开发者：zhaoliang
	 * 时间：2015年7月3日 下午5:16:26
	 */
	@RequestMapping("systemLogin.shtml")
	public String systemLogin(HttpServletRequest request, HttpServletResponse response, ManagerUser mu) {
		try {
			request.getSession().setAttribute("extendField2", mu.getPassword());
			request.getSession().setAttribute("sendLogin", GETProperties.readValue("SEND_CRM_URL"));
			int ret = loginService.systemLogin(request, mu);
			if (ret == 0) {
				logger.info("#####mid："+mu.getMid());

				
				/*String userIdischeck=mu.getUserIdischeck();
				String midischeck=mu.getMidischeck();
				if(userIdischeck==null){
					//移除cookie
					Cookie cookie = new Cookie("userId", null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}else if(userIdischeck.equals("y")){
					//cookie添加
					addUserIdCookie(response,mu.getUserId());
				}
				if(midischeck==null){
					//移除cookie
					Cookie cookie = new Cookie(Constants.CookieCode.PC_MID, null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}else if(midischeck.equals("y")){
					//cookie添加
					addMid2Cookie(response, mu.getMid());
				}*/
				return "redirect:index.shtml";
			} else if (ret == 1) {
				request.setAttribute("msg", "账号或密码不正确!");
				return "login";
			} else {
				return "login";
			}
		} catch (Exception e) {
			super.error(request, e);
			return "common/error";
		}
	}

	@RequestMapping("welcome.shtml")
	public String welcome(HttpServletResponse response) throws IOException {
		return "welcome";
	}
	@RequestMapping("index.shtml")
	public String index(HttpServletRequest req,HttpServletResponse response) throws IOException {

		List<Menu> menus = this.loginService.initMenus(req);
		if (menus==null||menus.size()==0){
			req.setAttribute("msg", "您的账号没有任何权限!");
			req.getSession().invalidate();
			return "login";
		}
		req.setAttribute("menus",JsonUtil.getJsonString(menus));
		this.loginService.initManagerUserPrivileges(req);
		return "index";
	}

	@RequestMapping("top.shtml")
	public String top(HttpServletResponse response) throws IOException {
		return "menu/top";
	}

	@RequestMapping("left.shtml")
	public String left(HttpServletResponse response) throws IOException {
		return "menu/left";
	}

	@RequestMapping("right.shtml")
	public String right(HttpServletRequest request, HttpServletResponse response, String res) throws IOException {
		request.setAttribute("res", res);
		return "right";
	}
	/**
	 * 
	 * @相关说明：从cookie中获取mid
	 * @开发者：zhangqingbin
	 * @时间：2016年3月21日 下午3:54:25
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
	/**
	 * 相关说明：退出系统
	 * 开发者：tangchang
	 * 时间：2015-2-26 下午6:10:06
	 */
	@RequestMapping("systemOut.shtml")
	public void systemOut(HttpServletRequest request ,HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute(Constants.SessionKey.LOGIN_USER);
		request.getSession().invalidate();
		response.sendRedirect("../login/toLoginPage.shtml");

	}

	/**
	 * 相关说明：去密码设置画面 开发者：汤云涛 时间：2015年6月4日 下午4:45:35
	 */
	@RequestMapping("toSetPwd.shtml")
	public String toSetPwd(HttpServletRequest request, Model model) {

		// 获取用户信息；
		ManagerUser user = (ManagerUser) request.getSession().getAttribute("ConsoleUser");
		model.addAttribute("user", user);

		return "member/setpwd";
	}

	/**
	 * 相关说明：设置密码 开发者：汤云涛 时间：2015年6月4日 下午4:46:44
	 */
	@RequestMapping("setPwd.shtml")
	public String setPwd(HttpServletRequest request, String userId, String oldPwd, String newPwd, Model model) {
		try {
			ManagerUser user = (ManagerUser) request.getSession().getAttribute("ConsoleUser");
			String msg = loginService.setPwd(userId, oldPwd, newPwd, user.getMid());
			if (StringUtils.isEmpty(msg)) {
				super.success(request, "密码设置成功！", "../login/toSetPwd.shtml");
				return "common/success";
			} else {
				model.addAttribute("msg", msg);
				return toSetPwd(request, model);
			}
		} catch (Exception e) {
			super.error(request, e);
			return "common/error";
		}

	}
	
	/**
	 * 
	 * @相关说明：将mid放入cookie
	 * @开发者：汤云涛
	 * @时间：2015年8月20日 下午3:50:48
	 */
	private void addMid2Cookie(HttpServletResponse response,String mid){
		Cookie cookie = new Cookie(Constants.CookieCode.PC_MID,mid);
	   // cookie的有效期为15天
	   cookie.setMaxAge(24 * 60 * 60 * 15);
	   cookie.setPath("/");
	   response.addCookie(cookie);
	}
	
	private void addUserIdCookie(HttpServletResponse response,String userId){
		Cookie cookie = new Cookie("userId",userId);
	   // cookie的有效期为15天
	   cookie.setMaxAge(24 * 60 * 60 * 15);
	   cookie.setPath("/");
	   response.addCookie(cookie);
	}
}
