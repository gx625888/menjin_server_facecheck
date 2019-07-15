package com.threey.guard.base.dao;

import com.threey.guard.base.domain.Catalog;
import com.threey.guard.base.domain.PageStyle;
import com.threey.guard.base.domain.SystemProperties;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommonDAO extends BaseDAO{
	public String getCurrentDateTime(){
		return (String)getSqlMapClientTemplate().queryForObject("CommonSQL.getCurrentDateTime");
	}

	public void updateSystemPropertiesVauleByCode(HashMap<String, String> parameterMap) {
		getSqlMapClientTemplate().update("CommonSQL.updateSystemPropertiesVauleByCode",parameterMap);
	}

	public HashMap getSystemPropertiesByCode(HashMap<String, String> map) {
		return (HashMap)getSqlMapClientTemplate().queryForObject("CommonSQL.getSystemPropertiesByCode",map);
	}
	
	public void addPcatalog (String mid){
		HashMap<String,String> map =new HashMap<String,String>();
		map.put("mid", mid);
		map.put("catalog", "default");
		getSqlMapClientTemplate().insert("CommonSQL.addPcatalog",map);
	}
	
	public void addPtype (String mid){
		HashMap<String,String> map =new HashMap<String,String>();
		map.put("mid", mid);
		map.put("style", "red");
		getSqlMapClientTemplate().insert("CommonSQL.addPtype",map);
	}
	
	public PageStyle findPageStyle(String mid){
		return (PageStyle) getSqlMapClientTemplate().queryForObject("CommonSQL.findPageStyle",mid);
	}
	
	public Catalog findCatalog(String mid){
		return  (Catalog) getSqlMapClientTemplate().queryForObject("CommonSQL.findCatalog",mid);
	}
	
	public void delSerialid(){
		getSqlMapClientTemplate().delete("CommonSQL.delSerialid");
	}
	
	public void delGainnumber(){
		getSqlMapClientTemplate().delete("CommonSQL.delGainnumber");
	}
	/**
	 * 
	 * 相关说明:根据CODE获取系统配置属性
	 * 开发者:dengyali
	 * 时间:2015-10-15下午2:34:06
	 */
	@SuppressWarnings("unchecked")
	public List<SystemProperties> getSystemProperties(String[] params) {
		return (List<SystemProperties>) getSqlMapClientTemplate().queryForList(
				"CommonSQL.getProperties", params);
	}
	public SystemProperties getSystemProperties(HashMap<String, String> map) {
		return (SystemProperties) getSqlMapClientTemplate().queryForObject("CommonSQL.getSystemProperties",map);
	}
	/**
	 * 相关说明：新增系统配置属性
	 * 开发者：zhangqingbin
	 * 时间：2016-01-19 15:42:30
	 */
	public void addSystemProperties(SystemProperties systemProperties){
		 getSqlMapClientTemplate().insert("CommonSQL.addSystemProperties",systemProperties);
	}
	/**
	 * 相关说明：删除系统配置属性
	 * 开发者：zhangqingbin
	 * 时间：2016-01-19 15:42:30
	 */
	public void delSystemProperties(SystemProperties systemProperties){
		 getSqlMapClientTemplate().delete("CommonSQL.delSystemProperties",systemProperties);
	}
	
	public String findImg(HashMap<String, String> map){
		return (String) getSqlMapClientTemplate().queryForObject("CommonSQL.findImg",map);
	}
	
	public void upImg(HashMap<String, String> map) {
		getSqlMapClientTemplate().update("CommonSQL.upImg",map);
	}
	
	
	public void addImg (HashMap<String, String> map){
		getSqlMapClientTemplate().insert("CommonSQL.addImg",map);
	}


	/**
	 * 取序列值 高并发场景禁用
	 * @param seqName
	 * @return
	 */
	public long getNextVal(String seqName){
		HashMap<String,String> param = new HashMap<>();
		param.put("seqName",seqName);
		return (long) getSqlMapClientTemplate().queryForObject("CommonSQL.getNextSeq",param);
	}
	
}

