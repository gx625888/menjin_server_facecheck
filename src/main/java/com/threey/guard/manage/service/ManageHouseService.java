package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManageHouseDao;
import com.threey.guard.manage.domain.HouseUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageHouseService extends CrudService<HouseUnit> {

    @Autowired
    private ManageHouseDao dao;


    @Override
    protected CrudDAO getDao() {
        return dao;
    }

    public HouseUnit getHouseByUnitAndName(long unitId, String houseNo) {
        return dao.getHouseByUnitAndName(unitId, houseNo);
    }

}
