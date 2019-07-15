package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManagerResourceDao;
import com.threey.guard.manage.domain.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerResourceService extends CrudService<Resource> {

    private Logger logger = Logger.getLogger(ManagerResourceService.class);

    @Autowired
    private ManagerResourceDao managerResourceDao;
    @Override
    protected CrudDAO getDao() {
        return managerResourceDao;
    }

    public List<Privilege> listUnitByResidentail(Map map){
        return managerResourceDao.listUnitByResidentail(map);
    }
    public void saveUnitByResidentail(String resourceId,String units){
        this.managerResourceDao.deleteUnitByResidentail(resourceId);
        this.managerResourceDao.insertUnitByResidentail(resourceId,units);
        //this.managerResourceDao.updateStatus(resourceId);
    }
}
