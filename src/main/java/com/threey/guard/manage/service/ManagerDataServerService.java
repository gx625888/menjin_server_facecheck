package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManagerDataServerDao;
import com.threey.guard.manage.domain.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerDataServerService extends CrudService<Plan> {

    private Logger logger = Logger.getLogger(ManagerDataServerService.class);

    @Autowired
    ManagerDataServerDao managerDataServerDao;
    @Override
    protected CrudDAO getDao() {
        return this.managerDataServerDao;
    }

    public List<OpenRecord> getOpenRecordList(Map map,int page,int pageSize){
        return this.managerDataServerDao.getOpenRecordList(map,page,pageSize);
    }

    public int countOpenRecord(Map map){
        return this.managerDataServerDao.openRecordCount(map);
    }

    public List<WarnRecord> getWarnRecordList(Map map,int page,int pageSize){
        return this.managerDataServerDao.getWarnRecordList(map,page,pageSize);
    }

    public int countWarnRecord(Map map){
        return this.managerDataServerDao.openWarnCount(map);
    }

    public List<Community> queryCommunity(Map<String,Object> map){
        return this.managerDataServerDao.queryCommunity(map);
    }

    public List<Residentail> queryResidentail(String id){
        return this.managerDataServerDao.queryResidentail(id);
    }

    public List<BuildUnit> queryBuild(String id){
        return this.managerDataServerDao.queryBuild(id);
    }

    public List<BuildUnit> queryUnit(String id){
        return this.managerDataServerDao.queryUnit(id);
    }

    public List<House> queryHouse(String id){
        return this.managerDataServerDao.queryHouse(id);
    }
}
