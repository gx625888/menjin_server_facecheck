package com.threey.guard.base.domain;

import java.io.Serializable;

/**
 * @author ZL
 * 
 * 
 */
public class ManagerUser implements Serializable {
	private static final long serialVersionUID = 2699702563871088648L;
	private String mid;
	private String userId;
	private String isRootUser; // 网站根用户标识
	private String isMRootUser;// 商户根用户标识
	private String storeNumber;
	private String name;
	private String password;
	private String address;
	private String phone;
	private String createTime;
	private String isDelete;
	private String style;
	private String type;
	private String cityId;
	private String areaId;
	private String userType;
	private String enterpriseLogo;
	private String enterpriseBannerLogo;
	private String parentUserId;// 父帐户登录名

	private String myareaId;
	private String mycityId;
	private String mystoreNumber;

	private String midischeck;
	private String userIdischeck;
	// 微信公众号二维码图片
	private String wxQrCode;
	
	private String status;
	private String endTime;
	
	private String eTime;
	private String sTime;

	private String managerProvince;

	private String managerCity;

	private int managerType;

	private String managerCompany;

	private String managerResidentail;

	private String managerProvinceName;

	private String managerCityName;
	private String managerCompanyName;

	private String managerResidentailName;

	public String getManagerProvince() {
		return managerProvince;
	}

	public void setManagerProvince(String managerProvince) {
		this.managerProvince = managerProvince;
	}

	public String getManagerCity() {
		return managerCity;
	}

	public void setManagerCity(String managerCity) {
		this.managerCity = managerCity;
	}

	public int getManagerType() {
		return managerType;
	}

	public void setManagerType(int managerType) {
		this.managerType = managerType;
	}

	public String getManagerCompany() {
		return managerCompany;
	}

	public void setManagerCompany(String managerCompany) {
		this.managerCompany = managerCompany;
	}

	public String getManagerResidentail() {
		return managerResidentail;
	}

	public void setManagerResidentail(String managerResidentail) {
		this.managerResidentail = managerResidentail;
	}

	public String getMyareaId() {
		return myareaId;
	}

	public void setMyareaId(String myareaId) {
		this.myareaId = myareaId;
	}

	public String getMycityId() {
		return mycityId;
	}

	public void setMycityId(String mycityId) {
		this.mycityId = mycityId;
	}

	public String getMystoreNumber() {
		return mystoreNumber;
	}

	public void setMystoreNumber(String mystoreNumber) {
		this.mystoreNumber = mystoreNumber;
	}

	public String getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}

	public String getEnterpriseLogo() {
		return enterpriseLogo;
	}

	public void setEnterpriseLogo(String enterpriseLogo) {
		this.enterpriseLogo = enterpriseLogo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIsRootUser() {
		return isRootUser;
	}

	public void setIsRootUser(String isRootUser) {
		this.isRootUser = isRootUser;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getIsMRootUser() {
		return isMRootUser;
	}

	public void setIsMRootUser(String isMRootUser) {
		this.isMRootUser = isMRootUser;
	}

	public String getMidischeck() {
		return midischeck;
	}

	public void setMidischeck(String midischeck) {
		this.midischeck = midischeck;
	}

	public String getUserIdischeck() {
		return userIdischeck;
	}

	public void setUserIdischeck(String userIdischeck) {
		this.userIdischeck = userIdischeck;
	}

	public String getWxQrCode() {
		return wxQrCode;
	}

	public void setWxQrCode(String wxQrCode) {
		this.wxQrCode = wxQrCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String getEnterpriseBannerLogo() {
		return enterpriseBannerLogo;
	}

	public void setEnterpriseBannerLogo(String enterpriseBannerLogo) {
		this.enterpriseBannerLogo = enterpriseBannerLogo;
	}

	public String getManagerProvinceName() {
		return managerProvinceName;
	}

	public void setManagerProvinceName(String managerProvinceName) {
		this.managerProvinceName = managerProvinceName;
	}

	public String getManagerCityName() {
		return managerCityName;
	}

	public void setManagerCityName(String managerCityName) {
		this.managerCityName = managerCityName;
	}

	public String getManagerCompanyName() {
		return managerCompanyName;
	}

	public void setManagerCompanyName(String managerCompanyName) {
		this.managerCompanyName = managerCompanyName;
	}

	public String getManagerResidentailName() {
		return managerResidentailName;
	}

	public void setManagerResidentailName(String managerResidentailName) {
		this.managerResidentailName = managerResidentailName;
	}
}