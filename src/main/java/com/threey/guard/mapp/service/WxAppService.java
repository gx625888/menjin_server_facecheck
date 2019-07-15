package com.threey.guard.mapp.service;

import com.threey.guard.mapp.dao.WxAppDao;
import com.threey.guard.mapp.domain.WxAppBindingVO;
import net.sf.json.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WxAppService.java
 *
 * @auth mulths@126.com
 * @date 2019/02/18
 */
@Service
public class WxAppService {

    //private static final Logger logger = Logger.getLogger(WxAppService.class);
    private static final Logger logger = LoggerFactory.getLogger(WxAppService.class);

    @Resource
    private WxAppDao wxAppDao;

    public String getBindingPhone(String appId, String openId) {

        logger.info("appId={} openId={}", appId, openId);

        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("openId", openId);

        return wxAppDao.getBindingPhone(param);
    }

    public void bindPhone(String appid, String openid, String phone) {
        wxAppDao.cleanBind(appid, openid);
        wxAppDao.bindPhone(appid, openid, phone);
    }

    public List<WxAppBindingVO> getBindingHouses(String phone) {
        return wxAppDao.getBindingHouses(phone);
    }

}
