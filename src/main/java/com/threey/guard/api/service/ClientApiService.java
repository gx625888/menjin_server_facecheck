package com.threey.guard.api.service;

import com.threey.guard.api.dao.ClientApiDao;
import com.threey.guard.api.domain.*;
import com.threey.guard.base.dao.UserDAO;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.domain.User;
import com.threey.guard.base.util.GETProperties;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.dao.ManagerPersonDao;
import com.threey.guard.manage.dao.ManagerUserDAO;
import com.threey.guard.manage.domain.CommonCard;
import com.threey.guard.manage.domain.HouseUnit;
import com.threey.guard.manage.domain.Person;
import com.threey.guard.manage.service.CommonCardService;
import com.threey.guard.manage.service.ManagerUserService;
import com.threey.guard.tel.util.TelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientApiService {

    private static String CALL_PATH = GETProperties.readValue("CALL_PATH");

    @Autowired
    private ClientApiDao clientApiDao;
    @Autowired
    private ManagerPersonDao managerPersonDao;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private CommonCardService commonCardService;

    public ApiResult versonUpdate(String deviceId){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(0);
        apiResult.setMsg("success");
        List<String> list = clientApiDao.versonUpdate(deviceId);
        if(list.size()>0){
            apiResult.setData(list.get(0));
        }else {
            apiResult.setData("");
        }
        return apiResult;
    }


    public ApiResult getAds(String deviceId){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(0);
        apiResult.setMsg("success");
        List<Ads> list = clientApiDao.getAds(deviceId);
        for (Ads a:list){
            a.setUrl(CALL_PATH+a.getUrl());
        }
        apiResult.setData(list);
        return apiResult;
    }

    public void openRecord(Map<String,Object> map){
        String openType = (String)map.get("openType");
        List<DeviceArea> dList = clientApiDao.getDeviceArea((String)map.get("deviceId"));
        if (CollectionUtils.isNotEmpty(dList)){
            map.put("residentail",dList.get(0).getResidentail());
            map.put("build",dList.get(0).getBuild());
            map.put("unit",dList.get(0).getUnit());
            map.put("unitName",dList.get(0).getUnitName());
            map.put("buildName",dList.get(0).getBuildName());
        }
        if("0".equals(openType)){ // 刷卡开门
            if(map.get("cardNo") != null){
                List<Person> list = clientApiDao.getPersonId((String)map.get("cardNo"));
                if(CollectionUtils.isNotEmpty(list)){
                    map.put("personId",list.get(0).getId());
                    map.put("personName",list.get(0).getName());
                    map.put("personPhone",list.get(0).getPhone());
                    List<HouseUnit> houses= clientApiDao.getHouseByPersonAndDevice(list.get(0).getId(),(String)map.get("deviceId"));
                    if (CollectionUtils.isNotEmpty(houses)){
                        map.put("house",houses.get(0).getId());
                        map.put("houseName",houses.get(0).getName());
                    }
                }else {
                    //查询通卡数据
                    List<CommonCard> commonCards = this.commonCardService.getCardByCardNo((String)map.get("cardNo"));
                    if (CollectionUtils.isEmpty(commonCards)){
                        map.put("personId","");
                    }else{
                        map.put("personId","");
                        map.put("personName",commonCards.get(0).getUserName());
                        map.put("personPhone",commonCards.get(0).getUserPhone());
                        map.put("houseName","通卡");
                    }

                }
            }
        } else if("3".equals(openType)){//呼叫
            String cardNo = (String)map.get("cardNo");
            map.put("openType",2);
            map.put("houseName",cardNo);
        }else if("1".equals(openType)){//临时密码开门
            String cardNo = (String)map.get("cardNo");
            map.put("openType",4);
            if(null!=cardNo&&cardNo.startsWith("pw")){
                map.put("personId",cardNo.split(":")[3]);
                Person wxPerson = this.managerPersonDao.getOne(cardNo.split(":")[3]);
                if (null!=wxPerson){
                    map.put("personName",wxPerson.getName());
                    map.put("personPhone",wxPerson.getPhone());
                }
            }
        }else { // 远程开门
            String cardNo = (String)map.get("cardNo");
            //"wxapp:" + houseNo + ":" + houseId + ":" + userId + ":" + openid;
            if(null!=cardNo){
                if(cardNo.startsWith("wx")){//微信开门
                    map.put("openType",3);
                    String person = cardNo.split(":")[3];
                    String openId = cardNo.split(":")[4];
                    map.put("personId",person);
                    Person wxPerson = this.managerPersonDao.getOne(person);
                    if (null!=wxPerson){
                        map.put("personName",wxPerson.getName());
                        map.put("personPhone",wxPerson.getPhone());
                    }
                    map.put("openId",openId);
                    List<HouseUnit> houses= clientApiDao.getHouseByPersonAndDevice(person,(String)map.get("deviceId"));
                    if (CollectionUtils.isNotEmpty(houses)){
                        map.put("house",houses.get(0).getId());
                        map.put("houseName",houses.get(0).getName());
                    }
                } else if(cardNo.startsWith("mn")){//管理员开门
                    map.put("openType",1);
                    String person = cardNo.split(":")[3];
                    map.put("personId",person);
                    ManagerUser user = this.managerUserService.getManagerUserByMidAndUserId(person,null);
                    if (null!=user){
                        map.put("personName",user.getName());
                        map.put("personPhone",user.getPhone());
                    }
                }else {//未知开门方式
                    map.put("openType",5);
                }

            }
        }
        System.out.println(map);

        clientApiDao.openRecord(map);
    }

    public void warnRecord(Map map){
        clientApiDao.warnRecord(map);
    }

    public ApiResult getAccounts(String deviceId){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(0);
        apiResult.setMsg("success");
        List<AccountData> list = clientApiDao.getAccounts(deviceId);
        //追加通卡数据


        List<AccountData> commonCardList = commonCardService.getCommonAccounts(deviceId);
        if (null==list){
          list = commonCardList;
        }else {
            if (null!=commonCardList){
                list.addAll(commonCardList);
            }
        }
        apiResult.setData(list);
//        Map<String,Object> dataMap= new HashMap<>();
//        dataMap.put("accounts",list);
//        dataMap.put("houses",clientApiDao.getHousePhoneData(deviceId));
//        apiResult.setData(dataMap);

        return apiResult;
    }


    public ApiResult getHouses(String deviceId){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(0);
        apiResult.setMsg("success");
        List<HousePhoneData> list = clientApiDao.getHousePhoneData(deviceId);
        if (CollectionUtils.isNotEmpty(list)){
            for (HousePhoneData hp : list){
                String p = hp.getP();
                p = p.replaceAll(",,",",");
                if (p.lastIndexOf(",")==p.length()-1){
                    p = p.substring(0,p.length()-1);
                }
                hp.setP(p);
            }
            apiResult.setData(list);
        }else{
            apiResult.setData(new ArrayList<>());
        }
        return apiResult;
    }

    public ApiResult getPwd(String deviceId,String pwd){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(-1);
        apiResult.setMsg("fail");
        List<String> list = clientApiDao.getPwd(deviceId);
        for(Iterator iterator = list.iterator();iterator.hasNext();){
            String s = (String)iterator.next();
            String arr[] = s.split(",");
            if(arr[0].equals(pwd)){
                Map<String,Object> rmap = new HashMap<>();
                rmap.put("deviceId",deviceId);
                rmap.put("pwd",pwd);
                clientApiDao.updatePwd(rmap);
                apiResult.setCode(0);
                apiResult.setMsg("pw:"+arr[1]);
                return apiResult;
            }
        }
        return apiResult;
    }

    public ApiResult getDeviceTel(String deviceId){
        ApiResult apiResult = new ApiResult();
        String tel = clientApiDao.getTelByDevice(deviceId);


        if(StringUtil.isEmpty(tel)){
            apiResult.setCode(-1);
            apiResult.setMsg("该设备未设置分机号！");
            return apiResult;
        }
        Map<String,Object> map = new HashMap<>();
        //map.put("zj","02566695505");//总机号，固定
        map.put("zj",GETProperties.readValue("tel.mk.url"));//总机号，固定
        map.put("fj",tel.split(",")[0]);//分机号
        map.put("pw",tel.split(",")[1]);
        apiResult.setCode(0);
        apiResult.setMsg("成功");
        apiResult.setData(map);
        return apiResult;
    }

    public ApiResult deviceCall(Map<String,Object> map ,int callNum){
        ApiResult apiResult = new ApiResult();
        List<CallInfo> list = clientApiDao.getCallInfo(map);

        if(list.size()>0){
            CallInfo c = list.get(0);
            if(StringUtil.isEmpty(c.getPhoneOne())&&StringUtil.isEmpty(c.getPhoneTwo())){
                apiResult.setCode(-1);
                apiResult.setMsg("被叫号码未设定，请联系物业！");
                return apiResult;
            }
            if(StringUtil.isEmpty(c.getDeviceTel())){
                apiResult.setCode(-1);
                apiResult.setMsg("设备分机号未设定，请联系物业！");
                return apiResult;
            }
            if(StringUtil.isEmpty(c.getDevicePhone())){
                apiResult.setCode(-1);
                apiResult.setMsg("设备手机号未设定，请联系物业！");
                return apiResult;
            }
            String to = "";
            if(!StringUtil.isEmpty(c.getPhoneOne())){
                to = c.getPhoneOne();
            }else {
                to = c.getPhoneTwo();
            }
            if (StringUtils.isNotEmpty(c.getPhoneOne())&&StringUtils.isNotEmpty(c.getPhoneTwo())&&callNum>1){
                to = c.getPhoneTwo();
            }
            if (callNum>1 && to.equals(c.getPhoneOne())){//不对第一个号码复复呼叫
                return apiResult;
            }
            try {
                Map<String,String> reMap = TelUtil.call(c.getDevicePhone(),to);
                String ret = reMap.get("ret");
                if("0".equals(ret)){
                    apiResult.setCode(0);
                    apiResult.setMsg("语音呼叫成功");
                }else {
                    apiResult.setCode(-1);
                    apiResult.setMsg("语音呼叫失败！");
                }
                Map<String,Object> pMap = new HashMap<>();
                pMap.put("deviceId",map.get("device"));
                pMap.put("deivceTel",c.getDeviceTel());
                pMap.put("phone",to);
                pMap.put("house",map.get("house"));
                pMap.put("callCode",ret);
                pMap.put("callId",reMap.get("callId"));
                pMap.put("devicePhone",c.getDevicePhone());
                pMap.put("callNum",callNum);
                pMap.put("openType",c.getOpenType());
                clientApiDao.insertCallRecord(pMap);
            } catch (Exception e) {
                e.printStackTrace();
                apiResult.setCode(-1);
                apiResult.setMsg("语音呼叫异常！");
            }
        }else {
            apiResult.setCode(-1);
            apiResult.setMsg("没有该住户信息，请联系物业！");
        }
        return apiResult;
    }

    public void callRecord(Map<String,Object> map){

        clientApiDao.callRecord(map);
    }
}
