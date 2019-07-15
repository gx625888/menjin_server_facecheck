package com.threey.guard.base.dao;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 继承此类访问wicp数据库
 * @author tangchang
 */
public class BaseDAO extends SqlMapClientDaoSupport{

	@Resource(name = "sqlMapClientTemplate")
	private SqlMapClientTemplate sqlMapClientTemplate;

	@PostConstruct
	public void initSqlMapClientTemplate() {
		super.setSqlMapClientTemplate(sqlMapClientTemplate);
	}
}
