package com.threey.guard.api.dao;

import com.threey.guard.api.domain.*;
import com.threey.guard.base.dao.BaseDAO;
import com.threey.guard.manage.domain.HouseUnit;
import com.threey.guard.manage.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientApiDao extends BaseDAO {

    public List<String> versonUpdate(String deviceId){
        return (List<String>)getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.versonUpdate",deviceId);
    }


    public List<Ads> getAds(String deviceId){
        return (List<Ads>)getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getAds",deviceId);
    }

    public void openRecord(Map map){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.openRecord",map);
    }

    public List<Person> getPersonId(String cardId){
        return getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getPersonId",cardId);
    }

    public void warnRecord(Map map){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.warnRecord",map);
    }

    public List<AccountData> getAccounts(String deviceId){
        return (List<AccountData>)getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getAccounts",deviceId);
    }

    public List<HousePhoneData> getHousePhoneData(String deviceId){
        return (List<HousePhoneData>)getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getHousePhoneData",deviceId);
    }

    public List<String> getPwd(String deviceId){
        return (List<String>)getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getPwd",deviceId);
    }

    public void updatePwd(Map map){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.updatePwd",map);
    }

    public void invalidPwd(Integer time){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.invalidPwd",time);
    }

    public void insertPwd(Map map){
        getSqlMapClientTemplate().insert("ManagerClientApiSQL.insertPwd",map);
    }

    public List<DeviceArea> getDeviceArea(String deviceId){
        return (List<DeviceArea>)getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getAreaByDevice",deviceId);
    }

    /**
     * 不要使用此方法
     * @param id
     * @return
     */
    @Deprecated
    public String getHouseByPerson(String id){
        return (String)getSqlMapClientTemplate().queryForObject("ManagerClientApiSQL.getHouseByPerson",id);
    }

    public List<HouseUnit> getHouseByPersonAndDevice(String personId,String deviceId){
        Map<String,String> map = new HashMap<>();
        map.put("personId",personId);
        map.put("deviceId",deviceId);
        return this.getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getHouseByPersonAndDevice",map);
    }

    public String getTelByDevice(String deviceId){
        return (String)getSqlMapClientTemplate().queryForObject("ManagerClientApiSQL.getTelByDevice",deviceId);
    }

    public List<CallInfo> getCallInfo(Map<String,Object> map){
        return (List<CallInfo>)getSqlMapClientTemplate().queryForList("ManagerClientApiSQL.getCallInfo",map);
    }

    public void insertCallRecord(Map<String,Object> map){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.InsertCallRecord",map);
    }

    public TelRecord getCallRecord(String callId){
        return (TelRecord) getSqlMapClientTemplate().queryForObject("ManagerClientApiSQL.queryTelRecord",callId);
    }

    public void updateCallRecord(TelRecord record){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.updateTelRecord",record);
    }
    public void updateCallRecordById(HashMap<String,String> record){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.updateTelRecordById",record);
    }

    public void callRecord(Map map){
        getSqlMapClientTemplate().update("ManagerClientApiSQL.callRecord",map);
    }


}
