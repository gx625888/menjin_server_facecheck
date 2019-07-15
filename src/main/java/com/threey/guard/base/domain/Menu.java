package com.threey.guard.base.domain;

/**
 *  菜单列表
 * @author hebaiyun
 *
 */
public class Menu {
	//菜单标示
private String id;

//菜单名称
private String name;

//菜单权限code
private String code;

//modular的Id
private String modularId;

//父菜单ID
private String parentId;

//菜单链接
private String url;

//菜单排序号
private int sort;

//菜单创建时间
private String createTime;

//删除标志位
private String delFlag;

//级别
private int level;
//是否最后一级 1是,0否
private String lastLevel;

private String mid;

private String userId;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public String getModularId() {
	return modularId;
}

public void setModularId(String modularId) {
	this.modularId = modularId;
}

public String getParentId() {
	return parentId;
}

public void setParentId(String parentId) {
	this.parentId = parentId;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public int getSort() {
	return sort;
}

public void setSort(int sort) {
	this.sort = sort;
}

public String getCreateTime() {
	return createTime;
}

public void setCreateTime(String createTime) {
	this.createTime = createTime;
}

public String getDelFlag() {
	return delFlag;
}

public void setDelFlag(String delFlag) {
	this.delFlag = delFlag;
}

public int getLevel() {
	return level;
}

public void setLevel(int level) {
	this.level = level;
}

public String getLastLevel() {
	return lastLevel;
}

public void setLastLevel(String lastLevel) {
	this.lastLevel = lastLevel;
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



}
