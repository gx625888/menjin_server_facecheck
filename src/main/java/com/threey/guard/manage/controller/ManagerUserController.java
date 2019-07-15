package com.threey.guard.manage.controller;

import com.threey.guard.base.controller.BaseController;
import com.threey.guard.base.dao.PrivilegeDao;
import com.threey.guard.base.domain.*;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.service.LoginService;
import com.threey.guard.base.service.RedisService;
import com.threey.guard.base.util.*;
import com.threey.guard.base.util.Constants.UserType;
import com.threey.guard.manage.dao.ManagerUserDAO;
import com.threey.guard.manage.domain.CompanyInfo;
import com.threey.guard.manage.domain.ManagementMode;
import com.threey.guard.manage.domain.ManagerRecord;
import com.threey.guard.manage.service.ManagerCompanyInfoService;
import com.threey.guard.manage.service.ManagerUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户管理请求处理
 * @author ZL
 *
 */
@Controller
@RequestMapping("/manageUser/*")
public class ManagerUserController extends BaseController {
	private Logger logger = Logger.getLogger(ManagerUserController.class);
	
	@Autowired
	private ManagerUserService managerUserService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private LoginService loginService;

	@Autowired
	private PrivilegeDao privilegeDao;

	@Autowired
	private ManagerUserDAO managerUserDAO;

	@Autowired
	private RedisService redisService;

	@Autowired
	private ManagerCompanyInfoService managerCompanyService;
	/**相关说明：商户分页查询
	 * 开发者：zhaoliang
	 * 时间：2015年7月5日 上午2:20:24
	 */
	@RequestMapping("toManagerUserList.shtml")
	public ModelAndView toManagerUserList(ManagerUser mu, Pages<ManagerUser> pages){
		//pages = managerUserService.getManagerUserByPage(mu, pages);
		ModelAndView mv = new ModelAndView("base/user/manageUserList");
//		mv.addObject("pages", pages);
//		mv.addObject("mu",mu);
		return mv;
	}
	@RequestMapping("/page.shtml")
	@ResponseBody
	public DataTable.Resp<ManagerUser> page(DataTable.Req p , HttpServletRequest request){
		ManagerUser u = new ManagerUser();
		ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
		if(loginUser.getManagerType() != 0){
			if(loginUser.getManagerType()==UserType.COM_MASTER){//只有公司管理员和系统管理员返回数据
				u.setManagerCompany(loginUser.getManagerCompany());
			}else {
				return null;
			}
		}
		u.setName(request.getParameter("name"));
		List<ManagerUser>  users = this.managerUserService.list(u,p.getPage()-1,p.getLimit());
		int count = this.managerUserService.count(u);
		return new DataTable.Resp<ManagerUser>(users,count,0);
	}

	@RequestMapping("/save.shtml")
	@ResponseBody
	public Map<String,Object> saveOrUpdate(ManagerUser mu){
		Map<String,Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(mu.getPassword())){
			mu.setPassword(MD5Util.MD5(mu.getPassword()));
		}
		if (StringUtils.isNotEmpty(mu.getMid())){

			this.managerUserService.update(mu);
		}else{
			ManagerUser temp = new ManagerUser();
			temp.setUserId(mu.getUserId());
			temp.setIsDelete(Constants.ManagerUser.DELETE_0);
			List<ManagerUser> list = this.managerUserService.list(temp,0,Integer.MAX_VALUE);
			if (CollectionUtils.isNotEmpty(list)){
				map.put("ret",-1);
				map.put("msg","账号已存在");
				return map;
			}

			mu.setMid(String.valueOf(this.commonService.getNextVal("m_user")));

			this.managerUserService.add(mu);
		}

		map.put("ret",0);
		map.put("msg","成功");
		return map;
	}

    @RequestMapping("/del.shtml")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        this.managerUserService.fackDel(request.getParameter("id"));
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }
	
	@RequestMapping("toAddOrEditManagerUser.shtml")
	public ModelAndView toAddOrEditManagerUser(ManagerUser mu,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("base/user/addOrEditManagerUser");
		ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
		if(StringUtils.isNotEmpty(mu.getMid())){
			ManagerUser managerUser = managerUserService.getManagerUserByMidAndUserId(mu.getMid(), mu.getUserId());
			mv = new ModelAndView("base/user/editManagerUser");
			mv.addObject("mu", managerUser);

		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			if (loginUser.getManagerType()==UserType.COM_MASTER){
				CompanyInfo companyInfo = this.managerCompanyService.getOne(loginUser.getManagerCompany());
				List<CompanyInfo> list = new ArrayList<>();
				list.add(companyInfo);
				mv.addObject("companies",list);

			}else{
				mv.addObject("companies",this.managerCompanyService.list(map,0,Integer.MAX_VALUE));
			}
		}
		mv.addObject("loginUser",loginUser);
		return mv;
	}
	
	@RequestMapping("changeStatus.shtml")
	public ModelAndView changeStatus(HttpServletRequest req, String mid, String userId, String status){
		ModelAndView mv = new ModelAndView("common/success");
		ManagerUser managerUser = new ManagerUser();
		managerUser.setMid(mid);
		managerUser.setUserId(userId);
		managerUser.setStatus(status);
		managerUserDAO.updateManagerUser(managerUser);
		managerUserService.doIsSingelValid(mid);
		super.success(req, "操作成功", "../manageUser/toManagerUserList.shtml");
		return mv;
	}
	
	@RequestMapping("queryTime.shtml")
	public void  queryTime(HttpServletResponse response,String mid) throws IOException {
		ManagerRecord managerRecord =new ManagerRecord();
		managerRecord.setMid(mid);
		List<ManagerRecord> list = managerUserDAO.getManagerRecordList(managerRecord);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("list",JSONArray.fromObject(list).toString());
	    JSONObject json = JSONObject.fromObject(map);
	    response.getWriter().write(json.toString());
	}
	
	@RequestMapping("doAddManagerUser.shtml")
	public void doAddManagerUser(HttpServletResponse response, HttpServletRequest req, ManagerUser mu,
                                 ManagementMode managementMode, String finalFactory, String userId, String name, String phone, String address,
                                 String spvalue, String catalog, String startTime, String endTime
			) throws IOException {
		ManagerUser user = (ManagerUser) req.getSession().getAttribute("ConsoleUser");
		if(mu.getAreaId()!= null && !"".equals(mu.getAreaId()) && mu.getAreaId().equals("all")){
			mu.setStoreNumber("all");
			mu.setAreaId("");
		}
		mu.setUserId(userId);
		mu.setName(name);
		mu.setPhone(phone);
		mu.setAddress(address);
		
		if(finalFactory!=null && finalFactory!=""){
			managementMode.setFinalFactory(finalFactory);//商户选择的餐饮运行模式
		}	
		
		String mid = managerUserService.addManagerUser(mu,user.getUserId(),managementMode,finalFactory);
		Catalog catalogs = new Catalog();	
		doRun(catalogs,mid,userId,spvalue,catalog,startTime,endTime,"");
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("result", "ok");
		map.put("mid", mid);
	    JSONObject json = JSONObject. fromObject(map);
	    response.getWriter().write(json.toString());
	}
	
	@RequestMapping("doEditManagerUser.shtml")
	public ModelAndView doEditManagerUser(HttpServletRequest req, ManagerUser mu, ManagementMode managementMode,
                                          String finalFactory, String catalog, String spvalue, String startTime, String endTime, String isNoQad){
		ModelAndView mv = new ModelAndView("common/success");
		if(mu.getAreaId()!= null && !"".equals(mu.getAreaId()) && mu.getAreaId().equals("all")){
			mu.setStoreNumber("all");
			mu.setAreaId("");
		}
		if(finalFactory!=null && finalFactory!=""){
			managementMode.setFinalFactory(finalFactory);//商户选择的餐饮运行模式
		}			
		managerUserService.modifyManagerUser(mu, managementMode,finalFactory);
		
		Catalog catalogs = new Catalog();	
		doRun(catalogs,mu.getMid(),mu.getUserId(),spvalue,catalog,startTime,endTime,isNoQad);
		super.success(req, "修改成功", "../manageUser/toManagerUserList.shtml");
		return mv;
	}
	
	public void doRun(Catalog catalogs, String mid, String userId, String spvalue, String catalog, String startTime, String endTime, String isNoQad){
		SystemProperties sys = new SystemProperties();
		sys.setCode("PhoneConfig");
		sys.setValue(spvalue);
		sys.setName("是否支持关注即配置");
		sys.setMid(mid);
		if ("0".equals(spvalue)){
			redisService.put("PhoneConfig"+mid, spvalue, null);
		}else{
			redisService.clear("PhoneConfig"+mid);
		}	
	

		if (StringUtils.isBlank(isNoQad)){
			ManagerRecord managerRecord = new ManagerRecord();
			managerRecord.setMid(mid);
			managerRecord.setStartTime(startTime);
			managerRecord.setEndTime(endTime);
			managerUserDAO.addManagerRecord(managerRecord);
		}	
		
		ManagerRecord managerRecord = managerUserDAO.findManRecord(mid);
		if (null != managerRecord){
			ManagerUser managerUser = new ManagerUser();
			managerUser.setMid(mid);
			managerUser.setUserId(userId);		
			managerUser.setEndTime(managerRecord.getEndTime());
			managerUserDAO.updateManagerUser(managerUser);
		}	
		managerUserService.doIsSingelValid(mid);
	}
	
	@RequestMapping("toAddOrEditManagerAuth.shtml")
	public ModelAndView toAddManagerAuth(String mid, String userId, String isEdit){
		ModelAndView mv = new ModelAndView("manageUser/addOrEditManagerAuth");
		if(StringUtils.isNotEmpty(isEdit)){
			List<String> PrivilegeCodeList = managerUserService.getManagerUserPrivilegeCodes(mid, userId);
			mv.addObject("muPrivilegeCodeList",JSONArray.fromObject(PrivilegeCodeList)); 
		}else{
			mv.addObject("muPrivilegeCodeList",JSONArray.fromObject(new String[0]));
		}	
		mv.addObject("mid",mid);
		mv.addObject("userId",userId);
		mv.addObject("isEdit",isEdit);
		return mv;
	}
	
	@RequestMapping("doAddOrEditManagerAuth.shtml")
	public ModelAndView doAddOrEditManagerAuth(HttpServletRequest req, String privilegeArrStr, String mid, String userId, String isEdit){
		ModelAndView mv = new ModelAndView("common/success");
		if(StringUtils.isNotEmpty(isEdit)){
			managerUserService.addOrModifyPrivilege(privilegeArrStr,mid,userId,false);
		}else{
			managerUserService.addOrModifyPrivilege(privilegeArrStr,mid,userId,true);
		}	
		super.success(req, "修改成功", "../manageUser/toManagerUserList.shtml");
		return mv;
	}
	
	/**相关说明：删除主账户及其下面的所有子账户
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午4:49:09
	 */
	@RequestMapping("delManagerUser.shtml")
	public ModelAndView delManagerUser(HttpServletRequest req,@RequestParam(value="mid",required=true) String mid){
		ModelAndView mv = new ModelAndView("common/success");
		managerUserService.delManagerUser(mid);
		redisService.put("doIsValid_"+mid, "false", null);
		super.success(req, "删除成功", "../manageUser/toManagerUserList.shtml");
		return mv;
	}
	
	
	/**相关说明：删除子账户
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午6:16:36
	 */
	@RequestMapping("delChildManagerUser.shtml")
	public ModelAndView delChildManagerUser(HttpServletRequest req,@RequestParam(value="mid",required=true) String mid
			,@RequestParam(value="userId",required=true) String userId){
		ModelAndView mv = new ModelAndView("common/success");
		privilegeDao.delUserPrivilege(mid,userId);
		managerUserService.delChildManagerUser(mid, userId);				
		super.success(req, "删除成功", "../manageUser/toMyChildManagerUserList.shtml");
		return mv;
	}
	
	
//	/**相关说明：跳转到我的账户页面
//	 * 开发者：zhaoliang
//	 * 时间：2015年7月7日 上午10:49:46
//	 */
//	@RequestMapping("toMyManagerUser.shtml")
//	public String toMyManagerUser(HttpServletRequest req){
//		ManagerUser user=(ManagerUser) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
//		req.setAttribute("mu", user);
//
//		String userType = user.getUserType();
//		if(UserType.COM_MASTER.equals(userType) || UserType.ROOT_MANAGER.equals(userType)){
//			List<ManagerUser> userList = managerUserDAO.findManagerUser(user);
//			ManagerUser u = userList.get(0);
//			u.setUserType(userType);
//			req.setAttribute("mu", u);
//			return "managerUser/myManagerUser";
//		}else{
//			return "redirect:toMyChildManagerUserList.shtml";
//		}
//
//	}
	
	private void deleteFile(File file){
		   if(file.exists()){                    //判断文件是否存在
		    if(file.isFile()){                    //判断是否是文件
		     file.delete();                       //delete()方法 你应该知道 是删除的意思;
		    }else if(file.isDirectory()){              //否则如果它是一个目录
		     File files[] = file.listFiles();               //声明目录下所有的文件 files[];
		     for(int i=0;i<files.length;i++){            //遍历目录下所有的文件
		      this.deleteFile(files[i]);             //把每个文件 用这个方法进行迭代
		     } 
		    } 
		    file.delete(); 
		   }else{ 
		    System.out.println("所删除的文件不存在！"+'\n');
		   } 
		} 
	/**相关说明：修改我的账户信息
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 上午11:22:55
	 */
	@RequestMapping("modifyMyManagerUser.shtml")
	public ModelAndView modifyMyManagerUser(HttpServletRequest req,HttpServletResponse res, 
			@RequestParam(value = "imgFiles", required = false)MultipartFile imgFiles,
			@RequestParam(value = "imgBannerFiles", required = false)MultipartFile imgBannerFiles,
			@RequestParam(value = "qrCodeImgFiles", required = false)MultipartFile qrCodeImgFiles,
			ManagerUser mu) throws IOException {
		ModelAndView mv = new ModelAndView("common/success");
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		String oldPath = req.getParameter("oldPath");
		String imgFileName ="";
		if(!StringUtil.isEmpty(oldPath)){
			logger.info("XXXXXXXXXXXXXXXX商户LOGO原路径="+oldPath);
			mu.setEnterpriseLogo(oldPath);
			imgFileName = oldPath.substring(oldPath.lastIndexOf("/")+1).toLowerCase();
			logger.info("XXXXXXXXXXXXXXXX商户LOGO原图片名称="+imgFileName);
		}
		
		String path = req.getSession().getServletContext().getRealPath("/upload/logo/");
		String fileName = "";
		if(imgFiles != null){
			fileName=imgFiles.getOriginalFilename();
		}
		if (null != fileName && !"".equals(fileName))
		{	
			String deletePath = path+File.separator+imgFileName;
			File file = new File(deletePath);
			logger.info("XXXXXXXXXXXXXXX商户LOGO删除的图片路径="+deletePath);
			deleteFile(file);
			String imgN=ImageUtils.savePicture(fileName, path, imgFiles);
			String serverPath =  GETProperties.readValue("SERVER_LOGO_PATH")+imgN;
			mu.setEnterpriseLogo(serverPath);
		}
		logger.info("XXXXXXXXXXXXXXXX商户Banner原路径=update");
		String oldBannerPath = req.getParameter("oldBannerPath");
		String imgBannerFileName ="";
		if(!StringUtil.isEmpty(oldBannerPath)){
			logger.info("XXXXXXXXXXXXXXXX商户Banner原路径="+oldBannerPath);
			mu.setEnterpriseBannerLogo(oldBannerPath);
			imgBannerFileName = oldBannerPath.substring(oldBannerPath.lastIndexOf("/")+1).toLowerCase();
			logger.info("XXXXXXXXXXXXXXXX商户Banner原图片名称= "+imgBannerFileName);
		}
		
		String fileBannerName = "";
		if(imgBannerFiles != null){
			fileBannerName=imgBannerFiles.getOriginalFilename();
			logger.info("XXXXXXXXXXXXXXXX商户Banner"+fileBannerName);
		}else {
			logger.info("XXXXXXXXXXXXXXXX商户Banner NULL");
		}
		if (null != fileBannerName && !"".equals(fileBannerName))
		{
			if(imgBannerFileName!=null && !"".equals(imgBannerFileName)) {
				String deletePath = path+File.separator+imgBannerFileName;
				File file = new File(deletePath);
				logger.info("XXXXXXXXXXXXXXX商户Banner删除的图片路径="+imgBannerFileName+"    "+deletePath);
				deleteFile(file);
			}
			
			String imgN=ImageUtils.savePicture(fileBannerName, path, imgBannerFiles);
			logger.info("XXXXXXXXXXXXXXXX商户savePicture Banner "+imgN);
			String serverPath =  GETProperties.readValue("SERVER_LOGO_PATH")+imgN;
			logger.info("XXXXXXXXXXXXXXXX商户serverPath Banner "+serverPath);
			mu.setEnterpriseBannerLogo(serverPath);
			logger.info("XXXXXXXXXXXXXXXX商户serverPath- Banner "+mu.getEnterpriseBannerLogo());
		}
		
		
		
		if (null != qrCodeImgFiles && !qrCodeImgFiles.isEmpty())
		{	
			ManagerUser loginUser = (ManagerUser) req.getSession().getAttribute("ConsoleUser");
			if(StringUtils.isNotBlank(loginUser.getWxQrCode())){
				String deletePath = path+File.separator+FilenameUtils.getExtension(loginUser.getWxQrCode());
				File file = new File(deletePath);
				deleteFile(file);
			}
			
			String imgN=ImageUtils.savePicture(qrCodeImgFiles.getOriginalFilename(), path, qrCodeImgFiles);
			String serverPath =  GETProperties.readValue("SERVER_LOGO_PATH")+imgN;
			mu.setWxQrCode(serverPath);
		}
		
		try {
			managerUserService.modifyMyManagerUser(mu);
			
			super.success(req, "新增、修改成功", "../manageUser/toMyManagerUser.shtml");
			
		} catch (Exception e) {
			logger.error(e);
			super.error(req, "网络异常！");
		}
		return mv;
	}
	
	
//	/**相关说明：我的子账户列表页面
//	 * 开发者：zhaoliang
//	 * 时间：2015年7月7日 下午12:39:08
//	 */
//	@RequestMapping("toMyChildManagerUserList.shtml")
//	public ModelAndView toMyChildManagerUserList(HttpServletRequest req,ManagerUser mu ,Pages<ManagerUser> page){
//		ManagerUser user=(ManagerUser) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
//		String mid = user.getMid();
//		String userType = user.getUserType();
//		mu.setMid(mid);
//		mu.setParentUserId(user.getUserId());
//		if(UserType.PRO_MANAGER.equals(userType)){
//			mu.setAreaId(user.getAreaId());
//		}
//		if(UserType.CITY_MANAGER.equals(userType)){
//			mu.setCityId(user.getCityId());
//		}
//		if(UserType.RES_MANAGER.equals(userType)){
//			mu.setStoreNumber(user.getStoreNumber());
//		}
//		Pages<ManagerUser> pages = managerUserService.getMyChildManagerUserByPage(req,mu, page);
//		ModelAndView mv = new ModelAndView("managerUser/myChildManagerUserList");
//		mv.addObject("pages", pages);
//		mv.addObject("mu",mu);
//		return mv;
//	}
	
	
	/**相关说明：跳转到子账户添加、修信息页面
	 * 开发者：zhaoliang
	 * 时间：2015年7月5日 上午10:40:35
	 */
	@RequestMapping("toAddOrEditChildManagerUser.shtml")
	public ModelAndView toAddOrEditChildManagerUser(HttpServletRequest req,ManagerUser mu){
		ModelAndView mv = new ModelAndView("managerUser/addOrEditChildManagerUser");
		ManagerUser user=(ManagerUser) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
		String mid = user.getMid();
		String userType = user.getUserType();
		req.setAttribute("userType", userType);

		if(StringUtils.isNotEmpty(mu.getMid()) && StringUtils.isNotEmpty(mu.getUserId())){ //修改
			ManagerUser managerUser = managerUserService.getManagerUserByMidAndUserId(mu.getMid(), mu.getUserId());
			String storeNum = managerUser.getStoreNumber();
			if(!StringUtil.isEmpty(storeNum)&&storeNum.equals("all")){
				managerUser.setStoreNumber("");
				managerUser.setAreaId("all");
			}
			mv.addObject("mu", managerUser);
			List<String> PrivilegeCodeList = managerUserService.getManagerUserPrivilegeCodes(mu.getMid(), mu.getUserId());
			mv.addObject("muPrivilegeCodeList",JSONArray.fromObject(PrivilegeCodeList)); // 商户拥有的权限
		}else{ //新增
			mv.addObject("muPrivilegeCodeList",JSONArray.fromObject(new String[0])); // 商户拥有的权限
		}
		
		return mv;
	}
	
	
//	/**相关说明：处理子账户添加、修改
//	 * 开发者：zhaoliang
//	 * 时间：2015年7月7日 下午3:18:54
//	 */
//	@RequestMapping("doAddOrEditChildManagerUser.shtml")
//	public ModelAndView doAddOrEditChildManagerUser(HttpServletRequest req,ManagerUser mu,String privilegeArrStr){
//		ManagerUser user=(ManagerUser) req.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
//		ModelAndView mv = new ModelAndView("common/success");
//
//		String myareaId = mu.getMyareaId();
//		String mycityId = mu.getMycityId();
//		String mystoreNumber = mu.getMystoreNumber();
//
//		if(mu.getAreaId()==null || mu.getAreaId()==""){
//			if(!StringUtil.isEmpty(myareaId)){
//				mu.setAreaId(myareaId);
//			}
//		}
//		if(mu.getCityId()==null || mu.getCityId()==""){
//			if(!StringUtil.isEmpty(mycityId)){
//				mu.setCityId(mycityId);
//			}
//		}
//		if(mu.getStoreNumber()==null || mu.getStoreNumber()==""){
//			if(!StringUtil.isEmpty(mystoreNumber)){
//				mu.setStoreNumber(mystoreNumber);
//			}
//		}
//
//		String areaId=mu.getAreaId();
//		String userType = user.getUserType();
//		mu.setParentUserId(user.getUserId());
//		if(UserType.PRO_MANAGER.equals(userType)){
//			mu.setAreaId(user.getAreaId());
//		}
//		if(UserType.CITY_MANAGER.equals(userType)){
//			mu.setAreaId(user.getAreaId());
//			mu.setCityId(user.getCityId());
//		}
//		if(UserType.RES_MANAGER.equals(userType)){
//			mu.setAreaId(user.getAreaId());
//			mu.setCityId(user.getCityId());
//			mu.setStoreNumber(user.getStoreNumber());
//		}
//
//		if("all".equals(areaId)){
//			mu.setAreaId("");
//			mu.setStoreNumber("all");
//		}
//		managerUserService.addOrEditChildManagerUserAndPrivilege(req,mu,privilegeArrStr);
//		super.success(req, "新增、修改成功", "../manageUser/toMyChildManagerUserList.shtml");
//		return mv;
//	}
//
	@RequestMapping("checkManagerUser.shtml")
	public void checkManagerUser(HttpServletRequest req,HttpServletResponse response,String userId) throws IOException {
		ManagerUser loginManagerUser = loginService.getLoginManagerUser(req);
		String result="0";
		ManagerUser managerUser = managerUserService.getManagerUserByMidAndUserId(loginManagerUser.getMid(), userId);
		if (null !=managerUser)
		{
			result ="1";
		}
		HashMap<String,String> map = new HashMap<String,String>();
	    map.put("result", result);
	    JSONObject json = JSONObject. fromObject(map);
	    response.getWriter().write(json.toString());
	}
}
