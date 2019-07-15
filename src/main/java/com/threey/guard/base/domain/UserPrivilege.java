package com.threey.guard.base.domain;
/**
 * 相关说明：通过判断一键同步账单数的的权限Code来获取需要同步账单数的mid
 * 
 * @author zhangqingbin
 * 时间：2015-12-30 15:07：30
 */
public class UserPrivilege {
	private String mid;
	private String privilegeCode;
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	
}
