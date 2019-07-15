package com.threey.guard.wechat.controller;

import com.threey.guard.wechat.domain.WeChatPropertis;
import com.threey.guard.wechat.service.ManagerWxBandService;
import com.threey.guard.wechat.service.MyHourseService;
import com.threey.guard.wechat.util.SmsUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wechat/my")
public class MyHourseController {

    private static  final Logger log = LoggerFactory.getLogger(MyHourseController.class);


    @Autowired
    private MyHourseService service;

    @Autowired
    private ManagerWxBandService bindService;

    @RequestMapping("/pwd.act")
    @ResponseBody
    public Map<String,Object> pwd(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        //map.put("code",this.service.createPwd(request.getParameter("deviceId"),request.getParameter("userId")));
        map.put("code",this.service.createPwdDynamic(request.getParameter("deviceId"),request.getParameter("userId")));
       // map.put("code",DynamicPwd.createPwd(request.getParameter("deviceId")));
        return map;
    }

    @RequestMapping("/opendoor.act")
    @ResponseBody
    public Map<String,Object> opendoor(HttpServletRequest request) {
        String openId = (String) request.getSession().getAttribute("wechat_user_open_id");
        String houseNo = request.getParameter("houseNo");
        String houseId = request.getParameter("houseId");
        String deviceId = request.getParameter("deviceId");
        String openType = request.getParameter("openType");
        String userId = request.getParameter("userId");
        log.info(">>>>opendoor.act:houseNo={} houseId={} deviceId={} userId={} openId={} openType={}",
                new Object[]{houseNo, houseId, deviceId, userId, openId, openType});
        int status = this.service.openDoor(houseNo, houseId, deviceId, userId, openId, openType);
        Map<String,Object> map = new HashMap<>();
        if (status==1){
            map.put("ret",0);
            map.put("msg","操作成功");
        }else {
            map.put("ret",-1);
            map.put("msg","操作失败");
        }
        return map;
    }


    @RequestMapping("/unbind.act")
    @ResponseBody
    public Map<String,Object> unbind(HttpServletRequest request){
        String openId = (String) request.getSession().getAttribute("wechat_user_open_id");
        this.service.unbind(openId,request.getParameter("houseId"));
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","操作成功");
        return map;
    }


    @RequestMapping("/sendCode.act")
    @ResponseBody
    public Map<String,Object> sendCode(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String phone = request.getParameter("phone");
        boolean hasPhone = this.bindService.checkPhone(phone);
        if (!hasPhone){
            map.put("ret",-1);
            map.put("msg","对不起,您的手机号不在系统中!");
            return map;
        }

        String code = SmsUtil.createCode(6);
        boolean result = SmsUtil.send(phone,code);
        request.getSession().setAttribute("wx_check_phone_code",code);
        request.getSession().setAttribute("wx_check_phone_phone",phone);


        if (result){
            map.put("ret",0);
            map.put("msg","发送成功");
        }else {
            map.put("ret",-2);
            map.put("msg","对不起,发送失败了");
        }
        return map;
    }

    @RequestMapping("/bindPhone.act")
    @ResponseBody
    public Map<String,Object> bindPhone(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String openId = (String) request.getSession().getAttribute("wechat_user_open_id");
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String sessionCode = (String) request.getSession().getAttribute("wx_check_phone_code");
        String sessionPhone = (String) request.getSession().getAttribute("wx_check_phone_phone");
        if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(code)){
            map.put("ret",-1);
            map.put("msg","参数错误!");
            return map;
        }
        if (StringUtils.isEmpty(sessionCode)){
            map.put("ret",-1);
            map.put("msg","请发送验证码!");
            return map;
        }
        if (sessionCode.equals(code)&&phone.equals(sessionPhone)){
            WeChatPropertis propertis = (WeChatPropertis) request.getSession().getAttribute("wechat_propertis");
            this.bindService.bindPhone(openId,phone,propertis.getCompany());
            map.put("ret",0);
            map.put("msg","操作成功");
            return map;
        }else {
            map.put("ret",-1);
            map.put("msg","验证码错误!");
            return map;
        }
    }
}
