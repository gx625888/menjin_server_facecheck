package com.threey.guard.base.service;


import com.threey.guard.base.dao.PrivilegeDao;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.domain.Menu;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.MD5Util;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.dao.ManagerUserDAO;
import com.threey.guard.manage.service.ManagerUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author ZL
 *
 */
@Service
public class LoginService {
	private static final Logger logger = Logger.getLogger(LoginService.class);
	@Autowired
	private ManagerUserDAO managerUserDAO;
	@Autowired
	private PrivilegeDao privilegeDao;
	@Autowired
	private ManagerUserService managerUserService;
//	@Autowired
//	private StoreInfoService storeInfoService;
//	@Autowired
//	private CityService cityService;
//
	@Autowired
	private LogAuditService logAuditService;
	/**相关说明：用户登录
	 * 开发者：zhaoliang
	 * 时间：2015年7月3日 下午5:20:54
	 */
	public int systemLogin(HttpServletRequest request, ManagerUser mu) throws Exception{
		try {
			if(StringUtils.isEmpty(mu.getPassword()) || StringUtils.isEmpty(mu.getUserId())){
				return 2;//账号、密码可能为空
			}
			
			mu.setPassword(MD5Util.MD5(mu.getPassword()));
			mu.setIsDelete(Constants.ManagerUser.DELETE_0);

			
			List<ManagerUser> user = managerUserDAO.findManagerUser(mu);
			if(CollectionUtils.isEmpty(user)){
				logAuditService.loginLog(mu,null,request);//记录登录日志信息 edit by threey 20180812
				return 1;//账号、密码不正确
			}else{				
				logger.info("账号密码正确，商户登录成功！");
				ManagerUser u = user.get(0);
				logAuditService.loginLog(mu,u,request);//记录登录日志信息 edit by threey 20180812
				String userType = "";
				u.setUserType(userType);
				request.getSession().setAttribute(Constants.SessionKey.LOGIN_USER, u);
				return 0;//账号密码正确
			}
		} catch (Exception e) {
			//throw new SAException("系统出错了", e);
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**相关说明：获取后台登录管理员用户信息
	 * 开发者：zhaoliang
	 * 时间：2015年7月4日 上午12:25:45
	 */
	public ManagerUser getLoginManagerUser(HttpServletRequest req){
		return (ManagerUser) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
	}
	
	
	/**相关说明：获取登录用户MId
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午3:57:05
	 */
	public String getLoginManagerUserMid(HttpServletRequest req){
		ManagerUser loginManagerUser = (ManagerUser) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
		return loginManagerUser.getMid();
	}
	/**
	 * 相关说明：返回当前登录用户下所有的门店编号格式为：('1111','2222','3333')
	 * 开发者：zhangqingbin
	 * 时间：2016年4月21日 上午9:45:25
	 */
//	public String getLoginManagerStoreNums(HttpServletRequest request){
//		ManagerUser user = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
//		String userType = user.getUserType();
//		String mid = user.getMid();
//		String storeNums = "";
//		StoreInfo store = new StoreInfo();
//		store.setMid(mid);
//		if(UserType.ROOT_MANAGER.equals(userType) || UserType.COM_MASTER.equals(userType)){
//			List<StoreInfo> storeList = storeInfoService.findAllByMid(mid);
//			if(storeList != null && storeList.size() > 0){
//				storeNums ="(";
//				for(StoreInfo tmp:storeList){
//					storeNums+="'"+ tmp.getNumber()+"',";
//				}
//				storeNums = storeNums.substring(0, storeNums.length()-1)+")";
//			}
//		}
//		if(UserType.PRO_MANAGER.equals(userType)){
//			City c = new City();
//			c.setMid(mid);
//			c.setAreaId(user.getAreaId());
//			List<City> cityList = cityService.findCitieList(c);
//			if(cityList != null && cityList.size() > 0){
//				storeNums ="(";
//				for(City tmp:cityList){
//					StoreInfo s = new StoreInfo();
//					s.setMid(mid);
//					s.setLocation(tmp.getId());
//					List<StoreInfo> storeList = storeInfoService.findAllT(s);
//					if(storeList != null && storeList.size() > 0){
//						for(StoreInfo stmp:storeList){
//							storeNums +="'"+ stmp.getNumber()+"',";
//						}
//					}
//				}
//				storeNums = storeNums.substring(0, storeNums.length()-1)+")";
//			}
//		}
//		if(UserType.CITY_MANAGER.equals(userType)){
//			StoreInfo sInfo = new StoreInfo();
//			sInfo.setMid(mid);
//			sInfo.setLocation(user.getCityId());
//			List<StoreInfo> storeList = storeInfoService.findAllT(sInfo);
//			if(storeList != null && storeList.size() > 0){
//				storeNums ="(";
//				for(StoreInfo tmp:storeList){
//					storeNums += "'"+tmp.getNumber()+"',";
//				}
//				storeNums = storeNums.substring(0, storeNums.length()-1)+")";
//			}
//		}
//		if(UserType.RES_MANAGER.equals(userType)){
//			storeNums="('"+user.getStoreNumber()+"')";
//		}
//		if(storeNums.length() <= 0){
//			storeNums="()";
//		}
//		return storeNums;
//	}

	/**
	 * 初始化菜单
	 * @param req
	 */
	public List<Menu> initMenus(HttpServletRequest req){
		ManagerUser loginUser = getLoginManagerUser(req);
		if (null==loginUser){
			return null;
		}
		return this.privilegeDao.getMenuByUserId(Constants.ManagerUser.ROOT_USER_1.equals(loginUser.getIsRootUser())?null:loginUser.getMid());
	}
	/**相关说明：初始化后台管理员权限信息
	 * 开发者：zhaoliang
	 * 时间：2015年7月4日 上午12:23:04
	 */

	public void initManagerUserPrivileges(HttpServletRequest req){
		ManagerUser loginUser = getLoginManagerUser(req);
		String mid =loginUser.getMid();
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("USERID", mid);
		if(Constants.ManagerUser.ROOT_USER_1.equals(loginUser.getIsRootUser())){ // 根管理员
			pMap.put("MID", mid);
		}

		List<Privilege> privileges = privilegeDao.getPrivileges(pMap);
		List<String> privilegeCods = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(privileges)){
			for (Privilege p:privileges) {
				privilegeCods.add(p.getPrivilegeCode());
			}
		}
		
		/*Map<String,List<Privilege>> privilegeMap = new HashMap<String,List<Privilege>>();
		if(CollectionUtils.isNotEmpty(privileges)){
			for(Privilege p : privileges){
				String modularId = p.getModularId();
				if(privilegeMap.containsKey(modularId)){
					privilegeMap.get(modularId).add(p);
				}else{
					List<Privilege> pTemp = new ArrayList<Privilege>();
					pTemp.add(p);
					privilegeMap.put(modularId, pTemp);
				}
			}
		}*/
		req.getSession().setAttribute(Constants.SessionKey.LOGIN_USER_PRIVILEGES, privilegeCods);
	}
	
	
	/**相关说明：获取登录用户权限，返回权限code集合
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午7:34:48
	 */
	public List<String> getLoginUserPrivilegeCodeList(HttpServletRequest req){
		//List<String> privilegeCodeList = new ArrayList<String>();
		/*Map<String,List<Privilege>> privilegeMap = (Map<String, List<Privilege>>) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER_PRIVILEGES);
		Set<Map.Entry<String,List<Privilege>>> set = privilegeMap.entrySet();
		for (Iterator<Map.Entry<String, List<Privilege>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<Privilege>> entry = (Map.Entry<String, List<Privilege>>) it.next();
          
            List<Privilege> pList = entry.getValue();
            if(CollectionUtils.isNotEmpty(pList)){
            	for(Privilege p : pList){
            		privilegeCodeList.add(p.getPrivilegeCode());
            	}
            }
        }*/
		List<String>  privilegeCodeList = (List<String>) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER_PRIVILEGES);
		return privilegeCodeList;
	}
	
	/**相关说明：根据user_id、mid判断用户是否存在
	 * 商家新增子账户的时候判断
	 * 开发者：zhaoliang
	 * 时间：2015年7月3日 下午7:22:52
	 */
	public boolean validateMidAndUserIdExists(String mid, String userId){
		ManagerUser mu = new ManagerUser();
		mu.setMid(mid);
		mu.setUserId(userId);
		return CollectionUtils.isNotEmpty(managerUserDAO.findManagerUser(mu)) ? true : false;
	}
	
	/**
	 * 相关说明：设置密码
	 * 开发者：汤云涛
	 * 时间：2015年6月4日 下午5:09:27
	 */
	public String setPwd(String userId, String oldPwd, String newPwd, String mid){
		
		String msg = "";
		
		// 验证是否有权设置密麻
		ManagerUser user = managerUserService.getManagerUserByMidAndUserId(mid, userId);
		if(null!=user){
			if(MD5Util.MD5(oldPwd).equals(user.getPassword())){
				// 设置密码
				managerUserDAO.setPwd(mid,userId, MD5Util.MD5(newPwd));
			}else{
				msg = "该用户与密码不匹配";
			}
		}else{
			msg = "该用户不存在";
		}
		
		return msg;
	}
}
