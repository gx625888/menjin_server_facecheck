package com.threey.guard.api.controller;

import com.baidu.aip.face.AipFace;
import com.threey.guard.api.domain.AccountData;
import com.threey.guard.api.domain.Ads;
import com.threey.guard.api.domain.ApiResult;
import com.threey.guard.api.service.AsyncServcie;
import com.threey.guard.api.service.ClientApiService;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.util.FileUtil;
import com.threey.guard.base.util.GETProperties;
import com.threey.guard.base.util.JsonUtil;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.domain.BuildUnit;
import com.threey.guard.manage.domain.Device;
import com.threey.guard.manage.domain.HouseUnit;
import com.threey.guard.manage.service.BuildUnitService;
import com.threey.guard.manage.service.ManageHouseService;
import com.threey.guard.manage.service.ManagerDataServerService;
import com.threey.guard.manage.service.ManagerDeviceService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ClientAipController {

    private static  final Logger log = LoggerFactory.getLogger(ClientAipController.class);


    private static String FILE_EXT = GETProperties.readValue("FILE_EXT");
    private static String SERVER_PATH = GETProperties.readValue("SERVER_PATH");
    private static String CALL_PATH = GETProperties.readValue("CALL_PATH");

    private static String STATIC_PATH = GETProperties.readValue("STATIC_PATH");

    @Autowired
    private ClientApiService service;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AsyncServcie asyncServcie;

    @Autowired
    private ManagerDeviceService managerDeviceService;

    @Autowired
    private ManageHouseService manageHouseService;

    @Autowired
    private BuildUnitService buildUnitService;

    @Autowired
    private AipFace faceApiClient;


    /**
     * 获取客户端更新信息
     * @param request
     * @return
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public ApiResult<String> updateInfo(HttpServletRequest request){
        String deviceNo = request.getParameter("deviceNo");

        ApiResult<String> result = this.service.versonUpdate(deviceNo);
        log.info(JsonUtil.getJsonString(result));
        System.out.println(JsonUtil.getJsonString(result));
        return result;
    }

    /**
     * 获取广告位信息
     * @param request
     * @return
     */
    @RequestMapping("/screen/ads.do")
    @ResponseBody
    public ApiResult<List<Ads>>  getAdsInfo(HttpServletRequest request){
        ApiResult<List<Ads>>result = this.service.getAds(request.getParameter("deviceNo"));
        log.info(JsonUtil.getJsonString(result));
        System.out.println(JsonUtil.getJsonString(result));
        return result;
    }

    /**
     * 人脸检测
     * @param request
     * @return
     */
    @RequestMapping(value = "/face/detect.do",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ApiResult<String> detectFace(HttpServletRequest request) throws Exception {

        log.info(">>>>人脸识别/face/detect.do<<<<");
        String deviceId = request.getParameter("deviceId");
        String houseId = request.getParameter("houseId");
        String image = request.getParameter("image");

        /*
        BufferedReader br = request.getReader();
        String str, image = "";
        while((str = br.readLine()) != null){
            image += str;
        }
        */

        /*
        int len = request.getContentLength();
        ServletInputStream iii = request.getInputStream();
        byte[] buffer = new byte[len];
        iii.read(buffer, 0, len);
        */

        log.info("deviceId={} houseId={} imageSize={}",
                new Object[]{deviceId, houseId, image.length()});
        log.info(image);

        ApiResult<String>  result = new ApiResult<>();
        result.setCode(0);
        result.setMsg("success");


        // 1.根据设备ID找单元ID
        Device device = managerDeviceService.getDeviceById(deviceId);
        if (device == null) {
            result.setCode(1);
            result.setMsg("invalid device");
            return result;
        }

        String unitId = device.getUnit();

        // 单元
        BuildUnit unit = buildUnitService.getOne(unitId);
        if (unit == null) {
            result.setCode(1);
            result.setMsg("invalid unit");
            return result;
        }

        // 楼栋
        BuildUnit build = buildUnitService.getOne(unit.getParentId());
        if (build == null) {
            result.setCode(1);
            result.setMsg("invalid build");
            return result;
        }

        String faceGrpId = build.getResidentailId() + "_" + build.getId() + "_" + unitId;
        log.info(">>>>face.grp.id={}", faceGrpId);

        String imageType = "BASE64";
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("max_face_num", "2");
        //options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");

        options.put("match_threshold", "80");
        options.put("quality_control", "NORMAL");

        JSONObject searchResult = faceApiClient.search(image, imageType, faceGrpId, options);
        log.info("face.search.result={}", searchResult);
        long rtnCode = searchResult.getLong("error_code");
        if (rtnCode != 0) {
            result.setCode(1);
            result.setMsg("invalid");
        }

        return result;
    }


    /**
     * 开门信息上传
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload/infos.do",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ApiResult<String> uploadInfos(HttpServletRequest request) {
        log.info(">>>>上传开门信息:/upload/infos.do<<<<");
        String mode = request.getParameter("mode");
        String deviceNo = request.getParameter("deviceNo");
        String cardNo = request.getParameter("cardNo");
        Object[] info = {mode, deviceNo, cardNo};
        log.info("mode={} deciceNo={} cardNo={}", info);
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        MultipartFile resFile = multiRequest.getFile("file");
        Map<String, Object> fileMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        boolean checkFile = true;
        String reStr, remark;
        try {
            if (testFile(resFile)) {
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>4");
                fileMap = saveFile(resFile, "openRecord");
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>5");
                checkFile = checkFile && testMap(fileMap);
            }

            if (!checkFile) {
                reStr = "操作失败:文件类型不符合要求";
                log.info("上传资源不通过:"+reStr);
                remark = "上传资源不通过:"+reStr;
                //WebUtil.toPrint(response, reStr,"text/html");
            } else {
                map.put("ret",0);
                map.put("msg","成功");
                map.put("oldName",fileMap.get("resFileOrigName"));
                map.put("resFileDbPath",fileMap.get("resFileDbPath"));
                map.put("fileExt",fileMap.get("resFileExtNoDot"));
                remark = "成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            reStr = "操作失败:出现系统错误";
            log.info("上传资源不通过:"+reStr);
            remark = "上传资源不通过:"+reStr;
            //WebUtil.toPrint(response, reStr,"text/html");
        }

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2");
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("cardNo", cardNo);
        rmap.put("openType", mode);
        rmap.put("picture", map.get("resFileDbPath"));
        rmap.put("deviceId", deviceNo);
        rmap.put("remark", remark);
        if("4".equals(mode)){
            this.service.callRecord(rmap);//记录呼叫
            this.asyncServcie.sendWxMessage(deviceNo, cardNo,(String) map.get("resFileDbPath"));//推送呼叫记录

            log.info(">>>>begin sendNotify <<<");

            // 呼叫时如果有外部app对接，推送呼叫通知
            ///////如果有外部对接app则发送开门通知消息
            String woyeeUrl = GETProperties.readValue("woyee.srv.baseUrl");
            String notifyPath = GETProperties.readValue("woyee.srv.notifyPath");
            if (StringUtils.isNotEmpty(woyeeUrl)
                    && StringUtils.isNotEmpty(notifyPath)) {
                String url = woyeeUrl + notifyPath;
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                String houseNo = cardNo;
                Device device = managerDeviceService.getDeviceById(deviceNo);
                String unitId = device.getUnit();
                log.info(">>>>deviceNo={} unitId={}<<<<", deviceNo, unitId);
                HouseUnit houseUnit = manageHouseService.getHouseByUnitAndName(Long.parseLong(unitId),
                        houseNo);
                long houseId = houseUnit.getId();

                log.info(">>>>woyee:houseNo={} houseId={}",
                        new Object[]{houseNo, houseId});
                String pictureUrl = CALL_PATH + map.get("resFileDbPath");
                log.info(">>>>pictureUrl={}<<<<", pictureUrl);

                //Map<String, Object> params = new HashMap<>();
                //params.put("houseId", houseId);
                //params.put("picUrl", pictureUrl);

                url += "?houseId=" + houseId;
                url += "&picUrl=" + pictureUrl;
                log.info("sendNotify.url=>{}", url);

                HttpEntity httpEntity = new HttpEntity(null,headers);
                ResponseEntity<String> res = restTemplate.postForEntity(url, httpEntity,String.class);
                log.info(">>>>res.body={}<<<<", res.getBody());
                log.info(">>>>end sendNotify<<<");
            }
        }

        // 开门记录
        this.service.openRecord(rmap);
        ApiResult<String>  re = new ApiResult<>();
        re.setCode(0);
        re.setMsg("success");

        log.info(JsonUtil.getJsonString(re));
        return re;
    }

    /**
     * 告警信息上传
     * @param request
     * @return
     */
    @RequestMapping("/upload/warn.do")
    @ResponseBody
    public ApiResult<String> uploadWarnInfos(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("deviceId",request.getParameter("deviceNo"));
        map.put("warnType",request.getParameter("mode"));
        this.service.warnRecord(map);
        ApiResult<String>  re = new ApiResult<>();
        re.setCode(0);
        re.setMsg("success");
        log.info(JsonUtil.getJsonString(re));
        System.out.println(JsonUtil.getJsonString(re));
        return re;
    }

    /**
     * 验证临时密码
     * @param request
     * @return
     */
    @RequestMapping("/validate/pass.do")
    @ResponseBody
    public ApiResult<String> validatePass(HttpServletRequest request){

        ApiResult<String>  re = new ApiResult<>();
        re = this.service.getPwd(request.getParameter("deviceNo"),request.getParameter("pwd"));
        log.info(JsonUtil.getJsonString(re));
        System.out.println(JsonUtil.getJsonString(re));
        return re;
    }


    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("/update/accounts.do")
    @ResponseBody
    public ApiResult<List<AccountData>> getAccounts(HttpServletRequest request){
        log.info(">>>>update/accounts.do<<<<");
        long t1 = System.currentTimeMillis();
        ApiResult<List<AccountData>> result =  this.service.getAccounts(request.getParameter("deviceNo"));
        long t2 = System.currentTimeMillis();
        log.info("update.costs={}, result={}",
                t2-t1, JsonUtil.getJsonString(result));
        return result;
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("/update/houses.do")
    @ResponseBody
    public ApiResult<List<AccountData>> getHouses(HttpServletRequest request){
        log.info(">>>>update/houses.do<<<");
        long t1 = System.currentTimeMillis();
        ApiResult<List<AccountData>> result =  this.service.getHouses(request.getParameter("deviceNo"));
        long t2 = System.currentTimeMillis();
        log.info("update.costs={}, result={}",
                t2-t1, JsonUtil.getJsonString(result));
        return result;
    }

    @RequestMapping("/tel.do")
    @ResponseBody
    public ApiResult tel(HttpServletRequest request){
        ApiResult result =  this.service.getDeviceTel(request.getParameter("deviceNo"));
        log.info(JsonUtil.getJsonString(result));
        return result;
    }

    @RequestMapping("/call.do")
    @ResponseBody
    public ApiResult call(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("device",request.getParameter("deviceNo"));
        map.put("house",request.getParameter("houseNo"));
        ApiResult result =  this.service.deviceCall(map,1);//callNum客户端发起第一号码呼叫
        log.info(JsonUtil.getJsonString(result));
        return result;
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
        long resId = commonService.getNextVal("open_record");
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
