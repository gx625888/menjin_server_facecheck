package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.Device;
import com.threey.guard.manage.domain.HouseUnit;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ManageHouseDao extends CrudDAO<HouseUnit> {
    @Override
    protected String getNameSpace() {
        return "ManageHouseSql";
    }

    public HouseUnit getHouseByUnitAndName(long unitId, String houseNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("unitId", unitId);
        map.put("name", houseNo);
        return (HouseUnit) getSqlMapClientTemplate().queryForObject(getNameSpace()+".getHouseByUnitAndName",map);
    }
}
