package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CommonDAO;
import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.dao.PrivilegeDao;
import com.threey.guard.base.domain.Catalog;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.domain.PageStyle;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.base.service.RedisService;
import com.threey.guard.base.util.*;
import com.threey.guard.manage.dao.ManagerUserDAO;
import com.threey.guard.manage.domain.ManagementMode;
import com.threey.guard.manage.domain.ManagerRecord;

import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商家模块业务处理
 * @author ZL
 *
 */
@Service
public class ManagerUserService extends CrudService<ManagerUser> {
	private Logger logger = Logger.getLogger(ManagerUserService.class);
	
	@Autowired
	private ManagerUserDAO managerUserDao;
	
	@Autowired
	private PrivilegeDao privilegeDao;
	
//	@Autowired
//	private LoginService loginService;
	
	@Autowired
	private CommonDAO commonDAO;
	
//	@Autowired
//	private WechatPropertiesDAO  wechatPropertiesDAO;
//
//	@Autowired
//	private ShortMessageSerivce shortMessageSerivce;
	@Autowired
	private RedisService redisService;
	/**相关说明：分页查询商家列表
	 * 开发者：zhaoliang
	 * 时间：2015年7月5日 上午2:51:17
	 */
	public Pages<ManagerUser> getManagerUserByPage(ManagerUser mu, Pages<ManagerUser> page){
		mu.setIsMRootUser(Constants.ManagerUser.M_ROOT_USER_1);
		mu.setIsDelete(Constants.ManagerUser.DELETE_0);
		List<ManagerUser> managerUserList = managerUserDao.getManagerUserByPage(mu, page.getCurrentPage());
		int totalCount = managerUserDao.getManagerUserCount(mu);
		page.setCurrList(managerUserList);
		page.setTotalCount(totalCount);
		return page;
	}
	
	
	/**相关说明：根据mid、userid查询商户信息
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午3:26:50
	 */
	public ManagerUser getManagerUserByMidAndUserId(String mid, String userId){
		ManagerUser mu = new ManagerUser();
		mu.setMid(mid);
		mu.setUserId(userId);
		List<ManagerUser> muList = managerUserDao.findManagerUser(mu);
		if(CollectionUtils.isEmpty(muList)){
			return null;
		}
		return muList.get(0);
	}
	
	
	/**相关说明：获取商家权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午6:31:13
	 */
	public List<Privilege> getMangerUserPrivileges(String mid, String userId){
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("MID", mid);
		pMap.put("USERID", userId);
		return privilegeDao.getPrivileges(pMap);
	}
	
	/**相关说明：
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午6:53:52
	 */
	public List<String> getManagerUserPrivilegeCodes(String mid, String userId){
		List<String> retList = new ArrayList<String>();
		List<Privilege> privilegeList = getMangerUserPrivileges(mid,userId);
		if(CollectionUtils.isNotEmpty(privilegeList)){
			for(Privilege p : privilegeList){
				retList.add(p.getPrivilegeCode());
			}
		}
		return retList;
	}
	
	
	/**相关说明：新增商户同时给商户赋予权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午12:52:02
	 */
	public String addManagerUser(ManagerUser mu, String userid, ManagementMode managementMode, String finalFactory){
		//*****************************1.添加商户******************//
		String mid = StringUtil.getUuid();
		String current = DateTimeUtil.getCurrDateTimeStr();
		mu.setMid(mid);
		mu.setIsRootUser(Constants.ManagerUser.ROOT_USER_0);
		mu.setIsMRootUser(Constants.ManagerUser.M_ROOT_USER_1);
		mu.setPassword(MD5Util.MD5(Constants.ManagerUser.ORIGN_M_PASSWORD));
		mu.setCreateTime(current);
		mu.setStoreNumber("all");
		managerUserDao.addManagerUser(mu);
		
		managementMode.setMid(mid);
		managementMode.setUpdate_time(current);
		managementMode = checkFactory(managementMode, finalFactory);//商户运营模式
		addManagementMode(managementMode);
		
		// 新建商户crm初始化
//		RemoteUtil.initMerch(mid, current, userid);
//
//		initPageType(mid);
//		try{
//			MessageRemainingRecord mrr = new MessageRemainingRecord();
//			mrr.setCreateTime(DateTimeUtil.getCurrDateTimeStr());
//			mrr.setMid(mid);
//			mrr.setId(StringUtil.getUuid());
//			mrr.setRemainingMessage(1000);
//			mrr.setUpdateTime(DateTimeUtil.getCurrDateTimeStr());
//			mrr.setSendNumber(mu.getPhone());
//			shortMessageSerivce.insertMessageRemainingRecord(mrr);
//
//		}catch(Exception e){
//			logger.info("添加关联短信条数失败！"+e);
//		}
		return mid;
	}
	
	
	
	public void initPageType(String mid){
		
		PageStyle pageStyle = commonDAO.findPageStyle(mid);
		if (null ==pageStyle)
		{
			commonDAO.addPtype(mid);
		}
		
		Catalog catalog = commonDAO.findCatalog(mid);
		if (null ==catalog)
		{
			commonDAO.addPcatalog(mid);
		}
	
	}
	
	
	/**相关说明：修改商户信息、商户权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午7:21:37
	 */
	public void modifyManagerUser(ManagerUser mu,ManagementMode managementMode,String finalFactory){
		//*****************************1.修改商户信息************************//
		ManagerUser managerUser = getManagerUserByMidAndUserId(mu.getMid(),mu.getUserId());
		managerUser.setName(mu.getName());
		managerUser.setAddress(mu.getAddress());
		managerUser.setPhone(mu.getPhone());
		managerUserDao.updateManagerUser(managerUser);
		
		String current = DateTimeUtil.getCurrDateTimeStr();
		
		ManagementMode mm = new ManagementMode();
		mm.setMid(managerUser.getMid());
		ManagementMode isCanUse = managerUserDao.findManagementMode(mm);
		//不存在	先创建
		if(StringUtil.isNull(isCanUse)){
			mm.setUpdate_time(current);
			mm = checkFactory(mm, finalFactory);
			addManagementMode(mm);
		}
		
		/*录入商户的餐饮运营模式*/
		managementMode.setUpdate_time(current);
		managementMode.setMid(managerUser.getMid());
		managementMode = checkFactory(managementMode, finalFactory);
		editManagementMode(managementMode);
	}
	
	public void addOrModifyPrivilege(String privilegeArrStr, String mid, String userId, Boolean isAdd){
		if (!isAdd){
			privilegeDao.delUserPrivilege(mid,userId);
		}
		setUserPrivileges(mid, userId, privilegeArrStr);
	}
	
	
	/**相关说明：修改我的账户信息，不包含权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 上午11:25:57
	 */
	public void modifyMyManagerUser(ManagerUser mu){
		ManagerUser managerUser = getManagerUserByMidAndUserId(mu.getMid(),mu.getUserId());
		managerUser.setName(mu.getName());
		managerUser.setAddress(mu.getAddress());
		managerUser.setPhone(mu.getPhone());
		managerUser.setEnterpriseLogo(mu.getEnterpriseLogo());
		managerUser.setEnterpriseBannerLogo(mu.getEnterpriseBannerLogo());
		managerUser.setWxQrCode(mu.getWxQrCode());
		managerUserDao.updateManagerUser(managerUser);
		
	}
	
	
	/**相关说明： 分页查询子账户
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午12:47:51
	 */
	public Pages<ManagerUser> getMyChildManagerUserByPage(HttpServletRequest req,ManagerUser mu,Pages<ManagerUser> page){
		mu.setIsMRootUser(Constants.ManagerUser.M_ROOT_USER_0);
		mu.setIsDelete(Constants.ManagerUser.DELETE_0);
//		mu.setMid(loginService.getLoginManagerUserMid(req));
		List<ManagerUser> managerUserList = managerUserDao.getManagerUserByPage(mu, page.getCurrentPage());
		int totalCount = managerUserDao.getManagerUserCount(mu);
		page.setCurrList(managerUserList);
		page.setTotalCount(totalCount);
		return page;
	}
	
	
	/**相关说明： 添加或修改子账户并赋予权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午2:14:57
	 */
	public void addOrEditChildManagerUserAndPrivilege(HttpServletRequest req,ManagerUser mu,String privilegeArrStr){
		if(StringUtils.isEmpty(mu.getMid())){ // 新增
//			mu.setMid(loginService.getLoginManagerUserMid(req));
			mu.setIsRootUser(Constants.ManagerUser.ROOT_USER_0);
			mu.setIsMRootUser(Constants.ManagerUser.M_ROOT_USER_0);
			mu.setPassword(MD5Util.MD5(mu.getPassword()));
			mu.setCreateTime(DateTimeUtil.getFormatDateTime(new Date()));
			managerUserDao.addManagerUser(mu);
		}else{ // 修改
			ManagerUser managerUser = getManagerUserByMidAndUserId(mu.getMid(), mu.getUserId());
			managerUser.setName(mu.getName());
			managerUser.setAddress(mu.getAddress());
			managerUser.setPhone(mu.getPhone());
			managerUser.setType(mu.getType());
			managerUser.setStoreNumber(mu.getStoreNumber());
			managerUser.setAreaId(mu.getAreaId());
			managerUser.setCityId(mu.getCityId());
			managerUser.setStoreNumber(mu.getStoreNumber());
			managerUser.setParentUserId(mu.getParentUserId());
			if(!mu.getPassword().equals(managerUser.getPassword())){
				managerUser.setPassword(MD5Util.MD5(mu.getPassword()));
			}
			managerUserDao.updateManagerUser(managerUser);
		}
		privilegeDao.delUserPrivilege(mu.getMid(),mu.getUserId());
		setUserPrivileges(mu.getMid(),mu.getUserId(),privilegeArrStr);
	}
	
	
	/**相关说明：
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午3:35:32
	 */
	public void setUserPrivileges(String mid, String userId, String privilegeArrStr){
		JSONArray privilegeArr = JSONArray.fromObject(privilegeArrStr);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("MID", mid);
		paramMap.put("USERID", userId);
		if(privilegeArr != null && privilegeArr.size() > 0){
			for(int i = 0 ; i < privilegeArr.size() ; i++){
				String privilegeCode = String.valueOf(privilegeArr.get(i));
				paramMap.put("PRIVILEGECODE", privilegeCode);
				privilegeDao.addUserPrivilege(paramMap);
			}
		}
	}
	
	/**相关说明：删除商户商户帐号和该商户的子帐号
	 * 		     删除该商户的微信配置信息
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午5:11:26
	 */
	public void delManagerUser(String mid){
		ManagerUser mu = new ManagerUser();
		mu.setMid(mid);
		managerUserDao.delManagerUser(mu);
		
		managerUserDao.delManagerRecord(mid);
		
		//根据商户号删除该商户下的所有微信配置信息
		//wechatPropertiesDAO.delWechatPropertiesByMid(mid);
		//add by shenrui 20161208
		try{
			//this.shortMessageSerivce.delMessageRemainingRecord(mid);
		}catch(Exception e){
			logger.info("删除关联短信:"+ e);
		}
	}
	
	
	/**相关说明：
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午5:13:43
	 */
	public void delChildManagerUser(String mid, String userId){
		ManagerUser mu = new ManagerUser();
		mu.setMid(mid);
		mu.setUserId(userId);
		managerUserDao.delManagerUser(mu);
		List<ManagerUser> muList = managerUserDao.selChildManagerUser(mu);
		if(muList != null && muList.size() > 0){
			for(ManagerUser tmp:muList){
				delChildManagerUser(tmp.getMid(),tmp.getUserId());
			}
		}
		managerUserDao.delChildManagerUser(mu);		
	}
	
	/**
	 * 相关说明：配置经营模式
	 * 开发者：wudouzhu
	 * 时间：2016年6月30日 下午2:13:00
	 */
	public ManagementMode checkFactory(ManagementMode managementMode,String finalFactory){
		if(managementMode!=null){
			managementMode.setTakeaway_flag("0");
			managementMode.setDinner_flag("0");
			managementMode.setFast_food_flag("0");
			
			if(finalFactory!="" && finalFactory!=null){
				String[] arr = finalFactory.split(",");
				if(arr!=null && arr.length>0){
					for(int i=0;i<arr.length;i++){
						String factoryType = arr[i];
						if(factoryType.equals("waimai")){
							managementMode.setTakeaway_flag("1");//经营-外卖
						}else if(factoryType.equals("tangshi")){
							managementMode.setDinner_flag("1");//经营-堂食
						}else if(factoryType.equals("kuaican")){
							managementMode.setFast_food_flag("1");//经营-快餐
						}
						
					}
				}
			}
		}
		return managementMode;
		
	}


	/*添加商户的经营模式*/
	public void addManagementMode(ManagementMode managementMode) {
		managerUserDao.addManagementMode(managementMode);
	}
	/*修改商户的经营模式*/
	public void editManagementMode(ManagementMode managementMode) {
		managerUserDao.editManagementMode(managementMode);
	}

	/*查询商户的经营模式*/
	public ManagementMode findManagementMode(String mid) {
		ManagementMode managementMode = new ManagementMode();
		managementMode.setMid(mid);
		return managerUserDao.findManagementMode(managementMode);
	}
	
	public void doIsValid(){			
		List<ManagerUser> list = findManUser();
		if (null!=list && list.size()>0){
			for (int i=0;i<list.size();i++){
				Boolean isValid = true;
				ManagerUser user = list.get(i);
				if (!StringUtils.isEmpty(user.getStatus()) && "1".equals(user.getStatus())){
					isValid = false;
				}else{
					isValid = findManRecord(user.getMid());	
				}
				if (isValid){
					redisService.put("doIsValid_"+user.getMid(), "true", null);
				}else{
					redisService.put("doIsValid_"+user.getMid(), "false", null);
				}
			}	
		}	
	}
	
	public void doIsSingelValid(String mid){
        Boolean isValid = true;
        ManagerUser user = getManUser(mid);	
	    if (null != user){
	    	if (!StringUtils.isEmpty(user.getStatus()) && "1".equals(user.getStatus())){
				isValid = false;
			}else{
				isValid = findManRecord(user.getMid());	
			}
	    }	
		if (isValid){
			redisService.put("doIsValid_"+user.getMid(), "true", null);
		}else{
			redisService.put("doIsValid_"+user.getMid(), "false", null);
		}
		
	}
	
	public List<ManagerUser> findManUser(){
		return managerUserDao.findManUser();
	}
	
	public ManagerUser getManUser(String mid){
		return managerUserDao.getManUser(mid);
	}
	
	
	public Boolean findManRecord(String mid){
		Boolean isValid = true;
		try{
			ManagerRecord record =  managerUserDao.findManRecord(mid);
			if (null != record){	
				String systemDate = DateTimeUtil.getFormatDate(new Date());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date d = df.parse(systemDate);
				Date st = df.parse(record.getStartTime());
				Date et = df.parse(record.getEndTime());
				if (d.getTime()<st.getTime() || d.getTime()>et.getTime()){
					isValid =false;
				}			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isValid;
	}

	public void fackDel(String mid){
		this.managerUserDao.fackDel(mid);
	}

	@Override
	protected CrudDAO getDao() {
		return managerUserDao;
	}
}
