package com.threey.guard.base.controller;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
	
    protected void success(HttpServletRequest request, String msg, String url) {
    	request.setAttribute("msg", msg);
    	request.setAttribute("fowardPage", url);
    }
    
    protected void message(HttpServletRequest request,String message) {
    	request.setAttribute("msg", message);
    }
    
    protected void error(HttpServletRequest request,String message) {
    	request.setAttribute("msg", message);
    }
    
    protected void error(HttpServletRequest request,Exception e) {
    	request.setAttribute("msg", e);
    }
    
    
    
    private static final String SUCCESS_CODE = "0" ;  //接口调用成功返回码
	private static final String ERROR_CODE = "1"; //接口调用失败返回码
	
	 
	protected void response_success(HttpServletResponse res,Object data){
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("code", SUCCESS_CODE);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			resMap.put("data",data); 
		} catch (IOException e) {
			
		}
		out.print(JSONObject.fromObject(resMap));
	}
	
	
	protected void response_error(HttpServletResponse res,String errorMsg){
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("code", ERROR_CODE);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			resMap.put("errorMsg",errorMsg); 
		} catch (IOException e) {
			
		}
		out.print(JSONObject.fromObject(resMap));
	}
}
