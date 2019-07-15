package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.XlsUtil;
import com.threey.guard.manage.domain.Card;
import com.threey.guard.manage.domain.Person;
import com.threey.guard.manage.domain.QueryResidentailByPerson;
import com.threey.guard.manage.service.ManagerCardService;
import com.threey.guard.manage.service.ManagerPersonService;
import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/person")
public class ManagerPersonController {
    private Logger logger = LoggerFactory.getLogger(ManagerPersonController.class);
    @Autowired
    private ManagerPersonService managerPersonService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ManagerCardService managerCardService;

    @RequestMapping("/toPersonList.shtml")
    public String toManagerUserList(HttpServletRequest request){
        if (null!=request.getParameter("houseId")){
            request.setAttribute("houseId",request.getParameter("houseId"));
            return "manage/person/personIndexHouse";
        }

        return "manage/person/personIndex";
    }

    @RequestMapping(value = "/page.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Person> page(DataTable.Req p , HttpServletRequest request){
        String houseId = request.getParameter("houseId");
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        if (null==houseId){
            Map<String,Object> map = new HashMap<String,Object>();
            if(loginUser.getManagerType() != 0){
                map.put("createUserCompany",loginUser.getManagerCompany());
                if(loginUser.getManagerType() == 2){
                    map.put("createUserProvince",loginUser.getManagerProvince());
                }
                if(loginUser.getManagerType() == 3){
                    map.put("createUserCity",loginUser.getManagerCity());
                }
                if(loginUser.getManagerType() == 4){
                    map.put("createUserResidentail",loginUser.getManagerResidentail());
                }
            }
            map.put("name",request.getParameter("p_name"));
            map.put("phone",request.getParameter("p_phone"));
            map.put("cardNo",request.getParameter("p_cardNo"));
            map.put("bandStatus",request.getParameter("p_bandStatus"));
            map.put("house",request.getParameter("house"));
            List<Person> persons = managerPersonService.list(map,p.getPage()-1,p.getLimit());
            int count = this.managerPersonService.count(map);
            return new DataTable.Resp<Person>(persons,count,0);
        }else {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("houseId",houseId);
            //List<Person> persons = managerPersonService.list(map,p.getPage()-1,Integer.MAX_VALUE);
            List<Person> housePersons = managerPersonService.listHousePersons(map, p.getPage()-1, Integer.MAX_VALUE);
            if (housePersons == null) {
                housePersons = new ArrayList<>();
            }
            //return new DataTable.Resp<Person>(persons,persons.size(),0);
            return new DataTable.Resp<>(housePersons, housePersons.size(), 0);
        }

    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Person person = this.managerPersonService.getOne(id);
            request.setAttribute("person",person);
            request.setAttribute("opt","edit");
        }
        if (null!=request.getParameter("houseId")){
            request.setAttribute("houseId",request.getParameter("houseId"));
            request.setAttribute("unitId",request.getParameter("pid"));
        }

        String bind = request.getParameter("bind");
        logger.info(">>>bind={}<<<", bind);
        if (StringUtils.isNotEmpty(bind)) {
            request.setAttribute("bind", bind);
        }

        return "manage/person/personAdd";
    }

    @RequestMapping(value = "/save.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveOrUpdate(Person person,HttpServletRequest request) {
        String bind = request.getParameter("bind");
        String liveType = request.getParameter("liveType");
        logger.info(">>>>/person/save.shtml:person={} bind={} liveType={}<<<<",
                new Object[]{person, bind, liveType});
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        String personId = person.getId();
        logger.info(">>>>personId={}<<<<", personId);
        if (StringUtils.isNotEmpty(personId)) {
            this.managerPersonService.update(person);
        } else {
            long id = this.commonService.getNextVal("mj_person");
            person.setId(String.valueOf(id));
            person.setCreateUser(loginUser.getMid());
            this.managerPersonService.add(person);

            String houseId = request.getParameter("houseId");
            personId = person.getId();
            logger.info(">>>houseId={}<<<<", houseId);
            if (StringUtils.isNotEmpty(houseId)) {//保存居住 House关系
                Map<String,Object> params = new HashMap<>();
                params.put("houseId", houseId);
                params.put("personId", personId);
                if (StringUtils.isEmpty(liveType)) {
                    liveType = "1";
                }

                params.put("liveType", liveType);
                this.managerPersonService.saveHousePerson(params);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/del.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request){
        String id = request.getParameter("id");
        this.managerPersonService.del(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/uploadFile.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> uploadFile(HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        long startTime=System.currentTimeMillis();   //获取开始时间
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        MultipartFile resFile = multiRequest.getFile("file");
        String[] arr = {"name","phone","sex","cardType","cardNo","birthday","country","nation","type","address"};
        List<Map<String,Object>> list = XlsUtil.analysisXls(arr,resFile);
        for (Map<String,Object> map:list){
            map.put("createUser",loginUser.getMid());
            map.put("id",this.commonService.getNextVal("mj_person")+"");
            if("女".equals(map.get("sex"))){
                map.put("sex",1);
            }else{
                map.put("sex",2);
            }
            if("身份证".equals(map.get("cardType"))){
                map.put("cardType",1);
            } else if ("护照".equals(map.get("cardType"))){
                map.put("cardType",2);
            } else if ("军人证".equals(map.get("cardType"))){
                map.put("cardType",3);
            } else {
                map.put("cardType",4);
            }
            if("非关注人群".contains((String)map.get("type"))){
                map.put("type",1);
            } else if ("独居老人".contains((String)map.get("type"))){
                map.put("type",2);
            } else if ("残障人士".contains((String)map.get("type"))){
                map.put("type",3);
            } else if ("涉案人员".contains((String)map.get("type"))){
                map.put("type",4);
            } else if ("涉毒人员".contains((String)map.get("type"))){
                map.put("type",5);
            } else if ("涉黑人员".contains((String)map.get("type"))){
                map.put("type",6);
            } else {
                map.put("type",1);
            }
        }
        this.managerPersonService.batchInsert(list);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功导入"+list.size()+"条数据！");
        return map;
    }

    @RequestMapping(value = "/ResidentailPage.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<QueryResidentailByPerson> ResidentailPage(DataTable.Req p , HttpServletRequest request){
        String id = request.getParameter("id");
        List<QueryResidentailByPerson> list = managerPersonService.queryResidentailByPerson(id,p.getPage()-1,p.getLimit());
        int count = this.managerPersonService.countResidentailByPerson(id);
        return new DataTable.Resp<QueryResidentailByPerson>(list,count,0);
    }

    @RequestMapping(value = "/CardPage.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Card> CardPage(DataTable.Req p , HttpServletRequest request){
        String id = request.getParameter("id");
        List<Card> list = managerPersonService.queryCardByPerson(id,p.getPage()-1,p.getLimit());
        int count = this.managerPersonService.countCardByPerson(id);
        return new DataTable.Resp<Card>(list,count,0);
    }

    @RequestMapping("/toPersonDetail.shtml")
    public String toPersonDetail(HttpServletRequest request){
        String id = request.getParameter("id");
        Person person = this.managerPersonService.getOne(id);
        request.setAttribute("person",person);
        return "manage/person/personDetailIndex";
    }

    @RequestMapping(value = "/cancelBand.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> cancelBand(HttpServletRequest request){
        Map<String,Object> pMap = new HashMap<>();
        pMap.put("person",request.getParameter("person"));
        pMap.put("house",request.getParameter("house"));
        this.managerPersonService.deleteBandInfo(pMap);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/cardLoseStatus.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> cardLoseStatus(HttpServletRequest request){
        this.managerCardService.toLoseCard(request.getParameter("id"));
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/saveHousePerson.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveHousePerson(HttpServletRequest request){
        Map<String,Object> pMap = new HashMap<>();
        pMap.put("houseId",request.getParameter("house"));
        pMap.put("personId",request.getParameter("personId"));
        pMap.put("liveType","");
        this.managerPersonService.saveHousePerson(pMap);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }
}
