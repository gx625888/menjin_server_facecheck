package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.Device;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ManagerDeviceDao extends CrudDAO<Device> {
    @Override
    protected String getNameSpace() {
        return "ManagerDeviceSQL";
    }

    /**
     * 查询绑定的单元设备
     * @param unitId
     * @return
     */
    public Device getDeviceByUnit(String unitId){
        return (Device) getSqlMapClientTemplate().queryForObject(getNameSpace()+".getDeviceByUnit",unitId);
    }

    /**
     * 根据设备ID查询设备
     * */
    public Device getDeviceById(String deviceId) {
        return (Device) getSqlMapClientTemplate().queryForObject(getNameSpace()+".getDeviceById",deviceId);
    }

    /**
     * 查询小区设备
     * @return
     */
    public List<Device> getDeviceByResidentail(String residentail){
        return  getSqlMapClientTemplate().queryForList(getNameSpace()+".getDeviceByResidentail",residentail);
    }

    /**
     * 绑定单元
     * @param deviceId
     * @param unitId
     */
    public void bind(String deviceId,String unitId){
        Map<String,String> params = new HashMap<>();
        params.put("deviceId",deviceId);
        params.put("unitId",unitId);
        getSqlMapClientTemplate().update(getNameSpace()+".bindDevice",params);
    }

    /**
     * 解绑
     * @param deviceId
     * @param unitId
     */
    public void unBind(String deviceId,String unitId){
        Map<String,String> params = new HashMap<>();
        params.put("deviceId",deviceId);
        params.put("unitId",unitId);
        getSqlMapClientTemplate().update(getNameSpace()+".unBindDevice",params);
    }

    public List<Device> listUnBind(Map<String,Object> obj, int page, int pageSize) {
        return  getSqlMapClientTemplate().queryForList(getNameSpace()+".listunbind",obj,page*pageSize,pageSize);
    }

    public int countUnbind(Map<String,Object> obj){
        return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace()+".countunbind",obj);
    }

    public void batchInsertDevice(List<Map<String,Object>> list){
        //getSqlMapClientTemplate().insert(getNameSpace()+".insertBatchDevice",list);
        getSqlMapClientTemplate().insert(getNameSpace()+".insertBatchDeviceTemp",list);
    }

    public void batchDeviceTemp(){
        getSqlMapClientTemplate().insert(getNameSpace()+".batchDeviceTemp");
    }

    public List<Device> getDeviceList(Map<String,Object> obj) {
        return  getSqlMapClientTemplate().queryForList(getNameSpace()+".listByMap",obj);
    }

}
