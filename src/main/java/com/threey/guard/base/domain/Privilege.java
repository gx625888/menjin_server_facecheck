package com.threey.guard.base.domain;

import java.io.Serializable;

/**
 * 权限实体Bean
 * @author ZL
 *
 */
public class Privilege implements Serializable {
	private String privilegeCode; //权限Code
	private String privilegeName; //权限名称
	private String privilegeURL; //权限URL
	private String modularId; //权限所属模块Id
	private String modularName;//权限所属模块名称
	private String modularIdSelect;//menu菜单中的modularId
	private String hasPrivilege;
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	public String getModularId() {
		return modularId;
	}
	public void setModularId(String modularId) {
		this.modularId = modularId;
	}
	public String getModularName() {
		return modularName;
	}
	public void setModularName(String modularName) {
		this.modularName = modularName;
	}
	public String getPrivilegeURL() {
		return privilegeURL;
	}
	public void setPrivilegeURL(String privilegeURL) {
		this.privilegeURL = privilegeURL;
	}
	public String getModularIdSelect() {
		return modularIdSelect;
	}
	public void setModularIdSelect(String modularIdSelect) {
		this.modularIdSelect = modularIdSelect;
	}

	public String getHasPrivilege() {
		return hasPrivilege;
	}

	public void setHasPrivilege(String hasPrivilege) {
		this.hasPrivilege = hasPrivilege;
	}
}
