package com.threey.guard.wechat.service;

import com.threey.guard.base.util.GETProperties;
import com.threey.guard.clientmsg.MsgRouteFact;
import com.threey.guard.wechat.dao.ManagerWxBandDao;
import com.threey.guard.wechat.domain.SelectObj;
import com.threey.guard.wechat.domain.WeChatPropertis;
import com.threey.guard.wechat.domain.WxBand;
import com.threey.guard.wechat.domain.WxBandVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerWxBandService {
    @Autowired
    private ManagerWxBandDao managerWxBandDao;
    public List<SelectObj> getSelectObj(String id,int type){
        return managerWxBandDao.getListObj(id,type);
    }

    public void insertWxBand(WxBand wxBand){
        managerWxBandDao.insertWxBand(wxBand);
    }

    public String queryPersonId(Map<String,Object> map){
        return managerWxBandDao.queryPersonId(map);
    }


    public List<WxBandVO> getBindHouses(String wxOpenId){

        List<WxBandVO>  list = new ArrayList<>();
        try {
            list = this.managerWxBandDao.getBindHouses(wxOpenId);
            checkOnline(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<WxBandVO> getBindHousesByPhone(String phone,String company){

        List<WxBandVO>  list = new ArrayList<>();
        try {
            list = this.managerWxBandDao.getBindHousesByPhone(phone,company);
            checkOnline(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    private void checkOnline(List<WxBandVO> list){
        if (CollectionUtils.isNotEmpty(list)){
            for (WxBandVO bindInfo: list) {
                if (StringUtils.isNotEmpty(bindInfo.getDeviceId())){//设备在线状态 1在线0离线
                    bindInfo.setDeviceStatus(MsgRouteFact.instance.getInstance().isClientOnline(bindInfo.getDeviceId())?"1":"0");
                }
            }
        }
    }

    public boolean checkPhone(String phone){
        return this.managerWxBandDao.checkPhoneExists(phone)>0;
    }

    public void cleanBind(String openId,String company){
        this.managerWxBandDao.cleanBind(openId,company );
    }

    /**
     * 绑定手机号
     * @param openId
     * @param phone
     */
    public void bindPhone(String openId,String phone,String company){
        this.managerWxBandDao.cleanBind(openId,company );
        this.managerWxBandDao.bindPhone(openId, phone,company);
    }

    /**
     * 获取绑定的手机号
     * @param openId
     * @return
     */
    public String getBindPhone(String openId,String company){
        Map<String,String> param = new HashMap<>();
        param.put("openId",openId);
        param.put("company",company);
        return this.managerWxBandDao.getBindPhone(param);
    }


    public WeChatPropertis getWeChatPropertis(String company){
        if (StringUtils.isEmpty(company)){
            return defaultPropertis();
        }
        WeChatPropertis propertis = this.managerWxBandDao.getWeChatPropertis(company);
        if (null == propertis){
            propertis = defaultPropertis();
        }
        return propertis;
    }

    private WeChatPropertis defaultPropertis(){
//        WeChatPropertis propertis = new WeChatPropertis();
//        propertis.setId("0");
//        propertis.setAppId(GETProperties.readValue("wechat.appid"));
//        propertis.setAppSecret(GETProperties.readValue("wechat.appsecret"));
//        propertis.setCompany("0");
        return this.managerWxBandDao.getWeChatPropertis("0");
    }
}
