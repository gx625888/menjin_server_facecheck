package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.GETProperties;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.domain.*;
import com.threey.guard.manage.service.ManagerDataServerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dataServer")
public class ManagerDataServerController {

    private static String CALL_PATH = GETProperties.readValue("CALL_PATH");
    @Autowired
    ManagerDataServerService managerDataServerService;

    @RequestMapping("/toOpenRecordList.shtml")
    public String toOpenRecordList(HttpServletRequest request){
        return "manage/dataServer/openRecordIndex";
    }

    @RequestMapping(value = "/selectOpenRecord.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<OpenRecord> selectOpenRecord(DataTable.Req p , HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
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
        map.put("community",request.getParameter("p_community"));
        map.put("residentail",request.getParameter("p_residentail"));
        map.put("build",request.getParameter("p_build"));
        map.put("unit",request.getParameter("p_unit"));
        map.put("doorName",request.getParameter("p_doorName"));
        map.put("personName",request.getParameter("p_personName"));
        map.put("personPhone",request.getParameter("p_personPhone"));
        map.put("cardNo",request.getParameter("p_cardNo"));
        map.put("openType",request.getParameter("p_openType"));
        String sDate = request.getParameter("p_sDate");
        String eDate = request.getParameter("p_eDate");
        if(!StringUtil.isEmpty(sDate)){
            sDate = sDate+" 00:00:00";
        }
        if(!StringUtil.isEmpty(eDate)){
            eDate = eDate+" 23:59:59";
        }
        map.put("sDate",sDate);
        map.put("eDate",eDate);
        List<OpenRecord> openRecords = managerDataServerService.getOpenRecordList(map,p.getPage()-1,p.getLimit());
        for(OpenRecord o:openRecords){
            o.setPicture(CALL_PATH+o.getPicture());
        }
        int count = this.managerDataServerService.countOpenRecord(map);
        return new DataTable.Resp<OpenRecord>(openRecords,count,0);
    }

    @RequestMapping("/toWarnRecordList.shtml")
    public String toWarnRecordList(HttpServletRequest request){
        return "manage/dataServer/warnRecordIndex";
    }

    @RequestMapping(value = "/selectWarnRecord.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<WarnRecord> selectWarnRecord(DataTable.Req p , HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
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
        map.put("community",request.getParameter("p_community"));
        map.put("residentail",request.getParameter("p_residentail"));
        map.put("build",request.getParameter("p_build"));
        map.put("unit",request.getParameter("p_unit"));
        map.put("warnType",request.getParameter("p_warnType"));
        List<WarnRecord> warnRecords = managerDataServerService.getWarnRecordList(map,p.getPage()-1,p.getLimit());
        int count = this.managerDataServerService.countWarnRecord(map);
        return new DataTable.Resp<WarnRecord>(warnRecords,count,0);
    }

    @RequestMapping("/toPlanList.shtml")
    public String toPlanList(HttpServletRequest request){
        return "manage/dataServer/planIndex";
    }

    @RequestMapping(value = "/page.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Plan> page(DataTable.Req p , HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map = new HashMap<>();
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
        map.put("planType",request.getParameter("p_planType"));
        map.put("remindType",request.getParameter("p_remindType"));
        map.put("remindCycle",request.getParameter("remind_cycle"));
        map.put("remindRange",request.getParameter("remind_range"));
        map.put("frequency",request.getParameter("frequency"));
        List<Plan> plans = managerDataServerService.list(map,p.getPage()-1,p.getLimit());
        int count = this.managerDataServerService.count(map);
        return new DataTable.Resp<Plan>(plans,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Plan plan = this.managerDataServerService.getOne(id);
            request.setAttribute("plan",plan);
            request.setAttribute("opt","edit");
        }
        return "manage/dataServer/planAdd";
    }

    @RequestMapping(value = "/save.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveOrUpdate(Plan plan,HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        if (StringUtils.isNotEmpty(plan.getId())){
            this.managerDataServerService.update(plan);

        }else{
            plan.setCreateUser(loginUser.getMid());
            this.managerDataServerService.add(plan);
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
        this.managerDataServerService.del(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }
    
    @RequestMapping(value = "/queryCommunity.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<Community> queryCommunity(HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map = new HashMap<>();
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
        return this.managerDataServerService.queryCommunity(map);
    }

    @RequestMapping(value = "/queryResidentail.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<Residentail> queryResidentail(HttpServletRequest request){
        String id = request.getParameter("id");
        return this.managerDataServerService.queryResidentail(id);
    }

    @RequestMapping(value = "/queryBuild.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<BuildUnit> queryBuild(HttpServletRequest request){
        String id = request.getParameter("id");
        return this.managerDataServerService.queryBuild(id);
    }

    @RequestMapping(value = "/queryUnit.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<BuildUnit> queryUnit(HttpServletRequest request){
        String id = request.getParameter("id");
        return this.managerDataServerService.queryUnit(id);
    }

    @RequestMapping(value = "/queryHouse.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<House> queryHouse(HttpServletRequest request){
        String id = request.getParameter("id");
        return this.managerDataServerService.queryHouse(id);
    }
}
