package com.threey.guard.base.dao;

import com.threey.guard.base.domain.Menu;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.domain.UserPrivilege;
import com.threey.guard.base.util.Pages;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限模块底层数据处理
 * @author ZL
 *
 */
@Repository
public class PrivilegeDao extends BaseDAO {
	
	
	/**相关说明：查询系统所有权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午6:27:10
	 */
	public List<Privilege> getSystemAllPrivileges(){
		return getSqlMapClientTemplate().queryForList("PrivilegeSQL.getSystemAllPrivileges");
	}
	
	/**相关说明：查询商家权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午6:26:57
	 */
	public List<Privilege> getPrivileges(Map<String,Object> pMap){
		return getSqlMapClientTemplate().queryForList("PrivilegeSQL.getPrivileges",pMap);
	}
	/**
	 * 相关说明：通过判断一键同步账单数的的权限Code来获取需要同步账单数的mid
	 * 开发者：zhangqingbin
	 * 时间：2015-12-30 15:10:40
	 */
	@SuppressWarnings("unchecked")
	public List<UserPrivilege> selUserPrivileges(String privilegeCode){
		return super.getSqlMapClientTemplate().queryForList("PrivilegeSQL.selUserPrivilege",privilegeCode);
	}
	/**相关说明：给某个商家的某个用户赋予某个权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午2:18:21
	 */
	public int addUserPrivilege(Map<String,Object> pMap){
		return getSqlMapClientTemplate().update("PrivilegeSQL.addUserPrivilege",pMap);
	}
	
	/**相关说明：根据商户号、用户号删除所有权限
	 * 开发者：zhaoliang
	 * 时间：2015年7月6日 下午3:06:04
	 */
	public int delUserPrivilege(String mid, String userId){
	    Map<String,Object> paramMap = new HashMap<String,Object>();
	    paramMap.put("MID", mid);
	    paramMap.put("USERID", userId);
		return getSqlMapClientTemplate().delete("PrivilegeSQL.delUserPrivilege", paramMap);
	}
	
	//后台配置-权限查询
	public List getAllPrivileges(Privilege privilege, int pageNumber){
		return getSqlMapClientTemplate().queryForList("PrivilegeSQL.getAllPrivileges",privilege,pageNumber * Pages.pageSize, Pages.pageSize);
	}
	public List getAllPrivileges(Privilege privilege){
		return getSqlMapClientTemplate().queryForList("PrivilegeSQL.getAllPrivileges",privilege);
	}
	//权限的总条数
	public int count(Privilege privilege){
		return (int) getSqlMapClientTemplate().queryForObject("PrivilegeSQL.count",privilege);
	}
	//删除
	public void delPrivilege(Privilege privilege){
		getSqlMapClientTemplate().delete("PrivilegeSQL.delPrivilege", privilege);
	}
	
	//更新
	public void updatePrivilege(Privilege privilege){
		getSqlMapClientTemplate().update("PrivilegeSQL.updatePrivilege", privilege);
	}
	//添加
	public void addPrivilege(Privilege privilege){
		getSqlMapClientTemplate().insert("PrivilegeSQL.addPrivilege", privilege);
	}
	//得到具体权限对象
	public Privilege getPrivilege(Privilege privilege){
		return (Privilege) getSqlMapClientTemplate().queryForObject("PrivilegeSQL.getAllPrivileges",privilege);
	}
	//添加是查看privilegeCode的值是否已经存在
	public Privilege checkCode(String privilegeCode){
		return (Privilege) getSqlMapClientTemplate().queryForObject("PrivilegeSQL.checkCode",privilegeCode);
	}
	//查询所有模块权限
	public List getModularList(){
		return getSqlMapClientTemplate().queryForList("PrivilegeSQL.getModularList");
	}

	/**
	 * 查询用户可访问的菜单
	 * @param userId 为空时查询所有
	 * @return
	 */
	public List<Menu> getMenuByUserId(String userId){
		Map<String,String> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)){
			map.put("userId",userId);
		}
		return getSqlMapClientTemplate().queryForList("PrivilegeSQL.getMenu",map);
	}
}
