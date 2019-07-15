package com.threey.guard.api.service;

import com.threey.guard.api.domain.Ads;
import com.threey.guard.api.domain.ApiResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端接口
 */
@Service
public class ApiService {

    private static final Logger logger = Logger.getLogger(ApiService.class);

    private final static int RESULT_SUCCESS = 0;

   public  ApiResult<String> updateInfo(String deviceNo){
        ApiResult<String>  re = new ApiResult<>();
        re.setCode(RESULT_SUCCESS);
        re.setMsg("success");
        re.setData("http://testurl.com/1.apk");//TODO  查找更新url
         return re;
    }




    public ApiResult<List<Ads>>  getAdsInfo(String deviceNo){
        ApiResult<List<Ads>>  re = new ApiResult<>();
        re.setCode(RESULT_SUCCESS);
        re.setMsg("success");

        Ads ads1 = new Ads(Ads.TYPE_IMG,"");
        Ads ads2 = new Ads(Ads.TYPE_VIDEO,"");

        List<Ads> list = new ArrayList<>();
        list.add(ads1);
        list.add(ads2);

        re.setData(list);



       return re;
    }






}
