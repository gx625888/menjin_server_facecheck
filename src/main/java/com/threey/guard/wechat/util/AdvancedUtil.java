package com.threey.guard.wechat.util;

import com.threey.guard.wechat.domain.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AdvancedUtil {
    private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);

    public static String makeTextCustomMessage(String openId, String content) {
        content = content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
        return String.format(jsonMsg, new Object[]{openId, content});
    }

    public static String makeImageCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, new Object[]{openId, mediaId});
    }

    public static String makeVoiceCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, new Object[]{openId, mediaId});
    }

    public static String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
        return String.format(jsonMsg, new Object[]{openId, mediaId, thumbMediaId});
    }

//  public static String makeMusicCustomMessage(String openId, Music music)
//  {
//    String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
//    jsonMsg = String.format(jsonMsg, new Object[] { openId, JSONObject.fromObject(music)
//      .toString() });
//
//    jsonMsg = jsonMsg.replace("thumbmediaid", "thumb_media_id");
//    return jsonMsg;
//  }
//
//  public static String makeNewsCustomMessage(String openId, List<Article> articleList)
//  {
//    String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
//    jsonMsg = String.format(
//      jsonMsg, new Object[] {
//      openId,
//      JSONArray.fromObject(articleList).toString()
//      .replaceAll("\"", "\\\"") });
//
//    jsonMsg = jsonMsg.replace("picUrl", "picurl");
//    return jsonMsg;
//  }

    public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
        log.info("消息内容：{}", jsonMsg);
        boolean result = false;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",
                jsonMsg);

        if (jsonObject != null) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (errorCode == 0) {
                result = true;
                log.info("客服消息发送成功 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            } else {
                log.error("客服消息发送失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }

        return result;
    }

    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;

        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode),
                        errorMsg);
            }
        }
        return wat;
    }

    public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
        WeixinOauth2Token wat = null;

        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode),
                        errorMsg);
            }
        }
        return wat;
    }

    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;

        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
                "OPENID", openId);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                snsUserInfo = new SNSUserInfo();

                snsUserInfo.setOpenId(jsonObject.getString("openid"));

                snsUserInfo.setNickname(jsonObject.getString("nickname"));

                snsUserInfo.setSex(jsonObject.getInt("sex"));

                snsUserInfo.setCountry(jsonObject.getString("country"));

                snsUserInfo.setProvince(jsonObject.getString("province"));

                snsUserInfo.setCity(jsonObject.getString("city"));

                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));

                snsUserInfo.setPrivilegeList(JSONArray.toList(
                        jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }
        return snsUserInfo;
    }

    public static WeixinQRCode createTemporaryQRCode(String accessToken, int expireSeconds, int sceneId) {
        WeixinQRCode weixinQRCode = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);

        String jsonMsg = "{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",
                String.format(jsonMsg, new Object[]{Integer.valueOf(expireSeconds), Integer.valueOf(sceneId)}));

        if (jsonObject != null) {
            try {
                weixinQRCode = new WeixinQRCode();
                weixinQRCode.setTicket(jsonObject.getString("ticket"));
                weixinQRCode.setExpireSeconds(jsonObject
                        .getInt("expire_seconds"));
                log.info("创建临时带参二维码成功 ticket:{} expire_seconds:{}",
                        weixinQRCode.getTicket(),
                        Integer.valueOf(weixinQRCode.getExpireSeconds()));
            } catch (Exception e) {
                weixinQRCode = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("创建临时带参二维码失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode),
                        errorMsg);
            }
        }
        return weixinQRCode;
    }

    public static String createPermanentQRCode(String accessToken, int sceneId) {
        String ticket = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);

        String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",
                String.format(jsonMsg, new Object[]{Integer.valueOf(sceneId)}));

        if (jsonObject != null) {
            try {
                ticket = jsonObject.getString("ticket");
                log.info("创建永久带参二维码成功 ticket:{}", ticket);
            } catch (Exception e) {
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("创建永久带参二维码失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode),
                        errorMsg);
            }
        }
        return ticket;
    }

    public static String getQRCode(String ticket, String savePath) {
        String filePath = null;

        String requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
        requestUrl = requestUrl.replace("TICKET",
                CommonUtil.urlEncodeUTF8(ticket));
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            if (!savePath.endsWith("/")) {
                savePath = savePath + "/";
            }

            filePath = savePath + ticket + ".jpg";

            BufferedInputStream bis = new BufferedInputStream(
                    conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();

            conn.disconnect();
            log.info("根据ticket换取二维码成功，filePath=" + filePath);
        } catch (Exception e) {
            filePath = null;
            log.error("根据ticket换取二维码失败：{}", e);
        }
        return filePath;
    }

    public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
                "OPENID", openId);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                weixinUserInfo = new WeixinUserInfo();

                weixinUserInfo.setOpenId(jsonObject.getString("openid"));

                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));

                weixinUserInfo.setSubscribeTime(jsonObject
                        .getString("subscribe_time"));

                weixinUserInfo.setNickname(jsonObject.getString("nickname"));

                weixinUserInfo.setSex(jsonObject.getInt("sex"));

                weixinUserInfo.setCountry(jsonObject.getString("country"));

                weixinUserInfo.setProvince(jsonObject.getString("province"));

                weixinUserInfo.setCity(jsonObject.getString("city"));

                weixinUserInfo.setLanguage(jsonObject.getString("language"));

                weixinUserInfo
                        .setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (weixinUserInfo.getSubscribe() == 0) {
                    log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("获取用户信息失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode),
                            errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }

    public static WeixinUserList getUserList(String accessToken, String nextOpenId) {
        WeixinUserList weixinUserList = null;

        if (nextOpenId == null) {
            nextOpenId = "";
        }

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
                "NEXT_OPENID", nextOpenId);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                weixinUserList = new WeixinUserList();
                weixinUserList.setTotal(jsonObject.getInt("total"));
                weixinUserList.setCount(jsonObject.getInt("count"));
                weixinUserList.setNextOpenId(jsonObject
                        .getString("next_openid"));
                JSONObject dataObject = (JSONObject) jsonObject.get("data");
                weixinUserList.setOpenIdList(JSONArray.toList(
                        dataObject.getJSONArray("openid"), List.class));
            } catch (JSONException e) {
                weixinUserList = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取关注者列表失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }
        return weixinUserList;
    }

    public static List<WeixinGroup> getGroups(String accessToken) {
        List weixinGroupList = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                weixinGroupList = JSONArray.toList(
                        jsonObject.getJSONArray("groups"), WeixinGroup.class);
            } catch (JSONException e) {
                weixinGroupList = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("查询分组失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }
        return weixinGroupList;
    }

    public static WeixinGroup createGroup(String accessToken, String groupName) {
        WeixinGroup weixinGroup = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);

        String jsonData = "{\"group\" : {\"name\" : \"%s\"}}";

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",
                String.format(jsonData, new Object[]{groupName}));

        if (jsonObject != null) {
            try {
                weixinGroup = new WeixinGroup();
                weixinGroup.setId(jsonObject.getJSONObject("group")
                        .getInt("id"));
                weixinGroup.setName(jsonObject.getJSONObject("group")
                        .getString("name"));
            } catch (JSONException e) {
                weixinGroup = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("创建分组失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }
        return weixinGroup;
    }

    public static boolean updateGroup(String accessToken, int groupId, String groupName) {
        boolean result = false;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);

        String jsonData = "{\"group\": {\"id\": %d, \"name\": \"%s\"}}";

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",
                String.format(jsonData, new Object[]{Integer.valueOf(groupId), groupName}));

        if (jsonObject != null) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (errorCode == 0) {
                result = true;
                log.info("修改分组名成功 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            } else {
                log.error("修改分组名失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }
        return result;
    }

    public static boolean updateMemberGroup(String accessToken, String openId, int groupId) {
        boolean result = false;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);

        String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",
                String.format(jsonData, new Object[]{openId, Integer.valueOf(groupId)}));

        if (jsonObject != null) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (errorCode == 0) {
                result = true;
                log.info("移动用户分组成功 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            } else {
                log.error("移动用户分组失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }
        return result;
    }

    public static WeixinMedia uploadMedia(String accessToken, String type, String mediaFileUrl) {
        WeixinMedia weixinMedia = null;

        String uploadMediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken)
                .replace("TYPE", type);

        String boundary = "------------7da2e536604c8";
        try {
            URL uploadUrl = new URL(uploadMediaUrl);
            HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl
                    .openConnection();
            uploadConn.setDoOutput(true);
            uploadConn.setDoInput(true);
            uploadConn.setRequestMethod("POST");

            uploadConn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            OutputStream outputStream = uploadConn.getOutputStream();

            URL mediaUrl = new URL(mediaFileUrl);
            HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl
                    .openConnection();
            meidaConn.setDoOutput(true);
            meidaConn.setRequestMethod("GET");

            String contentType = meidaConn.getHeaderField("Content-Type");

            String fileExt = CommonUtil.getFileExt(contentType);

            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream
                    .write(
                            String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", new Object[]{
                                    fileExt}).getBytes());
            outputStream.write(String.format("Content-Type: %s\r\n\r\n", new Object[]{
                    contentType}).getBytes());

            BufferedInputStream bis = new BufferedInputStream(
                    meidaConn.getInputStream());
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1) {
                outputStream.write(buf, 0, size);
            }

            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.close();
            bis.close();
            meidaConn.disconnect();

            InputStream inputStream = uploadConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            uploadConn.disconnect();

            JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
            weixinMedia = new WeixinMedia();
            weixinMedia.setType(jsonObject.getString("type"));

            if ("thumb".equals(type))
                weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
            else
                weixinMedia.setMediaId(jsonObject.getString("media_id"));
            weixinMedia.setCreatedAt(jsonObject.getInt("created_at"));
        } catch (Exception e) {
            weixinMedia = null;
            log.error("上传媒体文件失败：{}", e);
        }
        return weixinMedia;
    }

    public static String getMedia(String accessToken, String mediaId, String savePath) {
        String filePath = null;

        String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
                "MEDIA_ID", mediaId);
        System.out.println(requestUrl);
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            if (!savePath.endsWith("/")) {
                savePath = savePath + "/";
            }

            String fileExt = CommonUtil.getFileExt(conn
                    .getHeaderField("Content-Type"));

            filePath = savePath + mediaId + fileExt;

            BufferedInputStream bis = new BufferedInputStream(
                    conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();

            conn.disconnect();
            log.info("下载媒体文件成功，filePath=" + filePath);
        } catch (Exception e) {
            filePath = null;
            log.error("下载媒体文件失败：{}", e);
        }
        return filePath;
    }

    public static AccessToken getAccessToken(String appId, String appSecret) {
        AccessToken wat = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("APPSECRET", appSecret);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {
                wat = new AccessToken();
                wat.setToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取凭证失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode),
                        errorMsg);
            }
        }
        return wat;
    }

    public static WeixinUserInfo getWeixinUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
                "OPENID", openId);

        JSONObject jsonObject =
                CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                weixinUserInfo = new WeixinUserInfo();

                weixinUserInfo.setOpenId(jsonObject.getString("openid"));

                weixinUserInfo.setNickname(jsonObject.getString("nickname"));

                weixinUserInfo.setSex(jsonObject.getInt("sex"));

                weixinUserInfo.setCountry(jsonObject.getString("country"));

                weixinUserInfo.setProvince(jsonObject.getString("province"));

                weixinUserInfo.setCity(jsonObject.getString("city"));

                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                weixinUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", Integer.valueOf(errorCode), errorMsg);
            }
        }
        return weixinUserInfo;
    }
}