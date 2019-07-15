package com.threey.guard.mapp.controller;

import com.threey.guard.base.util.GETProperties;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.domain.MsgBean;
import com.threey.guard.mapp.WxMappingJackson2HttpMessageConverter;
import com.threey.guard.mapp.domain.WxAppBindingVO;
import com.threey.guard.mapp.service.WxAppService;
import com.threey.guard.quartz.util.MsgBeanUtil;
import com.threey.guard.wechat.service.ManagerWxBandService;
import com.threey.guard.wechat.service.MyHourseService;
import com.threey.guard.wechat.util.SmsUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * WxAppController.java
 *
 * @auth mulths@126.com
 * @date 2019/02/15
 */
@RequestMapping("/wxapp")
@Controller
public class WxAppController {

    private static  final  Logger LOG = LoggerFactory.getLogger(WxAppController.class);

    @Resource
    private WxAppService wxAppService;

    @Resource
    private ManagerWxBandService managerWxBandService;

    @Resource
    private MyHourseService myHourseService;

    @RequestMapping("/code2session.srv")
    @ResponseBody
    public JSONObject getUserInfo(HttpServletRequest request) throws Exception {
        LOG.info("+++++++code2session+++++");
        String appId = GETProperties.readValue("wxapp.appId");
        String secret = GETProperties.readValue("wxapp.secret");
        String baseUrl = GETProperties.readValue("wxapp.srv.baseUrl");
        String userInfoPath = GETProperties.readValue("wxapp.srv.userInfoPath");
        String code = request.getParameter("code");
        LOG.info("code=" + code + ",appid="+appId + ",secret=" + secret);

        String url = baseUrl + userInfoPath;
        url += "?appid=" + appId;
        url += "&secret=" + secret;
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";

        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        JSONObject res = null;
        try {
            res = template.getForObject(url, JSONObject.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        LOG.info("result={}", res);

        return res;
    }

    /**++++++获取微信小程序绑定手机号++++++*/
    @RequestMapping("/getBindingPhone.srv")
    @ResponseBody
    public String getBindingPhone(HttpServletRequest request) throws Exception {
        String appId = GETProperties.readValue("wxapp.appId");
        String openid = request.getParameter("openid");
        LOG.info("appid={} openid={}", appId, openid);

        String phone = wxAppService.getBindingPhone(appId, openid);
        if (StringUtils.isEmpty(phone)) {
            return "";
        }

        return phone;
    }

    /**
     * 根据绑定手机号获取绑定信息（房间／楼层／单元／楼栋／小区／设备）
     * */
    @RequestMapping("/getBindingHouses.srv")
    @ResponseBody
    public List<WxAppBindingVO> getBindingHouses(HttpServletRequest request) throws Exception {
        String phone = request.getParameter("phone");
        LOG.info("bidingPhone={}", phone);

        return wxAppService.getBindingHouses(phone);
    }

    /**发送手机验证码*/
    @RequestMapping("/sendVerifyCode.srv")
    @ResponseBody
    public JSONObject sendVerifyCode(HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        String phone = request.getParameter("phone");
        boolean exists = managerWxBandService.checkPhone(phone);
        if (!exists) {
            result.put("status", "1");
            result.put("message", "手机号在系统中不存在");
            return result;
        }

        String code = SmsUtil.createCode(6);
        boolean succeed = SmsUtil.send(phone,code);
        if (succeed) {
            result.put("status", "0");
            result.put("message", "发送成功");
            result.put("verifyCode", code);
        } else {
            result.put("status", "1");
            result.put("message", "发送失败");
        }

        return result;
    }

    /**绑定手机号*/
    @RequestMapping("/bindPhone.srv")
    @ResponseBody
    public JSONObject bindPhone(HttpServletRequest request) throws Exception {
        LOG.info("++++wxapp.bindPhone++++");
        String appId = request.getParameter("appid");
        String openId = request.getParameter("openid");
        String phone = request.getParameter("phone");
        LOG.info("appid={} openid={}", appId, openId);
        LOG.info("phone={}", phone);
        JSONObject result = new JSONObject();
        result.put("status", "0");
        result.put("message", "ok");
        try {
            wxAppService.bindPhone(appId, openId, phone);
        } catch (Exception e) {
            LOG.error("phone-binding error:{}", e.getMessage());
            e.printStackTrace();
            result.put("status", "1");
            result.put("message", "绑定失败，请联系管理员");
        }

        return result;
    }

    /**生成临时密码*/
    @RequestMapping("/getTmpPwd")
    @ResponseBody
    public JSONObject getTmpPwd(HttpServletRequest request) throws Exception {
        LOG.info("++++++getTmpPwd++++++");

        JSONObject result = new JSONObject();
        String deviceId = request.getParameter("deviceId");
        String userId = request.getParameter("userId");
        String tmpPwd = myHourseService.createPwdDynamic(deviceId, userId);
        LOG.info("tmpPwd={}", tmpPwd);

        result.put("status", "0");
        result.put("pwd", tmpPwd);
        result.put("message", "ok");

        return result;
    }

    /**
     * 发送开门指令
     * */
    @RequestMapping("/opendoor.srv")
    @ResponseBody
    public JSONObject opendoor(HttpServletRequest request) throws Exception {

        JSONObject res = new JSONObject();

        String deviceId = request.getParameter("deviceId");
        String openid = request.getParameter("openid");
        String userId = request.getParameter("userId");
        String houseId = request.getParameter("houseId");
        String houseNo = request.getParameter("houseNo");

        LOG.info(">>>>opendoor.srv:deviceId={} houseNo={} houseId={} userId={} openid={}",
                new Object[] {deviceId, houseNo, houseId, userId, openid});
        String optUser = "wxapp:" + houseNo + ":" + houseId + ":" + userId + ":" + openid;
        LOG.info(">>>>deviceId={} optUser={}<<<<", deviceId, optUser);

        MsgBean msgBean = new MsgBean();
        msgBean.setMsgType(MsgBean.TYPE_OPEN);
        msgBean.setStatus(MsgBean.STATUS_SUCCESS);
        msgBean.setDeviceId(deviceId);
        msgBean.setOptUser(optUser);
        int sendStatus = MsgUtil.send(MsgBeanUtil.parseMsg(msgBean));
        LOG.info("status={}", sendStatus);
        if (sendStatus == 1) {
            res.put("status", "0");
        } else {
            res.put("status", "1");
        }

        return res;
    }
}
