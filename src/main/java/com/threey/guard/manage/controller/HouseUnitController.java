package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.Role;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.domain.Device;
import com.threey.guard.manage.domain.HouseUnit;
import com.threey.guard.manage.service.ManageHouseService;
import com.threey.guard.manage.service.ManagerDeviceService;
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

/**
 * 单元住户
 */
@Controller
@RequestMapping("/mangeHouse")
public class HouseUnitController {

    @Autowired
    private ManageHouseService service;

    @Autowired
    private ManagerDeviceService managerDeviceService;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/page.shtml")
    @ResponseBody
    public DataTable.Resp<HouseUnit> page(DataTable.Req p , HttpServletRequest request){
        HouseUnit houseUnit = new HouseUnit();
        houseUnit.setUnitId(Long.parseLong(request.getParameter("unitId")));
        List<HouseUnit> roles = this.service.list(houseUnit,p.getPage()-1,Integer.MAX_VALUE);
        //int count = this.service.count(role);
        return new DataTable.Resp<HouseUnit>(roles,roles.size(),0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        request.setAttribute("unitId",request.getParameter("pid"));
        if (id!=null){
            HouseUnit role = this.service.getOne(id);
            request.setAttribute("role",role);
        }
        return "manage/residentailDetail/addOrEditHouse";
    }

    @RequestMapping("/save.shtml")
    @ResponseBody
    public Map<String,Object> saveOrUpdate(HouseUnit role){
        if (role.getId()!=0){
           // role.setCreateTime(StringUtil.getTimetmp2());
            this.service.update(role);

        }else{
            //role.setId(StringUtil.getUuid());
            //role.setCreateTime(StringUtil.getTimetmp2());
            //role.setDelFlag("0");
            role.setId(this.commonService.getNextVal("build_unit"));
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


    @RequestMapping("/bindDevice.shtml")
    public String bindDevice(HttpServletRequest request){
        request.setAttribute("unitId",request.getParameter("unitId"));
        return "manage/residentailDetail/deviceBind";
    }
    @RequestMapping("/unBindDevice.shtml")
    @ResponseBody
    public Map<String,Object>  unBindDevice(HttpServletRequest request){
        managerDeviceService.unBind(request.getParameter("deviceId"),request.getParameter("unitId"));
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/doBindDevice.shtml")
    @ResponseBody
    public Map<String,Object>  doBindDevice(HttpServletRequest request){
        managerDeviceService.bind(request.getParameter("deviceId"),request.getParameter("unitId"));
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }


    @RequestMapping(value = "/pagedevice.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Device> pagedevice(DataTable.Req p , HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("deviceId",request.getParameter("p_deviceId"));
        map.put("deviceType",request.getParameter("p_deviceType"));
        map.put("deviceStatus",request.getParameter("p_deviceStatus"));
        map.put("residentailName",request.getParameter("p_residentail"));
        List<Device> devices = managerDeviceService.listUnBind(map,p.getPage()-1,p.getLimit());
        int count = this.managerDeviceService.countUnbind(map);
        return new DataTable.Resp<Device>(devices,count,0);
    }
}
