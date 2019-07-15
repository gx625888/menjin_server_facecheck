package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.Pages;
import com.threey.guard.manage.dao.ManagerCommunityDao;
import com.threey.guard.manage.domain.Area;
import com.threey.guard.manage.domain.Community;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerCommunityService extends CrudService<Community> {
    @Autowired
    private ManagerCommunityDao managerCommunityDao;

    private Logger logger = Logger.getLogger(ManagerCommunityService.class);

    @Override
    protected CrudDAO getDao() {
        return managerCommunityDao;
    }

    public List<Area> getArea(Area area){
        return managerCommunityDao.getAreaList(area);
    }

    public boolean getResidentailCount(String coummunity){
        return managerCommunityDao.getResidentailCount(coummunity);
    }


}
