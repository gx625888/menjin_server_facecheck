package com.threey.guard.base.controller;

import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.domain.Menu;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.domain.SelectMenu;
import com.threey.guard.base.service.MenuService;
import com.threey.guard.base.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu/*")
public class MenuController extends BaseController{	
	@Autowired
	private MenuService menuService;
	/**
	 * 相关说明：菜单树列表
	 * 开发者：贺白云
	 * 时间：2016-8-04 下午5:39:04
	 * @throws IOException
	 */
	@RequestMapping("menu.shtml")
	public void menuList(HttpServletRequest request,HttpServletResponse response,Menu menu) throws IOException {
		ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
		String modularId = (String)request.getParameter("modularId");
		String mid = loginUser.getMid();
		String userId = loginUser.getUserId();
		//通过一级菜单的id 放入查出下级菜单
		try {
		menu.setModularId(modularId);
		 JSONObject obj=new JSONObject();
		 
		List<Menu> menuList =null;
		if(loginUser.getIsRootUser().equals(Constants.ManagerUser.ROOT_USER_1)){ // 根管理员
		 menuList = menuService.findMenuByParentId(menu);
		 
		}else{ //普通商家
			menu.setMid(mid);
			menu.setUserId(userId);
		    menuList = menuService.findMenu(menu);
		   }
		 JSONArray jsonArray = JSONArray.fromObject(menuList); 
		 obj.element("jsonArray",jsonArray);
		 response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("queryMenu.shtml")
	public void queryMenu(HttpServletResponse response,HttpServletRequest request) throws IOException {
		String modularId = (String)request.getParameter("modularId");
		Menu menu=new Menu();
		menu.setModularId(modularId);
		List<Menu> menuList = menuService.findMenuByParentIdOrModularId(menu);
		 JSONArray menuArray = JSONArray.fromObject( menuList ); 
		 JSONObject obj=new JSONObject();
		 obj.element("menuArray",menuArray);
		 response.getWriter().print(obj.toString());
	}
	/*
	 * 
	 * add by shenrui
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("treeMenu.shtml")
	public void treeMenu(HttpServletRequest request,HttpServletResponse response,Menu menu) throws IOException {
		List<SelectMenu> list = new ArrayList<SelectMenu>();
		try {		
		    JSONObject obj=new JSONObject();		 	
			Map<String,List<Privilege>> privilegeMap = (Map<String, List<Privilege>>) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER_PRIVILEGES);
			  if(privilegeMap!=null){
				  for (Map.Entry<String, List<Privilege>> entry : privilegeMap.entrySet()) {
					    List<Privilege> rivilegeList = entry.getValue();
					    for(Privilege privilege :rivilegeList){
					    	Menu men = new Menu();
							men.setModularId(privilege.getModularId());
							List<Menu> fList = menuService.findMenuByModularId(men);
							String parentId = fList.get(0).getParentId();
							if(parentId==null ||"".equals(parentId)){
								parentId="0";
							}
					    	SelectMenu z = new SelectMenu();
				        	z.setId(privilege.getModularId());
				        	z.setpId(parentId);
				        	z.setName(privilege.getPrivilegeName());
				        	z.setOpen("true");
				        	z.setNocheck("false");
				        	z.setCode(privilege.getPrivilegeCode());
				        	list.add(z);		    	 
					    }
				   }		  
			  }
			 JSONArray jsonArray = JSONArray.fromObject(list); 
			 obj.element("jsonArray",jsonArray);
			 response.getWriter().print(obj.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
