package com.threey.guard.wechat.dao;

import com.threey.guard.base.dao.BaseDAO;
import com.threey.guard.wechat.domain.SelectObj;
import com.threey.guard.wechat.domain.WeChatPropertis;
import com.threey.guard.wechat.domain.WxBand;
import com.threey.guard.wechat.domain.WxBandVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ManagerWxBandDao extends BaseDAO {

    protected String getNameSpace() {
        return "ManageWxBandSQL";
    }

    public List<SelectObj> getListObj(String id,int type){
        List<SelectObj> list = new ArrayList<>();
        if(type==1){
            list = getSqlMapClientTemplate().queryForList(this.getNameSpace()+".queryResidentail");
        }else if (type==2){
            list = getSqlMapClientTemplate().queryForList(this.getNameSpace()+".queryBuild",id);
        }else if (type==3){
            list = getSqlMapClientTemplate().queryForList(this.getNameSpace()+".queryUnit",id);
        }else if (type==4){
            list = getSqlMapClientTemplate().queryForList(this.getNameSpace()+".queryFloor",id);
        }else {
            list = getSqlMapClientTemplate().queryForList(this.getNameSpace()+".queryHouse",id);
        }
        return list;
    }

    public void insertWxBand(WxBand wxBand){
        getSqlMapClientTemplate().insert(this.getNameSpace()+".wxBandInsert",wxBand);
    }

    public String queryPersonId(Map<String,Object> map){
        return (String) getSqlMapClientTemplate().queryForObject(this.getNameSpace()+".queryPersonId",map);
    }


    public List<WxBandVO> getBindHouses(String wxOpenId) throws SQLException {
        return getSqlMapClient().queryForList(this.getNameSpace()+".getBindHouses",wxOpenId);
    }

    public List<WxBandVO> getBindHousesByPhone(String phone,String company) throws SQLException {

        HashMap<String,String> param = new HashMap<>();
        param.put("phone",phone);
        param.put("company",company);
        return getSqlMapClient().queryForList(this.getNameSpace()+".getBindHousesByPhone",param);
    }

    public void unbind(String wxOpenId,String houseId) throws SQLException {
        Map<String,String> map = new HashMap<>();
        map.put("wxOpenId",wxOpenId);
        map.put("houseId",houseId);
        getSqlMapClient().delete(getNameSpace()+".unbind",map);
    }

    public int checkPhoneExists(String phone){
        return (Integer) getSqlMapClientTemplate().queryForObject(this.getNameSpace()+".checkPhoneExists",phone);
    }

    public void cleanBind(String openId,String company){
        HashMap<String,String> param = new HashMap<>();
        param.put("openId",openId);
        param.put("company",company);
        this.getSqlMapClientTemplate().update(this.getNameSpace()+".clearBind",param);
    }

    public void bindPhone(String openId,String phone,String company){
       WxBand wxBand = new WxBand();
       wxBand.setWxId(openId);
       wxBand.setPhone(phone);
       wxBand.setCompany(company);
       this.insertWxBand(wxBand);

    }

    /**
     * 获取绑定的手机号
     * @param param
     * @return
     */
    public String getBindPhone(Map<String,String> param){
        return  (String) getSqlMapClientTemplate().queryForObject(this.getNameSpace()+".getBindPhone",param);
    }

    public WeChatPropertis getWeChatPropertis(String company){

        if (StringUtils.isEmpty(company)){
            return null;
        }

        List<WeChatPropertis> list = this.getSqlMapClientTemplate().queryForList(this.getNameSpace()+".getWeChatProperties",company);

        if (null!=list && list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    public List<WeChatPropertis> getAllWeChatPropertis(){
        return this.getSqlMapClientTemplate().queryForList(this.getNameSpace()+".getWeChatPropertiesAll");
    }

    public void updateAppToken(WeChatPropertis propertis){
        this.getSqlMapClientTemplate().update(this.getNameSpace()+".updateWeChatToken",propertis);
    }

    public List<WxBand> getWxBandByHouseDevice(String house,String device){
        HashMap<String,String> param = new HashMap<>();
        param.put("house",house);
        param.put("device",device);
        return this.getSqlMapClientTemplate().queryForList(this.getNameSpace()+".getWxBindByHouseDevice",param);
    }

    public String getResNameByDevice(String device){
        return (String) this.getSqlMapClientTemplate().queryForObject(this.getNameSpace()+".getResNameByDevice",device);
    }
}
