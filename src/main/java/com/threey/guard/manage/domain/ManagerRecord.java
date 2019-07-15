package com.threey.guard.manage.domain;

import java.io.Serializable;

/**
 * @author ZL
 * 
 * 
 */
public class ManagerRecord implements Serializable {
	private static final long serialVersionUID = 2699702563871088648L;
	private String mid;
	private String id;
	private String startTime;
	private String endTime;
	private String creatTime;
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
}