package com.threey.guard.wechat.util;

import com.threey.guard.base.util.CtxFactory;
import com.threey.guard.wechat.dao.ManagerWxBandDao;
import com.threey.guard.wechat.domain.Token;
import com.threey.guard.wechat.domain.WeChatPropertis;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信apptoken 管理
 */
public class AppTokenUtil {


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 检查刷新token
     * @param propertis
     */
    public static void checkAndReflesh(WeChatPropertis propertis){

        if (checkToken(propertis)){
            return ;
        }else{
            freshToken(propertis);
        }
    }

    /**
     *
     * @param propertis
     * @return true 未过期
     */
    private  static boolean checkToken(WeChatPropertis propertis) {

        if (StringUtils.isEmpty(propertis.getToken())){//没有token
            return false;
        }
        Date d = null;
        try {
            d = sdf.parse(propertis.getUpdateTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (System.currentTimeMillis()-d.getTime()>7000*1000){//7000秒
            return false;
        }
        return true;

    }

    /**
     *
     * @param propertis
     * @return
     */
    public  synchronized static void freshToken(WeChatPropertis propertis){

//        if (checkToken(propertis)){
//            return;
//        }
        Token token = CommonUtil.getToken(propertis.getAppId(),propertis.getAppSecret());

        if (null==token){
            return ;
        }
        propertis.setToken(token.getAccessToken());
        propertis.setUpdateTime(sdf.format(new Date()));

        ManagerWxBandDao managerWxBandDao = CtxFactory.getCtx().getBean(ManagerWxBandDao.class);
        managerWxBandDao.updateAppToken(propertis);

    }
}
