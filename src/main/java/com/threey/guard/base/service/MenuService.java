package com.threey.guard.base.service;

import com.threey.guard.base.dao.MenuDAO;
import com.threey.guard.base.domain.Menu;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单service
 * 
 * @author 贺白云
 *
 */
@Service
public class MenuService {

	private static final Logger LOGGER = Logger.getLogger(MenuService.class);

	@Autowired
	private MenuDAO menuDAO;
	
	/**
	 * 相关说明：菜单查询
	 */
	public List<Menu> findMenuByParentId(Menu menu) {
		return menuDAO.findMenuByParentId(menu);
	}
	
	public List<Menu> findMenu(Menu menu) {
		return menuDAO.findMenu(menu);
	}
	
	public List<Menu> findMenuByModularId(Menu menu) {
		return menuDAO.findMenuByModularId(menu);
	}
	
	public List<Menu> findMenuByParentIdOrModularId(Menu menu) {
		return menuDAO.findMenuByParentIdOrModularId(menu);
	}
	
	//删除
	public void delMenu(Menu menu){
		menuDAO.delMenu(menu);
	}
	
	//更新
	public void updateMenu(Menu menu){
		menuDAO.updateMenu(menu);
	}
	//添加
	public void addMenu(Menu menu){
		menuDAO.addMenu(menu);
	}

	public List<Menu> findRootNodes(Menu menu) {
		
		return menuDAO.findRootNodes(menu);
	}

}
