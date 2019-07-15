package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManageBuildUnitDao;
import com.threey.guard.manage.domain.BuildUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BuildUnitService.java
 *
 * @auth mulths@126.com
 * @date 2019/06/18
 */
@Service
public class BuildUnitService extends CrudService<BuildUnit> {

    @Autowired
    private ManageBuildUnitDao manageBuildUnitDao;

    @Override
    protected CrudDAO getDao() {
        return manageBuildUnitDao;
    }

    public BuildUnit getOne(String id) {
        return manageBuildUnitDao.getOne(id);
    }
}
