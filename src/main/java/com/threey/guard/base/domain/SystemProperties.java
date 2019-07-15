package com.threey.guard.base.domain;

import java.io.Serializable;

public class SystemProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 编码 */
	private String code;
	/** 名称 */
	private String name;
	/**值*/
	private String value;
	/** 同步时间点 */
	private String synStartTime;
	/** 是否自动同步 */
	private String isAuto;
	/** 同步频率 */
	private String frequency;
	/** 同步结束点 */
	private String synEndTime;
	
	private String storeName;
	
	private String ticketName;
	
	private String mid;
	

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSynStartTime() {
		return synStartTime;
	}

	public void setSynStartTime(String synStartTime) {
		this.synStartTime = synStartTime;
	}

	public String getIsAuto() {
		return isAuto;
	}

	public void setIs_auto(String isAuto) {
		this.isAuto = isAuto;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSynEndTime() {
		return synEndTime;
	}

	public void setSynEndTime(String synEndTime) {
		this.synEndTime = synEndTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	
}
