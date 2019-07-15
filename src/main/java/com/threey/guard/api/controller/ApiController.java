package com.threey.guard.api.controller;

import com.threey.guard.api.domain.AccountData;
import com.threey.guard.api.domain.Ads;
import com.threey.guard.api.domain.ApiResult;
import com.threey.guard.api.service.ApiService;
import com.threey.guard.clientmsg.dao.SendMsgDao;
import com.threey.guard.clientmsg.domain.MsgBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端接口
 */
@Controller
@RequestMapping("/apitext")
public class ApiController {

    private static final Logger logger = Logger.getLogger(ApiController.class);


    @Autowired
    private ApiService service;

    @Autowired
    private SendMsgDao msgDao;


    /**
     * 获取客户端更新信息
     * @param request
     * @return
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public ApiResult<String> updateInfo(HttpServletRequest request){
        String deviceNo = request.getParameter("deviceNo");

        return this.service.updateInfo(deviceNo);
    }

    @RequestMapping("/pushupdate.do")
    @ResponseBody
    public int pushupdateInfo(HttpServletRequest request){
        String deviceNo = request.getParameter("deviceNo");
        MsgBean msgBean = new MsgBean();
        msgBean.setMsgType(MsgBean.TYPE_APP);
        msgBean.setStatus(0);
        msgBean.setDeviceId(deviceNo);
        this.msgDao.insert(msgBean);
        return 1;
    }



    /**
     * 获取广告位信息
     * @param request
     * @return
     */
    @RequestMapping("/screen/ads.do")
    @ResponseBody
    public ApiResult<List<Ads>>  getAdsInfo(HttpServletRequest request){
        return this.service.getAdsInfo(request.getParameter("deviceNo"));
    }


    /**
     * 开门信息上传
     * @param request
     * @return
     */
    @RequestMapping("/upload/infos.do")
    @ResponseBody
    public ApiResult<String> uploadInfos(HttpServletRequest request){
        ApiResult<String>  re = new ApiResult<>();
        re.setCode(0);
        re.setMsg("success");
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
        ApiResult<String>  re = new ApiResult<>();
        re.setCode(0);
        re.setMsg("success");
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
        re.setCode(0);
        re.setMsg("success");
        return re;
    }


    /**
     * 获取设备绑卡信息
     * @param request
     * @return
     */
    @RequestMapping("/update/accounts.do")
    @ResponseBody
    public ApiResult<List<AccountData>> getAccounts(HttpServletRequest request){
        ApiResult<List<AccountData>>  re = new ApiResult<>();
        re.setCode(0);
        re.setMsg("success");

        List<AccountData> list = new ArrayList<>();
        AccountData a1 = new AccountData();
        a1.setCard("123123");
        a1.setId("123123");
        a1.setIsAdd(1);
        list.add(a1);
        re.setData(list);
        return re;
    }
}
