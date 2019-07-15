package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.BaseDAO;
import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.util.Pages;
import com.threey.guard.manage.domain.ManagementMode;
import com.threey.guard.manage.domain.ManagerRecord;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZL
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class ManagerUserDAO extends CrudDAO<ManagerUser> {

	
	/**相关说明：
	 * 开发者：zhaoliang
	 * 时间：2015年7月3日 下午4:09:35
	 */
	public List<ManagerUser> findManagerUser(ManagerUser mu) {
		return (List<ManagerUser>)getSqlMapClientTemplate().queryForList("ManagerUserSQL.findManagerUser",mu);
	}	
	
	
	public List<ManagerUser> getNoteUserList() {
		return (List<ManagerUser>)getSqlMapClientTemplate().queryForList("ManagerUserSQL.getNoteUserList");
	}	
	
	
	
	public int getManagerUserCount(ManagerUser mu){
		return (int) getSqlMapClientTemplate().queryForObject("ManagerUserSQL.getManagerUserCount", mu);
	}
	
	public List<ManagerUser> getManagerUserByPage(ManagerUser mu, int pageNo){
		return getSqlMapClientTemplate().queryForList("ManagerUserSQL.findManagerUser", mu, (pageNo -1) * Pages.pageSize, Pages.pageSize);
	}
	
	/**相关说明：添加商户
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午12:54:10
	 */
	public int addManagerUser(ManagerUser mu){
		return getSqlMapClientTemplate().update("ManagerUserSQL.addManagerUser",mu);
	}
	
	
	/**相关说明：修改商户
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午7:29:50
	 */
	public int updateManagerUser(ManagerUser mu){
		return getSqlMapClientTemplate().update("ManagerUserSQL.updateManagerUser",mu);
	}
	
	public List<ManagerRecord> getManagerRecordList(ManagerRecord managerRecord){
		return  getSqlMapClientTemplate().queryForList("ManagerUserSQL.getManagerRecordList",managerRecord);
	}
	
	public String getManagerRecord(String mid){
		String time ="";
		ManagerRecord managerRecord = new ManagerRecord();
		managerRecord.setMid(mid);
		List<ManagerRecord> list = getManagerRecordList(managerRecord);
		if (null != list && list.size()>0){
			time = list.get(0).getEndTime();
		}		
		return time;
	}
	
	public void addManagerRecord(ManagerRecord managerRecord){
		getSqlMapClientTemplate().insert("ManagerUserSQL.addManagerRecord",managerRecord);
	}
	
	
	/**相关说明：删除商户信息（物理删除）
	 * 开发者：zhaoliang
	 * 时间：2015年7月7日 下午5:01:45
	 */
	public int delManagerUser(ManagerUser mu){
		return getSqlMapClientTemplate().delete("ManagerUserSQL.delManagerUser",mu);
	}
	
	public int delManagerRecord(String mid){
		return getSqlMapClientTemplate().delete("ManagerUserSQL.delManagerRecord",mid);
	}
	
	public void delChildManagerUser(ManagerUser mu){
		getSqlMapClientTemplate().delete("ManagerUserSQL.delChildManagerUser",mu);
	}
	public List<ManagerUser> selChildManagerUser(ManagerUser mu){
		return  getSqlMapClientTemplate().queryForList("ManagerUserSQL.selChildManagerUser",mu);
	}
	/**
	 * 
	 * @相关说明：设置密码
	 * @开发者：汤云涛
	 * @时间：2015年8月18日 上午11:03:17
	 */
	public int setPwd(String mid, String userId, String pwd){
		ManagerUser mu = new ManagerUser();
		mu.setMid(mid);
		mu.setUserId(userId);
		mu.setPassword(pwd);
		return getSqlMapClientTemplate().update("ManagerUserSQL.setPwd", mu);
	}


	/*添加该商户的餐饮经营模式*/
	public void addManagementMode(ManagementMode managementMode) {
		getSqlMapClientTemplate().insert("ManagerUserSQL.addManagementMode",managementMode);
	}

	/*修改该商户的餐饮经营模式*/
	public void editManagementMode(ManagementMode managementMode) {
		getSqlMapClientTemplate().update("ManagerUserSQL.editManagementMode",managementMode);
	}

	/*查询餐饮*/
	public ManagementMode findManagementMode(ManagementMode managementMode) {
		return (ManagementMode) getSqlMapClientTemplate().queryForObject("ManagerUserSQL.findManagementMode", managementMode);
	}
	
	public List<ManagerUser> findManUser(){
		return  getSqlMapClientTemplate().queryForList("ManagerUserSQL.findManUser");		
	}
	
	public ManagerUser getManUser(String mid){
		return  (ManagerUser) getSqlMapClientTemplate().queryForObject("ManagerUserSQL.getManUser",mid);		
	}
	
	public ManagerRecord findManRecord(String mid){
		return (ManagerRecord) getSqlMapClientTemplate().queryForObject("ManagerUserSQL.findManRecord",mid);		
	}

	public void fackDel(String mid) {
		getSqlMapClientTemplate().update("ManagerUserSQL.fackDel",mid);
	}
		@Override
	public String getNameSpace(){
		return "ManagerUserSQL";
	}


}