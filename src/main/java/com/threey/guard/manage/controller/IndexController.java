package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.service.IndexChartService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexChartService service;

    @RequestMapping("/chart/openrecord.shtml")
    @ResponseBody
    public Map<String,Object> openRecord(HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map  = new HashMap<>();
        int dayNum = Integer.parseInt(request.getParameter("days"));
        map.put("days",dayNum);
        List<String>  days = StringUtil.getRecentDays(dayNum);
        if(loginUser.getManagerType() != Constants.UserType.ROOT_MANAGER){//超级管理员
            Map<String,String> param = getParamMap(loginUser);
            StringBuilder sb = new StringBuilder();
            for (String day : days){
                sb.append("'").append(day).append("',");
            }
            param.put("days",sb.substring(0,sb.length()-1));
            map.put("data",this.service.getRecentOpenData(param));
            map.put("dayList",days);
            return map;
        }
        map.put("data",this.service.getRecentOpenData(days));
        map.put("dayList",days);
        return map;
    }

    @RequestMapping("/chart/warn.shtml")
    @ResponseBody
    public Map<String,Object> warn(HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        int dayNum = Integer.parseInt(request.getParameter("days"));
        List<String>  days = StringUtil.getRecentDays(dayNum);
        Map<String,Object> map  = new HashMap<>();
        map.put("days",dayNum);
        if(loginUser.getManagerType() != Constants.UserType.ROOT_MANAGER){//超级管理员
            Map<String,String> param = getParamMap(loginUser);
            StringBuilder sb = new StringBuilder();
            for (String day : days){
                sb.append("'").append(day).append("',");
            }
            param.put("days",sb.substring(0,sb.length()-1));
            map.put("data",this.service.getRecentWarnData(param));
            map.put("dayList",days);
            return map;
        }
        map.put("data",this.service.getRecentWarnData(days));
        map.put("dayList",days);
        return map;
    }

    @RequestMapping("/chart/openType.shtml")
    @ResponseBody
    public List<Map> openType(HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        if(loginUser.getManagerType() != Constants.UserType.ROOT_MANAGER){//超级管理员
            Map<String,String> param = getParamMap(loginUser);
            return this.service.openType(param);
        }
        return this.service.openType();
    }

    @RequestMapping("/chart/deviceStatus.shtml")
    @ResponseBody
    public List<Map> deviceStatus(HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        if(loginUser.getManagerType() != Constants.UserType.ROOT_MANAGER){//超级管理员
            Map<String,String> param = getParamMap(loginUser);
            return this.service.deviceStatus(param);
        }
        return this.service.deviceStatus();
    }


    private Map<String,String> getParamMap(ManagerUser loginUser){
        Map<String,String> param  = new HashMap<>();
        if(loginUser.getManagerType() == Constants.UserType.COM_MASTER){
            param.put("company",loginUser.getManagerCompany());
        }
        if(loginUser.getManagerType() == Constants.UserType.CITY_MANAGER){
            param.put("city",loginUser.getManagerCity());
        }
        if(loginUser.getManagerType() == Constants.UserType.RES_MANAGER){
            param.put("res",loginUser.getManagerResidentail());
        }
        return param;
    }


}
