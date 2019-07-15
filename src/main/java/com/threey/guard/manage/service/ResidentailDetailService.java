package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.base.service.LoginService;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.dao.SendMsgDao;
import com.threey.guard.clientmsg.domain.MsgBean;
import com.threey.guard.manage.dao.ResidentailDetailDao;
import com.threey.guard.manage.domain.BuildUnit;
import com.threey.guard.manage.domain.House;
import com.threey.guard.manage.domain.HouseUnit;
import com.threey.guard.manage.domain.Person;
import com.threey.guard.quartz.util.MsgBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 楼栋单元service
 */
@Service
public class ResidentailDetailService extends CrudService<BuildUnit> {
    private Logger logger = Logger.getLogger(ResidentailDetailService.class);

    @Autowired
    private ResidentailDetailDao dao;

    @Autowired
    private SendMsgDao sendMsgDao;

    @Autowired
    private CommonService commonService;

    /**
     * 通过小区id查询楼栋和单元
     * @param residentailId
     * @return
     */
    public List<BuildUnit> getAllBuildUnitByResidentailId(String residentailId){
        return this.dao.getAllBuildUnitByResidentailId(residentailId);
    }

    public List<BuildUnit> getAllBuildByResidentailId(String residentailId){
        return this.dao.getAllBuildByResidentailId(residentailId);
    }

    /**
     * 查询未绑定过的单元
     * @param id
     * @return
     */
    public List<BuildUnit> queyUnbindUnit(String id){
        return this.dao.queyUnbindUnit(id);
    }
    /**
     * 查询下级单位
     * @param id
     * @return
     */
    public List<BuildUnit> queyNextLevalUnit(String id){
        return this.dao.queyNextLevalUnit(id);
    }


    public int openDoor(String deviceId,String userId,String flag){

        MsgBean msgBean = new MsgBean();
        msgBean.setMsgType(MsgBean.TYPE_OPEN);
        if ("1".equals(flag)){//更新资源
            msgBean.setMsgType(MsgBean.TYPE_ADS);
        }else if("2".equals(flag)){//更新用户信息
            msgBean.setMsgType(MsgBean.TYPE_USER);
        }else if("3".equals(flag)){//更新客户端
            msgBean.setMsgType(MsgBean.TYPE_APP);
        }else if ("5".equals(flag)){//重启
            msgBean.setMsgType(MsgBean.TYPE_REBOOT);
        }

        msgBean.setStatus(MsgBean.STATUS_SUCCESS);
        msgBean.setDeviceId(deviceId);
        msgBean.setOptUser("mn:"+userId);
        int sendStatus = MsgUtil.send(MsgBeanUtil.parseMsg(msgBean));
        this.sendMsgDao.insert(msgBean);

        return sendStatus;
    }

    public boolean getHouseCount(String id){
        return this.dao.getHouseCount(id);
    }

    public boolean getBuildUnitCount(String id){
        return this.dao.getBuildUnitCount(id);
    }

    public boolean getDeviceCount(String id){
        return this.dao.getDeviceCount(id);
    }


    public int batchImport(List<Map<String,Object>> list ,String unitId,String createUser){
        if (CollectionUtils.isEmpty(list)){
            return 0;
        }


        Map<String,String> levels = new HashMap<>();
        Map<String,List<Person>> pesons = new HashMap<>();
        List<House> levelHouses = new ArrayList<>();
        Map<String,String> houses = new HashMap<>();


        int count =0;
        for (Map<String,Object> map : list){

            Person person = changeToPerson(map,createUser);
            if (person==null){
                continue;
            }
            String level = (String) map.get("level");
            if (StringUtil.isEmpty(level)){
                continue;
            }
            String house = (String) map.get("house");
            if (StringUtil.isEmpty(house)){
                continue;
            }
            if (!levels.containsKey(level)){
                levels.put(level,String.valueOf(this.commonService.getNextVal("build_unit")));
            }

            if (!houses.containsKey(house)){
                String houseId = String.valueOf(this.commonService.getNextVal("build_unit"));
                houses.put(house,houseId);
                House houseUnit = new House();
                houseUnit.setId(houseId);
                houseUnit.setName(house);
                houseUnit.setUnitId(levels.get(level));
                houseUnit.setStatus(openType((String) map.get("openType")));
                // 开门电话
                houseUnit.setPhone((String)map.get("phone"));
                levelHouses.add(houseUnit);
            }


            if (!pesons.containsKey(houses.get(house))){
                pesons.put(houses.get(house),new ArrayList<Person>());
            }
           List<Person> pesonsList = pesons.get(houses.get(house));
           pesonsList.add(person);

            count++;
        }

        addBatch(unitId,levels,pesons, levelHouses);


        return count;
    }

    private void addBatch( String unitId,Map<String,String> levels, Map<String,List<Person>> pesons ,  List<House> levelHouses){
        List<BuildUnit> buildUnits = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
        List<Map<String,String>> housePersons = new ArrayList<>();

        for (Map.Entry<String,String> entry : levels.entrySet()) {
            BuildUnit b = new BuildUnit();
            b.setId(entry.getValue());
            b.setName(entry.getKey());
            b.setParentId(unitId);
            b.setType(3);//楼层
            buildUnits.add(b);
        }


        for (Map.Entry<String,List<Person>> entry : pesons.entrySet()) {
            List<Person> hps = entry.getValue();
            if (CollectionUtils.isNotEmpty(hps)){
                for (Person p: hps) {
                    Map<String,String> hpsr = new HashMap<>();
                    hpsr.put("houseId",entry.getKey());
                    hpsr.put("personId",p.getId());
                    String liveType = p.getLiveType();
                    if (StringUtils.isEmpty(liveType)) {
                        liveType = "1";
                    }

                    hpsr.put("liveType", liveType);
                    housePersons.add(hpsr);
                }

                personList.addAll(hps);
            }
        }

        this.dao.insertBatchLevels(buildUnits);
        this.dao.insertBatchHouses(levelHouses);
        this.dao.insertBatchPersons(personList);
        this.dao.insertBatchHousePerson(housePersons);


    }

    private Person changeToPerson(Map<String,Object> map,String createUser){
        if (null==map.get("name")||"".equals(map.get("name"))){
           return null;
        }
        Person p = new Person();
        String[] arr = {"name","sex","phone","idType","idNo","country","people","address","house","level","openType","liveType","viewType"};
        try{
            p.setId(String.valueOf(this.commonService.getNextVal("mj_person")));
            p.setName((String) map.get("name"));
            p.setSex(getIdSex((String) map.get("sex")));
            p.setPhone((String) map.get("phone"));
            p.setCardType(getIdType((String) map.get("idType")));
            p.setCardNo((String) map.get("idNo"));
            p.setCountry((String) map.get("country"));
            p.setNation((String) map.get("people"));
            p.setAddress((String) map.get("address"));
            p.setType(getType((String) map.get("viewType")));
            p.setLiveType((String) map.get("liveType"));
            p.setCreateUser(createUser);
        }catch (Exception e){
            logger.info("解析导入数据异常!!!!");
            e.printStackTrace();
            return null;
        }
        return p;
    }

    private int getIdType(String type){
        if ("身份证".equals(type)){
            return 1;
        }
        if ("护照".equals(type)){
            return 2;
        }
        if ("军官证".equals(type)){
            return 3;
        }
        return 4;
    }
    private int getIdSex(String sex){
        return "男".equals(sex)?2:1;
    }
    private int getType(String viewType){
        try{
            return Integer.parseInt(viewType);
        }catch (Exception e){
            return 1;
        }

    }

    private int openType(String openType){
        try{
            return Integer.parseInt(openType);
        }catch (Exception e){
            return 3;
        }
    }
    @Override
    protected CrudDAO getDao() {
        return this.dao;
    }
}
