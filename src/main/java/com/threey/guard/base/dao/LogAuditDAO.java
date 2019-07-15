package com.threey.guard.base.dao;

import com.threey.guard.base.domain.LogAudit;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class LogAuditDAO extends BaseDAO {

    public List<LogAudit> getList(String key,String sDate,String eDate, int page,int pageSize){
        HashMap<String,String> params = new HashMap<>();
        params.put("type",key);
        params.put("sDate",sDate);
        params.put("eDate",eDate);
        return getSqlMapClientTemplate().queryForList("LogAudit.getList",params,page*pageSize,pageSize);
    }

    public List<LogAudit> getLogList(String key,String sDate,String eDate){
        HashMap<String,String> params = new HashMap<>();
        params.put("type",key);
        params.put("sDate",sDate);
        params.put("eDate",eDate);
        return getSqlMapClientTemplate().queryForList("LogAudit.getList",params);
    }


    public int getCount(String key,String sDate,String eDate,String mid){
        HashMap<String,String> params = new HashMap<>();
        params.put("type",key);
        params.put("sDate",sDate);
        params.put("eDate",eDate);
        return (Integer)getSqlMapClientTemplate().queryForObject("LogAudit.getCount",params);
    }

    public void insert(LogAudit log){
        getSqlMapClientTemplate().insert("LogAudit.insert",log);

    }
}
