package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.Card;
import com.threey.guard.manage.domain.Person;
import com.threey.guard.manage.domain.QueryResidentailByPerson;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ManagerPersonDao extends CrudDAO<Person> {


    public void saveHousePerson(Map<String,Object> map){
        this.getSqlMapClientTemplate().insert(getNameSpace()+".saveHousePerson",map);
    }

    @Override
    protected String getNameSpace() {
        return "ManagerPersonSQL";
    }

    public void batchInsert(List<Map<String,Object>> list){
        this.getSqlMapClientTemplate().insert(getNameSpace()+".insertBatchPerson",list);
    }

    public List<QueryResidentailByPerson> queryResidentailByPerson(String id, int page, int pageSize){
        return (List<QueryResidentailByPerson>) this.getSqlMapClientTemplate().queryForList(getNameSpace()+".queryResidentailByPerson",id,page,pageSize);
    }

    public int countResidentailByPerson(String id){
        return (Integer) this.getSqlMapClientTemplate().queryForObject(getNameSpace()+".countResidentailByPerson",id);
    }

    public List<Card> queryCardByPerson(String id, int page, int pageSize){
        return (List<Card>) this.getSqlMapClientTemplate().queryForList(getNameSpace()+".queryCardByPerson",id,page,pageSize);
    }

    public int countCardByPerson(String id){
        return (Integer) this.getSqlMapClientTemplate().queryForObject(getNameSpace()+".countCardByPerson",id);
    }

    public void deleteBandInfo(Map map){
        this.getSqlMapClientTemplate().delete(getNameSpace()+".deleteBandInfo",map);
    }

    public List<Person> listHousePersons(Map<String, Object> params, int page, int pageSize) {
        return (List<Person>) this.getSqlMapClientTemplate().queryForList(getNameSpace()+".listHousePerson",params,page,pageSize);
    }
}
