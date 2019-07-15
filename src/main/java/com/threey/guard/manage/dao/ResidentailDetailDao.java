package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.BaseDAO;
import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.BuildUnit;
import com.threey.guard.manage.domain.House;
import com.threey.guard.manage.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ResidentailDetailDao extends CrudDAO<BuildUnit> {


    /**
     * 通过小区id查询楼栋和单元
     * @param residentailId
     * @return
     */
    public List<BuildUnit> getAllBuildUnitByResidentailId(String residentailId){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".getAllBuildUnitByResidentailId",residentailId);
    }

    public List<BuildUnit> getAllBuildByResidentailId(String residentailId){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".getAllBuildByResidentailId",residentailId);
    }


    public List<BuildUnit> queyNextLevalUnit(String id){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".queyNextLevalUnit",id);
    }

    public List<BuildUnit> queyUnbindUnit(String id){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".queyUnbindUnit",id);
    }

    @Override
    protected String getNameSpace() {
        return "ManagerBuildUnitSQL";
    }

    public boolean getHouseCount(String id){
        int count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace()+".getHouseCount",id);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean getBuildUnitCount(String id){
        int count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace()+".getBuildUnitCount",id);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean getDeviceCount(String id){
        int count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace()+".getDeviceCount",id);
        if(count>0){
            return true;
        }
        return false;
    }

    public void insertBatchLevels(List<BuildUnit> buildUnits){
        this.getSqlMapClientTemplate().insert(getNameSpace()+".insertbatchBuild",buildUnits);
    }
    public void insertBatchHouses(List<House> levelHouse){
        this.getSqlMapClientTemplate().insert(getNameSpace()+".insertbatchHouse",levelHouse);
    }
    public void insertBatchPersons(List<Person> personList ){
        this.getSqlMapClientTemplate().insert(getNameSpace()+".insertbatchPerson",personList);
    }

    public void insertBatchHousePerson(List<Map<String,String>> list){
        this.getSqlMapClientTemplate().insert(getNameSpace()+".insertbatchHousePerson",list);

    }
}
