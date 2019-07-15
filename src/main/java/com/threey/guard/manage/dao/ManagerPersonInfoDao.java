package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.ManagerInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ManagerPersonInfoDao extends CrudDAO<ManagerInfo> {
    @Override
    protected String getNameSpace() {
        return "ManagerPersonInfoSQL";
    }

    public List<String> getRoleList(String id){
        return (List<String>)getSqlMapClientTemplate().queryForList("ManagerPersonInfoSQL.queryRoleName",id);
    }

    public int updatePW(Map map){
        return getSqlMapClientTemplate().update("ManagerPersonInfoSQL.updatePW",map);
    }
}
