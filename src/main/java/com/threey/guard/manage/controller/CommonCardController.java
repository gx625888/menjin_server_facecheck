package com.threey.guard.manage.controller;


import com.threey.guard.base.domain.*;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.Pages;
import com.threey.guard.manage.domain.CommonCard;
import com.threey.guard.manage.domain.Device;
import com.threey.guard.manage.service.CommonCardService;
import com.threey.guard.manage.service.ManagerDeviceService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通卡管理
 */
@Controller
@RequestMapping("/manageCommonCard")
public class CommonCardController {

    @Autowired
    private CommonCardService service;

    @Autowired
    private ManagerDeviceService managerDeviceService;
    @RequestMapping("/index.shtml")
    public String toManagerUserList(HttpServletRequest request){
        request.setAttribute("residentail",request.getParameter("residentail"));
        return "manage/commonCard/index";
    }
    @RequestMapping("/page.shtml")
    @ResponseBody
    public DataTable.Resp<CommonCard> page(DataTable.Req p , HttpServletRequest request){
        CommonCard role = new CommonCard();
        role.setResidentail(request.getParameter("residentail"));
        List<CommonCard>  roles = this.service.list(role,p.getPage()-1,p.getLimit());
        int count = this.service.count(role);
        return new DataTable.Resp<CommonCard>(roles,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        request.setAttribute("residentail",request.getParameter("residentail"));
        String id = request.getParameter("id");
        if (id!=null){
            CommonCard role = this.service.getOne(id);
            request.setAttribute("role",role);
        }
        return "manage/commonCard/add";
    }

    @RequestMapping("/save.shtml")
    @ResponseBody
    public Map<String,Object> saveOrUpdate(CommonCard role){
        if (role.getId()!=0){
            this.service.update(role);
        }else{
            this.service.add(role);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/del.shtml")
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request){
        String id = request.getParameter("id");
        this.service.del(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/sync.shtml")
    @ResponseBody
    public Map<String,Object> sync(HttpServletRequest request){
        String residentail = request.getParameter("residentail");


        List<Device> list = this.managerDeviceService.getDeviceByResidentail(residentail);
        if (CollectionUtils.isNotEmpty(list)){
            String[] deviceIds = new String[list.size()];
            for (int i=0;i<deviceIds.length;i++){
                deviceIds[i] = list.get(i).getDeviceId();
            }
            ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
            this.managerDeviceService.deviceOpt(loginUser.getMid(),"2",deviceIds);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }




}
