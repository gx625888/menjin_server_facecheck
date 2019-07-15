package com.threey.guard.base.service;

import com.threey.guard.base.dao.CrudDAO;

import java.util.List;
import java.util.Map;

/**
 * 增删改查service
 */
public abstract class CrudService<T>{
    /**
     * 按实体类查询
     * @param obj
     * @return
     */
    public int count(T obj){
       return  getDao().count(obj);
    }

    /**
     * 传入map查询
     * @param params
     * @return
     */
    public int count(Map<String,Object> params){
       return getDao().count(params);
    }

    public List<T> list(Map<String,Object> params,int page,int pageSize){
        return getDao().list(params,page,pageSize);
    }

    public List<T> list(T obj,int page,int pageSize){
        return getDao().list(obj,page,pageSize);
    }

    public int update(T obj){
        return getDao().update(obj);
    }
    public int add(T obj){
        return getDao().add(obj);
    }

    public int del(String id){
        return getDao().del(id);
    }

    public T getOne(String id){
        return (T) getDao().getOne(id);
    }
    protected abstract CrudDAO getDao();

}
