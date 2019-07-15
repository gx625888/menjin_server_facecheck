package com.threey.guard.base.dao;

import com.threey.guard.base.domain.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hebaiyun
 */
@Repository
public class MenuDAO extends BaseDAO {

	/**
	 * 
	 * 相关说明:获取菜单树
	 * 开发者:贺白云
	 * 时间:2016-08-03
	 */
	public List<Menu> findMenuByParentId(Menu menu){
		return getSqlMapClientTemplate().queryForList("MenuSQL.findMenuByParentId",menu);
	}
	
	public List<Menu> findMenu(Menu menu){
		return getSqlMapClientTemplate().queryForList("MenuSQL.findMenu",menu);
	}
	
	public List<Menu> findMenuByModularId(Menu menu){
		return getSqlMapClientTemplate().queryForList("MenuSQL.findMenuByModularId",menu);
	}
	
	public List<Menu> findMenuByParentIdOrModularId(Menu menu){
		return getSqlMapClientTemplate().queryForList("MenuSQL.findMenuByParentIdOrModularId",menu);
	}
	
	//删除
		public void delMenu(Menu menu){
			getSqlMapClientTemplate().delete("MenuSQL.delMenu", menu);
		}
		
		//更新
		public void updateMenu(Menu menu){
			getSqlMapClientTemplate().update("MenuSQL.updateMenu", menu);
		}
		//添加
		public void addMenu(Menu menu){
			getSqlMapClientTemplate().insert("MenuSQL.addMenu", menu);
		}

		public List<Menu> findRootNodes(Menu menu) {
		
			return getSqlMapClientTemplate().queryForList("MenuSQL.findRootNodes",menu);
		}

}
