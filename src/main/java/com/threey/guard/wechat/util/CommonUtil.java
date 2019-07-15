package com.threey.guard.wechat.util;

import com.threey.guard.wechat.domain.Token;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;

public class CommonUtil
{
  private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

  //发送微信模板消息接口
  private static String REQUEST_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
  public static final String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

  public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr)
  {
    JSONObject jsonObject = null;
    try
    {
      TrustManager[] tm = { new MyX509TrustManager() };
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new SecureRandom());

      SSLSocketFactory ssf = sslContext.getSocketFactory();

      URL url = new URL(requestUrl);
      HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
      conn.setSSLSocketFactory(ssf);

      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);

      conn.setRequestMethod(requestMethod);

      if (outputStr != null) {
        OutputStream outputStream = conn.getOutputStream();

        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }

      InputStream inputStream = conn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(
        inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(
        inputStreamReader);
      String str = null;
      StringBuffer buffer = new StringBuffer();
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }

      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      inputStream = null;
      conn.disconnect();
      jsonObject = JSONObject.fromObject(buffer.toString());
    } catch (ConnectException ce) {
      log.error("连接超时：{}", ce);
    } catch (Exception e) {
      log.error("https请求异常：{}", e);
    }
    return jsonObject;
  }

  public static Token getToken(String appid, String appsecret)
  {
    Token token = null;
    String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace(
      "APPSECRET", appsecret);

    JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

    if (jsonObject != null) {
      try {
        token = new Token();
        token.setAccessToken(jsonObject.getString("access_token"));
        token.setExpiresIn(jsonObject.getInt("expires_in"));
      } catch (JSONException e) {
        token = null;

        log.error("获取token失败 errcode:{} errmsg:{}", 
          Integer.valueOf(jsonObject.getInt("errcode")), 
          jsonObject.getString("errmsg"));
      }
    }
    return token;
  }

  public static String urlEncodeUTF8(String source)
  {
    String result = source;
    try {
      result = URLEncoder.encode(source, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String getFileExt(String contentType)
  {
    String fileExt = "";
    if ("image/jpeg".equals(contentType))
      fileExt = ".jpg";
    else if ("audio/mpeg".equals(contentType))
      fileExt = ".mp3";
    else if ("audio/amr".equals(contentType))
      fileExt = ".amr";
    else if ("video/mp4".equals(contentType))
      fileExt = ".mp4";
    else if ("video/mpeg4".equals(contentType))
      fileExt = ".mp4";
    return fileExt;
  }


  /**
   * 相关说明：发送模板消息
   * 开发者：tangchang
   * 时间：2014-12-23 下午7:27:52
   */
  public static String sendMessage(String accessToken,String xmlInfo){
    @SuppressWarnings("deprecation")
    HttpClient hc = new DefaultHttpClient();
    try {
      String requestUrl = REQUEST_SEND_URL+"?access_token="+ accessToken;
      HttpPost httppost = new HttpPost(requestUrl);
      ByteArrayInputStream bis = new ByteArrayInputStream(xmlInfo.getBytes("UTF-8"));
      InputStreamEntity ise = new InputStreamEntity(bis, xmlInfo.getBytes("UTF-8").length);
      httppost.setEntity(ise);
      HttpResponse httpResp = hc.execute(httppost);
      HttpEntity entity = httpResp.getEntity();
      String checkSession = null;
      if (entity != null) {
        InputStream in = entity.getContent();
        int totalbytes = (int) entity.getContentLength();
        byte[] buf = {};
        int size = 0;
        if (totalbytes > 0) {
          buf = new byte[totalbytes];
          while (size < totalbytes) {
            int c = in.read();
            buf[size++] = (byte) c;
          }
          checkSession = new String(buf, 0, size);
        }
        if(null!=checkSession.trim()&&!"".equals(checkSession.trim())){
          Map map = (Map)JSONObject.fromObject(checkSession);
          log.info("------微信消息推送返回信息："+checkSession+"------");
          if(StringUtils.isEmpty(map.get("errmsg"))==true){
            return "-1";
          }else {
            if(!"ok".equals(map.get("errmsg").toString())){
              return "-1";
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "-1";
    }
    return "0";
  }
}