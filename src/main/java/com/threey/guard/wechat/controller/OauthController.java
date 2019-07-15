package com.threey.guard.wechat.controller;

import com.threey.guard.base.util.GETProperties;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.wechat.domain.*;
import com.threey.guard.wechat.service.ManagerWxBandService;
import com.threey.guard.wechat.util.AdvancedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号跳转
 */
@RequestMapping("/wechat")
@Controller
public class OauthController {

    private final static String SESSION_KEY_OPEN_ID = "wechat_user_open_id";
    private final static String SESSION_KEY_PRO = "wechat_propertis";

    @Autowired
    ManagerWxBandService managerWxBandService;

    @RequestMapping("/test.act")
    public String test() {
        return "test";
    }

    @RequestMapping("/index.act")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        if ("debug".equals(GETProperties.readValue("wechat.state"))){
            request.getSession().setAttribute(SESSION_KEY_OPEN_ID,"testopenid");
            request.getSession().setAttribute(SESSION_KEY_PRO, this.managerWxBandService.getWeChatPropertis(null));
        }else{
            if (null==request.getSession().getAttribute(SESSION_KEY_OPEN_ID)){
                String code = request.getParameter("code");
                WeChatPropertis propertis = this.managerWxBandService.getWeChatPropertis(request.getParameter("state"));
//                request.getSession().setAttribute(SESSION_KEY_PRO,propertis);
                WeixinOauth2Token weixinOauth2Token =
                        AdvancedUtil.getOauth2AccessToken(propertis.getAppId(), propertis.getAppSecret(), code);

                String userOpenId = weixinOauth2Token.getOpenId();
                request.getSession().setAttribute(SESSION_KEY_OPEN_ID,userOpenId);
                request.getSession().setAttribute(SESSION_KEY_PRO,propertis);
            }
        }
        String openId = (String) request.getSession().getAttribute(SESSION_KEY_OPEN_ID);
        WeChatPropertis propertis = (WeChatPropertis) request.getSession().getAttribute(SESSION_KEY_PRO);
        String bindPhone = this.managerWxBandService.getBindPhone(openId,propertis.getCompany());
        if (null!=bindPhone){//绑定了手机号
            List<WxBandVO> houses = this.managerWxBandService.getBindHousesByPhone(bindPhone,propertis.getCompany());
            request.setAttribute("bindPhone",bindPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1^_^$2"));
            request.setAttribute("houses",houses);
            return "index";
        }else{//未绑定手机号
            return "bandIndex";
        }
    }

    @RequestMapping("/bandIndex.act")
    public String bandIndex(HttpServletRequest request, HttpServletResponse response) {
        String userOpenId= (String) request.getSession().getAttribute(SESSION_KEY_OPEN_ID);

        List<SelectObj> list = managerWxBandService.getSelectObj(userOpenId,1);
        request.getSession().setAttribute("residentail",list);
        return "bandIndex";
    }

    @RequestMapping("/selectObj.act")
    public @ResponseBody List<SelectObj> selectObj(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        int type = Integer.parseInt(request.getParameter("type"));
        List<SelectObj> list = managerWxBandService.getSelectObj(id,type);
        return list;
    }

    @RequestMapping("/insertWxBand.act")
    public @ResponseBody
    Map<String,Object> insertWxBand(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        String userOpenId= (String) request.getSession().getAttribute(SESSION_KEY_OPEN_ID);
        WxBand wxBand = new WxBand();
        String house = request.getParameter("house");
        String phone = request.getParameter("phone");
        Map<String,Object> resMap  = new HashMap<>();
        resMap.put("house",house);
        resMap.put("phone",phone);
        String personId = managerWxBandService.queryPersonId(resMap);
        if(StringUtil.isEmpty(personId)){
            map.put("ret",-1);
            map.put("msg","居民信息不存在！");
            return map;
        }
        wxBand.setWxId(userOpenId);
        wxBand.setHouseId(house);
        wxBand.setPhone(phone);
        wxBand.setPersonId(personId);
        managerWxBandService.insertWxBand(wxBand);
        map.put("ret",0);
        map.put("msg","绑定成功");
        return map;
    }

}
