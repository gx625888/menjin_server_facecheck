package com.threey.guard.base.dao;

import java.util.List;
import java.util.Map;

/**
 * 增删改查dao
 * @param <T>
 */
public abstract class CrudDAO<T> extends BaseDAO{

    /**
     * 按实体类查询
     * @param obj
     * @return
     */
    public int count(T obj){

        return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace()+".count",obj);
    }

    /**
     * 传入map查询
     * @param params
     * @return
     */
    public int count(Map<String,Object> params){
        return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace()+".countByMap",params);
    }

    public List<T> list(Map<String,Object> params,int page,int pageSize){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".listByMap",params,page*pageSize,pageSize);
    }

    public List<T> list(T obj,int page,int pageSize){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".list",obj,page*pageSize,pageSize);
    }

    public int update(T obj){
        return getSqlMapClientTemplate().update(getNameSpace()+".update", obj);
    }
    public int add(T obj){
        getSqlMapClientTemplate().insert(getNameSpace()+".insert",obj);
        return 1;
    }

    public int del(String id){
       return  getSqlMapClientTemplate().delete(getNameSpace()+".delete",id);

    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int fakeDel(String id){
        getSqlMapClientTemplate().update(getNameSpace()+".fakeDel",id);
        return 0;
    }
    public T getOne(String id){
        return (T) getSqlMapClientTemplate().queryForObject(getNameSpace()+".queryOne",id);
    }
    protected  abstract  String getNameSpace();
}
