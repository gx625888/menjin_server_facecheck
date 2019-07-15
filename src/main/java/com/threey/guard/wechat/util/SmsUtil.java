package com.threey.guard.wechat.util;

import com.google.gson.Gson;
import com.threey.guard.base.util.GETProperties;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信接口
 */
public class SmsUtil {

    private static final Logger logger = Logger.getLogger(SmsUtil.class);
    private static Gson gson = new Gson();

    /**
     * 发送短信
     * @param phone 手机号
     * @param msg 信息
     * @return true成功  false 失败
     */
    public static boolean send(String phone,String msg){
        logger.info("发送信息!phone--->"+phone+"$msg---->"+msg);

        return alisms(phone, msg);

    }


    public SmsUtil() {
    }

    private static boolean alisms(String phone, String msg){
        String host = GETProperties.readValue("sms.url.host");
        String path = GETProperties.readValue("sms.url.path");
//        String method = "GET";
        String appcode = GETProperties.readValue("sms.code");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile",phone);
        querys.put("paras", msg+",2");
        querys.put("sign", "消息通");
        querys.put("tpid", "009");


        try {

            HttpResponse response = HttpUtils.doGet(host, path, headers, querys);
            System.out.println(response.getEntity().toString());

            //获取response的body
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            Map<String,Object> map = gson.fromJson(result,HashMap.class);
            return ((Double) map.get("result")).intValue()==0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
       // send("15236192196",createCode(6));
        String msg = "{\"result\":0,\"errmsg\":\"OK\",\"ext\":\"\",\"sid\":\"8:T6CNkIVT1pav41oW3P720181110\",\"fee\":1}";
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(msg,HashMap.class);
        System.out.println(map);
        System.out.println(((Double) map.get("result")).intValue()==0);
    }

    public static String createCode(int length){
        return Math.abs(Math.round(Math.random()*(Math.pow(10, length-1)*9-1)+(Math.pow(10, length-1))))+"";
    }
}
