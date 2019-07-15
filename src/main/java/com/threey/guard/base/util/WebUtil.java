package com.threey.guard.base.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information  : 邹建松
 * @Project                 : 
 * @JDK version used        : jdk1.6
 * @Comments                : 版权所有--邹建松:
 * @Version                 : 1.0
 * @Author                  : 邹建松
 * @Date                    : 2014-07-22
 * @Modification history    : 
 **/
public class WebUtil {
	
	final static Logger log = LoggerFactory.getLogger(WebUtil.class);
	
	private static String logPrefixError = "WebUtil操作失败:";
	
	public static final String SUCCESS = "success";

	public static final String FAIL = "fail";

	public static final String ERROR = "error";
	
	/**
	 * 返回boolean
	 * @param  jsonObj  用户键值对权限对象
	 * @param  objArr   需要被判断的权限对象key数字
	 * @return void  	  无返回对象
	 * @author 邹建松 2014-08-13
	 */
	public static boolean judgeAuth(JSONObject jsonObj, Object[] objArr){
		boolean re = false;
		for(int i=0;i<objArr.length;i++){
			if(jsonObj.containsKey(objArr[i])){
				re = true;
				return re;
			}
		}
		return re;
	}
	
	/**
	 * 返回json字符串
	 * @param  response  response对象
	 * @param  reStr     被写入对象
	 * @return void  	  无返回对象
	 * @author 邹建松 2014-07-22
	 */
	public static void toJson(HttpServletResponse response, String reStr){
		response.setContentType("application/json;charset=UTF-8");
		try{
			response.getWriter().print(reStr);
		}catch(Exception e){
			log.info(logPrefixError+e);
			e.printStackTrace();
		}
	}
	
	/**
     * 返回不同contentType字符串
     * @param  response  response对象
     * @param  reStr     被写入对象
     * @return void       无返回对象
     * @author 邹建松 2014-11-26
     */
    public static void toPrint(HttpServletResponse response, String reStr, String contentType){
        response.setContentType(contentType+";charset=UTF-8");
        try{
            response.getWriter().print(reStr);
        }catch(Exception e){
            log.info(logPrefixError+e);
            e.printStackTrace();
        }
    }
    
    /**
	 * 通过request.getHeader("X-Requested-With")判断是否是ajax请求
	 * @param request 请求
	 * @return 是否ajax请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		if (request == null){
			return false;
		}
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}
}
