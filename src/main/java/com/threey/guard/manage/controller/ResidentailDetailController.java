package com.threey.guard.manage.controller;


import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.service.LogAuditService;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.JsonUtil;
import com.threey.guard.base.util.XlsUtil;
import com.threey.guard.clientmsg.MsgRouteFact;
import com.threey.guard.manage.domain.BuildUnit;
import com.threey.guard.manage.domain.Device;
import com.threey.guard.manage.service.ManagerDeviceService;
import com.threey.guard.manage.service.ResidentailDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *小区详细信息
 */
@Controller
@RequestMapping("/residentailDetail")
public class ResidentailDetailController {



    @Autowired
    private LogAuditService logAuditService;
    @Autowired
    private ResidentailDetailService service;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ManagerDeviceService managerDeviceService;

    @RequestMapping("/index.shtml")
    public String index(HttpServletRequest request){
        List<BuildUnit> buildUnits = this.service.getAllBuildUnitByResidentailId(request.getParameter("id"));
        request.setAttribute("buildUnits",JsonUtil.getJsonString(buildUnits));
        request.setAttribute("residentailId",request.getParameter("id"));
        return "manage/residentailDetail/index";
    }

    @RequestMapping("/del.shtml")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        if("unit".equals(type)){
            if(this.service.getBuildUnitCount(id)||this.service.getDeviceCount(id)){
                map.put("ret",-1);
                map.put("msg","存在楼层信息或已绑定设备，不可删除！");
                return map;
            }
        } else if ("build".equals(type)){
            if(this.service.getBuildUnitCount(id)){
                map.put("ret",-1);
                map.put("msg","存在单元信息，不可删除！");
                return map;
            }
        } else {
            if(this.service.getHouseCount(id)){
                map.put("ret",-1);
                map.put("msg","存在住户信息，不可删除！");
                return map;
            }
        }
        this.service.del(id);
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/add.shtml")
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request,BuildUnit bu){
        Map<String,Object> map = new HashMap<>();
        bu.setId(String.valueOf(this.commonService.getNextVal("build_unit")));
        this.service.add(bu);
        map.put("ret",0);
        map.put("msg","成功");
        map.put("id",bu.getId());
        return map;
    }



    @RequestMapping("/unitIndex.shtml")
    public String unitIndex(HttpServletRequest request){
        BuildUnit build = this.service.getOne(request.getParameter("pid"));
        BuildUnit unit = this.service.getOne(request.getParameter("id"));
        List<BuildUnit> buildUnits = this.service.queyNextLevalUnit(request.getParameter("id"));
        request.setAttribute("buildUnits",JsonUtil.getJsonString(buildUnits));
        request.setAttribute("unitId",request.getParameter("id"));
        request.setAttribute("build",build);
        request.setAttribute("unit",unit);
        Device device = this.managerDeviceService.getDeviceByUnit(request.getParameter("id"));
        request.setAttribute("device",device);
        if (device!=null){
            request.setAttribute("isOnline",MsgRouteFact.instance.getInstance().isClientOnline(device.getDeviceId()));
        }else {
            request.setAttribute("isOnline",false);
        }

        return "manage/residentailDetail/unitIndex";
    }



    @RequestMapping("/opendoor.shtml")
    @ResponseBody
    public Map<String,Object> opendoor(HttpServletRequest request){
        String deviceId = request.getParameter("deviceId");
        String flag = request.getParameter("flag");
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        int status = this.service.openDoor(deviceId,loginUser.getMid(),flag);
        Map<String,Object> map = new HashMap<>();
        if (status==1){
            if ("0".equals(flag)){
                this.logAuditService.log(LogAuditService.LOG_TYPE_OPENDOOR,"开门成功;设备id:"+deviceId,loginUser,request);
            }

            map.put("ret",0);
            map.put("msg","成功");
        }else {
            if ("0".equals(flag)){
                this.logAuditService.log(LogAuditService.LOG_TYPE_OPENDOOR,"开门失败;设备id:"+deviceId,loginUser,request);
            }
            map.put("ret",-1);
            map.put("msg","操作失败");
        }

        return map;
    }


    @RequestMapping(value = "/uploadFile.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> uploadFile(HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        MultipartFile resFile = ((MultipartHttpServletRequest)request).getFile("file");
        String[] arr = {"name","sex","phone","idType","idNo","country","people","address","house","level","openType","liveType","viewType"};
        List<Map<String,Object>> list = XlsUtil.analysisXls(arr,resFile);
        int result =0 ;
        try{
            result  = this.service.batchImport(list,request.getParameter("unitId"),loginUser.getMid());
        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("ret",-1);
            map.put("msg","解析数据异常,请检查文件是否符合模板");
            return map;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","解析出"+list.size()+"条数据!导入成功"+result+"条数据!");
        return map;
    }

    @RequestMapping("/selectPerson.shtml")
    public String selectPerson(HttpServletRequest request){
        request.setAttribute("house",request.getParameter("houseId"));
        return "manage/residentailDetail/selectPersonIndex";
    }
}
