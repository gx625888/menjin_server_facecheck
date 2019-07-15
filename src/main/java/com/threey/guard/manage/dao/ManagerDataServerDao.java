package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ManagerDataServerDao extends CrudDAO<Plan>{
    @Override
    protected String getNameSpace() {
        return "ManagerDataServerSQL";
    }

    public List<OpenRecord> getOpenRecordList(Map map,int page,int pageSize){
        return (List<OpenRecord>)getSqlMapClientTemplate().queryForList("ManagerDataServerSQL.selectOpenRecord",map,page*pageSize,pageSize);
    }

    public int openRecordCount(Map map){
        return (Integer) getSqlMapClientTemplate().queryForObject("ManagerDataServerSQL.openRecordCount",map);
    }

    public List<WarnRecord> getWarnRecordList(Map map,int page,int pageSize){
        return (List<WarnRecord>)getSqlMapClientTemplate().queryForList("ManagerDataServerSQL.selectWarnRecord",map,page*pageSize,pageSize);
    }

    public int openWarnCount(Map map){
        return (Integer) getSqlMapClientTemplate().queryForObject("ManagerDataServerSQL.warnRecordCount",map);
    }

    public List<Community> queryCommunity(Map<String,Object> map){
        return (List<Community>)getSqlMapClientTemplate().queryForList("ManagerDataServerSQL.queryCommunity",map);
    }

    public List<Residentail> queryResidentail(String id){
        return (List<Residentail>)getSqlMapClientTemplate().queryForList("ManagerDataServerSQL.queryResidentail",Integer.parseInt(id));
    }

    public List<BuildUnit> queryBuild(String id){
        return (List<BuildUnit>)getSqlMapClientTemplate().queryForList("ManagerDataServerSQL.queryBuild",id);
    }

    public List<BuildUnit> queryUnit(String id){
        return (List<BuildUnit>)getSqlMapClientTemplate().queryForList("ManagerDataServerSQL.queryUnit",Integer.parseInt(id));
    }

    public List<House> queryHouse(String id){
        return (List<House>)getSqlMapClientTemplate().queryForList("ManagerDataServerSQL.queryHouse",Integer.parseInt(id));
    }
}
