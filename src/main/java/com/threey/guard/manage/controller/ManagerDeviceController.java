package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.XlsUtil;
import com.threey.guard.clientmsg.MsgRouteFact;
import com.threey.guard.manage.domain.BuildUnit;
import com.threey.guard.manage.domain.Community;
import com.threey.guard.manage.domain.Device;
import com.threey.guard.manage.domain.Residentail;
import com.threey.guard.manage.service.ManagerDeviceService;
import com.threey.guard.manage.service.ManagerResidentailService;
import com.threey.guard.manage.service.ResidentailDetailService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
@RequestMapping("/device")
public class ManagerDeviceController {

    private Logger logger = Logger.getLogger(ManagerDeviceController.class);
    @Autowired
    private ManagerDeviceService managerDeviceService;
    @Autowired
    private ManagerResidentailService managerResidentailService;

    @Autowired
    private ResidentailDetailService residentailDetailService;

    @RequestMapping("/toDeviceList.shtml")
    public String toManagerUserList(HttpServletRequest request){
        return "manage/device/deviceIndex";
    }

    @RequestMapping(value = "/page.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Device> page(DataTable.Req p , HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("deviceId",request.getParameter("p_deviceId"));
        map.put("deviceType",request.getParameter("p_deviceType"));
        map.put("deviceStatus",request.getParameter("p_deviceStatus"));
        map.put("residentailName",request.getParameter("p_residentail"));

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
        List<Device> devices = managerDeviceService.list(map,p.getPage()-1,p.getLimit());
        if (CollectionUtils.isNotEmpty(devices)){//状态查询
            for (Device d:devices) {
                d.setOnline(MsgRouteFact.instance.getInstance().isClientOnline(d.getDeviceId()));
            }
        }
        int count = this.managerDeviceService.count(map);
        return new DataTable.Resp<Device>(devices,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Device device = this.managerDeviceService.getOne(id);
            request.setAttribute("device",device);
            request.setAttribute("opt","edit");
        } else {
            request.setAttribute("opt","add");
        }
        return "manage/device/deviceAdd";
    }
    @RequestMapping("/toDetail.shtml")
    public String toDetail(HttpServletRequest request){
        String id = request.getParameter("id");
        Device device = this.managerDeviceService.getOne(id);
        request.setAttribute("isOnline",MsgRouteFact.instance.getInstance().isClientOnline(device.getDeviceId()));
        request.setAttribute("device",device);
        return "manage/device/deviceDetail";
    }

    @RequestMapping("/tobinduint.shtml")
    public String toBindUint(HttpServletRequest request){
        String id = request.getParameter("id");
        Community community = new Community();
        List<Community> communities = this.managerResidentailService.getCommunity(community);
        request.setAttribute("communities",communities);
        request.setAttribute("id",id);
        return "manage/device/bindUnit";
    }

    @RequestMapping(value = "/save.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveOrUpdate(Device device,HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        if (StringUtils.isNotEmpty(device.getId())){
            device.setDeviceId(device.getDeviceId().trim());
            this.managerDeviceService.update(device);
        }else{
            device.setCreateUser(loginUser.getMid());
            Map<String,Object> map = new HashMap<String,Object>();
            device.setDeviceId(device.getDeviceId().trim());
            map.put("deviceId",device.getDeviceId());
            int count = this.managerDeviceService.count(map);
            if (count>0){
                map.put("ret",-1);
                map.put("msg","设备号已存在!");
                return map;
            }
            this.managerDeviceService.add(device);
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
        this.managerDeviceService.del(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/dic.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<Map<String,String>> dic(HttpServletRequest request){
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        List<Map<String,String>> list = new ArrayList<>();
        if ("residentail".equals(type)){
            Residentail r = new Residentail();
            r.setCommunity(id);
            List<Residentail> residentails = this.managerResidentailService.list(r,0,Integer.MAX_VALUE);
            if (CollectionUtils.isNotEmpty(residentails)){
                for (Residentail rd:residentails ) {
                    Map<String,String> map = new HashMap<>();
                    map.put("id",rd.getId());
                    map.put("name",rd.getName());
                    list.add(map);
                }
            }
            return list;
        }
        List<BuildUnit> buildUnits = null;
        if ("build".equals(type)){
            buildUnits = this.residentailDetailService.getAllBuildByResidentailId(id);
        }
        if ("unit".equals(type)){
          buildUnits = this.residentailDetailService.queyUnbindUnit(id);
        }
        if (CollectionUtils.isNotEmpty(buildUnits)){
            for (BuildUnit b:buildUnits ) {
                Map<String,String> map = new HashMap<>();
                map.put("id",b.getId());
                map.put("name",b.getName());
                list.add(map);
            }
        }
        return list;
    }


    @RequestMapping(value = "/uploadFile.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> uploadFile(HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        long startTime=System.currentTimeMillis();   //获取开始时间
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        MultipartFile resFile = multiRequest.getFile("file");
        String[] arr = {"deviceId","deviceType","deviceStatus","deviceTel","devicePhone"};
        List<Map<String,Object>> list = XlsUtil.analysisXls(arr,resFile);
        for (Map<String,Object> map:list){
            map.put("createUser",loginUser.getMid());
            if("类型I".equals(map.get("deviceType"))){
                map.put("deviceType",1);
            }else{
                map.put("deviceType",2);
            }
            if("可用".equals(map.get("deviceStatus"))){
                map.put("deviceStatus",0);
            } else if ("停用".equals(map.get("deviceStatus"))){
                map.put("deviceStatus",1);
            } else if ("报修".equals(map.get("deviceStatus"))){
                map.put("deviceStatus",2);
            } else if ("废弃".equals(map.get("deviceStatus"))){
                map.put("deviceStatus",3);
            } else {
                map.put("deviceStatus",1);
            }
        }
        this.managerDeviceService.batchInsert(list);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功导入"+list.size()+"条数据！");
        return map;
    }


    @RequestMapping(value = "/deviceopt.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> deviceopt(HttpServletRequest request){
        String deviceIdArr = request.getParameter("deviceIds");
        String flag = request.getParameter("flag");
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        return this.managerDeviceService.deviceOpt(loginUser.getMid(),flag,deviceIdArr.split(","));
    }

    @RequestMapping("export.shtml")
    @ResponseBody
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception {
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("deviceId",request.getParameter("p_deviceId"));
        map.put("deviceType",request.getParameter("p_deviceType"));
        map.put("deviceStatus",request.getParameter("p_deviceStatus"));
        map.put("residentailName",request.getParameter("p_residentail"));

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
        List<Device> devices = managerDeviceService.getDeviceList(map);
        if (CollectionUtils.isNotEmpty(devices)){//状态查询
            for (Device d:devices) {
                d.setOnline(MsgRouteFact.instance.getInstance().isClientOnline(d.getDeviceId()));
            }
        }
        this.managerDeviceService.export(request, devices,response);
    }
}
