package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.service.LoginService;
import com.threey.guard.base.util.*;
import com.threey.guard.manage.domain.Resource;
import com.threey.guard.manage.service.ManagerResourceService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
public class ManagerResourceController {

    private Logger log = Logger.getLogger(ManagerResourceController.class);

    private static String SERVER_PATH = GETProperties.readValue("SERVER_PATH");

    private static String STATIC_PATH = GETProperties.readValue("STATIC_RESOURCE_PATH");

    private static String FILE_EXT = GETProperties.readValue("FILE_EXT");

    @Autowired
    private ManagerResourceService managerResourceService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/toResourceList.shtml")
    public String toManagerUserList(HttpServletRequest request){
        return "manage/resource/resourceIndex";
    }

    @RequestMapping(value = "/page.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Resource> page(DataTable.Req p , HttpServletRequest request){
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
        map.put("resourceName",request.getParameter("p_resourceName"));
        map.put("resourceType",request.getParameter("p_resourceType"));
        map.put("resourceStatus",request.getParameter("p_resourceStatus"));
        List<Resource> resources = managerResourceService.list(map,p.getPage()-1,p.getLimit());
        int count = this.managerResourceService.count(map);
        return new DataTable.Resp<Resource>(resources,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Resource resource = this.managerResourceService.getOne(id);
            request.setAttribute("resource",resource);
            request.setAttribute("opt","edit");
        }
        return "manage/resource/resourceAdd";
    }

    @RequestMapping(value = "/save.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveOrUpdate(Resource resource, HttpServletRequest request, HttpServletResponse response){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(resource.getId())){
            this.managerResourceService.update(resource);

        }else{
            resource.setCreateUser(loginUser.getMid());
            this.managerResourceService.add(resource);
        }

        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/uploadFile.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> uploadFile(HttpServletRequest request, HttpServletResponse response){

        long startTime=System.currentTimeMillis();   //获取开始时间
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        MultipartFile resFile = multiRequest.getFile("file");
        Map<String ,Object> fileMap = new HashMap<String, Object>();
        Map<String,Object> map = new HashMap<>();
        boolean checkFile=true;
        String reStr = "";
        try {
            if (testFile(resFile)) {
                fileMap = saveFile(resFile, "file");
                checkFile=checkFile&&testMap(fileMap);
            } else {
                map.put("ret",-1);
                map.put("msg","文件上传失败");
                return map;
            }
            if (!checkFile) {
                reStr = "操作失败:文件类型不符合要求";
                log.info("上传资源不通过:"+reStr);
                //WebUtil.toPrint(response, reStr,"text/html");
                map.put("ret",-1);
                map.put("msg",reStr);
                return map;
            } else{
                map.put("ret",0);
                map.put("msg","成功");
                map.put("oldName",fileMap.get("resFileOrigName"));
                map.put("resFileDbPath",fileMap.get("resFileDbPath"));
                map.put("fileExt",fileMap.get("resFileExtNoDot"));
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            reStr = "操作失败:出现系统错误";
            log.info("上传资源不通过:"+reStr);
            //WebUtil.toPrint(response, reStr,"text/html");
            map.put("ret",-1);
            map.put("msg",reStr);
            return map;
        }
    }

    @RequestMapping(value = "/del.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request){
        String id = request.getParameter("id");
        this.managerResourceService.del(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/queryUnitByResidentail.shtml")
    public String queryUnitByResidentail(HttpServletRequest req){
        ManagerUser loginUser = loginService.getLoginManagerUser(req);
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
        map.put("resourceId",req.getParameter("id"));
        List<Privilege> privileges = this.managerResourceService.listUnitByResidentail(map);
        req.setAttribute("privileges", JsonUtil.getJsonString(privileges));
        req.setAttribute("resourceId",req.getParameter("id"));
        return "manage/resource/selectUnit";
    }

    @RequestMapping("/saveUnitByResidentail.shtml")
    @ResponseBody
    public Map<String,Object> saveUnitByResidentail(HttpServletRequest req){
        String resourceId = req.getParameter("resourceId");
        String units = req.getParameter("units");
        this.managerResourceService.saveUnitByResidentail(resourceId,units);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    private boolean testFile(MultipartFile resFile){
        if(resFile==null || resFile.getSize()==0){
            return false;
        }
        return true;

    }

    private Map<String, Object> saveFile(MultipartFile resFile,String prv) throws Exception{
        Map<String, Object> reMap = new HashMap<String, Object>();
        String resFileOrigName,resFileExt,resFileExtNoDot,resFileOrigNameNoExt;
        resFileOrigName = resFile.getOriginalFilename().toLowerCase();
        resFileOrigNameNoExt=resFileOrigName.substring(0, resFileOrigName.lastIndexOf("."));
        resFileExt = resFileOrigName.substring(resFileOrigName.lastIndexOf("."), resFileOrigName.length());
        resFileExtNoDot = resFileOrigName.substring(resFileOrigName.lastIndexOf(".")+1, resFileOrigName.length());
        boolean type = checkExt(resFileExtNoDot);
        if (!type) {
            reMap.put("error", "文件格式不对");
            return reMap;
        }
        log.info("上传资源文件大小:"+ FileUtil.formatFileSize(resFile.getSize()));
        log.info("上传资源文件源名称:"+resFileOrigName);
        log.info("上传资源文件源格式:"+resFileExt);
        String serverPath = SERVER_PATH;
        // String tempPath = ManageStatistic.MANAGE_RES_TEMPPATH_URL;
        String staticPath = STATIC_PATH;
        String curDateStr = StringUtil.getCurrDateStr();//时间格式yyyymmdd
        long resId = commonService.getNextVal("mj_resource");
        //资源文件路径
        String tempResFileName = prv+resId+resFileExt;
        String fileResTempFullDir = serverPath+staticPath+resFileExtNoDot+"/"+curDateStr+"/";
        String fileResTempFullPath = fileResTempFullDir+tempResFileName;
        log.info("资源文件服务器存放地址:"+fileResTempFullPath);
        String resFileDbPath = staticPath+resFileExtNoDot+"/"+curDateStr+"/"+tempResFileName;
        log.info("资源文件数据库存放地址:"+resFileDbPath);
        log.info("");

        //资源文件存储
        File tempResFileDir = new File(fileResTempFullDir);
        if(!tempResFileDir.exists()){
            tempResFileDir.mkdirs();
        }
        FileOutputStream resFileOutStream = null;
        InputStream resFileInPutStream = null;
        try {
            resFileOutStream = new FileOutputStream(fileResTempFullPath);
            resFileInPutStream = resFile.getInputStream();
            FileUtil.copyStream(resFile.getInputStream(), resFileOutStream, -1);
        } catch (Exception e) {
            throw e;
        }finally{
            IOUtils.closeQuietly(resFileInPutStream);
            IOUtils.closeQuietly(resFileOutStream);
        }
        log.info("resFileDbPath>>>>>>>>>>>>>>>"+resFileDbPath);
        log.info("resFileOrigName>>>>>>>>>>>>>>>"+resFileOrigName);
        log.info("resFileExtNoDot>>>>>>>>>>>>>>>"+resFileExtNoDot);
        log.info("fileResFullPath>>>>>>>>>>>>>>>"+fileResTempFullPath);
        reMap.put("ret",0);
        reMap.put("msg","成功");
        reMap.put("resFileDbPath",resFileDbPath);
        reMap.put("resFileOrigName",resFileOrigName);
        reMap.put("resFileExtNoDot",resFileExtNoDot);
        return reMap;

    }

    private  boolean checkExt(String ext){
        if (null==ext) {
            return false;
        }
        ext = ext.toUpperCase();
        String fM = FILE_EXT.toUpperCase();
        if (fM.indexOf(ext)>0) {
            return true;
        }
        return false;
    }

    private boolean testMap(Map<String, Object> map){
        return null == map.get("error");
    }
}
