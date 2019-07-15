package com.threey.guard.manage.domain;
/**
 * 商户经营模式
 * @author wudouzhu
 */
public class ManagementMode {

	/*
	 * 经营模式
	 * 1/0 经营/不经营
	 */
	private String takeaway_flag;//外卖
	private String dinner_flag;//堂食
	private String fast_food_flag;//快餐
	private String update_time;
	
	private String mid;
	
	private String finalFactory;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTakeaway_flag() {
		return takeaway_flag;
	}

	public void setTakeaway_flag(String takeaway_flag) {
		this.takeaway_flag = takeaway_flag;
	}

	public String getDinner_flag() {
		return dinner_flag;
	}

	public void setDinner_flag(String dinner_flag) {
		this.dinner_flag = dinner_flag;
	}

	public String getFast_food_flag() {
		return fast_food_flag;
	}

	public void setFast_food_flag(String fast_food_flag) {
		this.fast_food_flag = fast_food_flag;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getFinalFactory() {
		return finalFactory;
	}

	public void setFinalFactory(String finalFactory) {
		this.finalFactory = finalFactory;
	}
	
	
	
}
