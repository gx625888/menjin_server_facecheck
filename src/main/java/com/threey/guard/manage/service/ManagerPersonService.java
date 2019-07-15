package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManagerPersonDao;
import com.threey.guard.manage.domain.Card;
import com.threey.guard.manage.domain.Person;
import com.threey.guard.manage.domain.QueryResidentailByPerson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerPersonService extends CrudService<Person> {

    private Logger logger = Logger.getLogger(ManagerPersonService.class);

    @Autowired
    ManagerPersonDao managerPersonDao;

    public void saveHousePerson(Map<String,Object> map){
        this.managerPersonDao.saveHousePerson(map);
    }

    public List<Person> listHousePersons(Map<String, Object> params, int page,int pageSize) {
        return managerPersonDao.listHousePersons(params, page, pageSize);
    }

    @Override
    protected CrudDAO getDao() {
        return managerPersonDao;
    }

    public void batchInsert(List<Map<String,Object>> list){
        this.managerPersonDao.batchInsert(list);
    }

    public List<QueryResidentailByPerson> queryResidentailByPerson(String id,int page,int pageSize){
        return this.managerPersonDao.queryResidentailByPerson(id,page,pageSize);
    }

    public int countResidentailByPerson(String id){
        return this.managerPersonDao.countResidentailByPerson(id);
    }

    public List<Card> queryCardByPerson(String id,int page,int pageSize){
        return this.managerPersonDao.queryCardByPerson(id,page,pageSize);
    }

    public int countCardByPerson(String id){
        return this.managerPersonDao.countCardByPerson(id);
    }

    public void deleteBandInfo(Map map){
        this.managerPersonDao.deleteBandInfo(map);
    }
}
