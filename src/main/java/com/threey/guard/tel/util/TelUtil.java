package com.threey.guard.tel.util;

import com.google.gson.Gson;
import com.threey.guard.base.util.GETProperties;
import com.threey.guard.base.util.MD5Util;
import com.threey.guard.tel.domain.CallResponse;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 呼叫中心工具类
 */
public class TelUtil {

    private static String accountId = GETProperties.readValue("tel.account.id");
    private static String accountToken = GETProperties.readValue("tel.account.token");
    private static String appId = GETProperties.readValue("tel.app.id");
    private static String callUrl = GETProperties.readValue("tel.url.call");


    /**
     * 发起呼叫
     *
     * @param send 发起号码
     * @param to   被叫号码
     * @return 0 呼叫成功
     */
    public static Map<String,String> call(String send, String to) throws Exception {
        Date today = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");//20170831055228
        String time = f.format(today);
        String SigParameter = MD5Util.MD5(accountId + accountToken + time).toUpperCase();//验证参数
        String Authorization = new BASE64Encoder().encode((accountId + ":" + time).getBytes("UTF-8"));//报头验证信息
        String json = "{\"appId\":\"" + appId + "\",\"from\":\"" + send + "\",\"to\":\"" +
                to + "\",\"userData\":\"1234\",\"getFeedBack\":1,\"keyRange\":\"*\"}";

        String url = callUrl + "?sig=" + SigParameter;

        String content = send(accountId, accountToken, Authorization, url, json, "callBack");
        System.out.println(content);
        //{ "resp": { "respCode": 0, "callBack": { "callId": "api001000229ec1540968205704K52cu", "createTime": "20181031144325" } } }
        //{ "resp": { "respCode": 101035 } }
        Gson gson = new Gson();
        CallResponse callResponse = gson.fromJson(content,CallResponse.class);
        Map<String,String>  map = new HashMap<>();
        if (callResponse.getResp().getRespCode()==0){//成功
            map.put("ret","0");//失败时放错误代码
            map.put("callId",callResponse.getResp().getCallBack().getCallId());
        }else{
            map.put("ret",String.valueOf(callResponse.getResp().getRespCode()));
            map.put("callId","");
        }
        return map;
    }


    //发送请求
    public static String send(String accountSid, String subAccountToken, String Authorization, String ADD_URL, String json, String param) throws Exception {

        StringBuffer sb = null;
        try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/json;charset=utf-8");
            connection.setRequestProperty("Accept",
                    "application/json");
            connection.setRequestProperty("Authorization", Authorization);

            connection.connect();

            //POST请求
            DataOutputStream outs = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.element(param, json);
            System.out.println(obj.toString());
            outs.writeBytes(obj.toString());
            outs.flush();
            outs.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }

            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
         call("1001", "15236192196");
        String failedStr = "{ \"resp\": { \"respCode\": 101035 } }";
        String successStr = "{ \"resp\": { \"respCode\": 0, \"callBack\": { \"callId\": \"api001000229ec1540968205704K52cu\", \"createTime\": \"20181031144325\" } } }";

        Gson gson = new Gson();
        CallResponse response = gson.fromJson(failedStr,CallResponse.class);

       response = gson.fromJson(successStr,CallResponse.class);
        System.out.println(response);
    }
}
