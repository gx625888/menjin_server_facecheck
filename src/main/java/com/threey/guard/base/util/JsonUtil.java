package com.threey.guard.base.util;

import com.google.gson.Gson;

public class JsonUtil {
    private static  Gson gson = new Gson();


    public static Object getObject(String jsonString,Class clazz){

        return gson.fromJson(jsonString,clazz);
    }
    public static String getJsonString(Object obj){
        return gson.toJson(obj);
    }
}
