package com.threey.guard.api.controller;

import com.threey.guard.api.service.AsyncServcie;
import com.threey.guard.api.service.ClientApiService;
import com.threey.guard.api.service.TelCallBackService;
import com.threey.guard.tel.domain.CallBackResultXmlEntity;
import com.threey.guard.tel.domain.CallBackXmlEntity;
import com.threey.guard.tel.util.TelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/api/tel")
public class TelCallBackController {

    @Autowired
    private TelCallBackService service;

    @Autowired
    private AsyncServcie asyncServcie;




    @RequestMapping(value = "/callback.do" ,consumes = "application/xml",produces ="application/xml",method = RequestMethod.POST)
    public void callBack(HttpServletRequest request , HttpServletResponse response, @RequestBody CallBackXmlEntity entity){

        System.out.println(entity);
        this.service.delCallBack(entity);
        String re = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <response>" +
                "<retcode>00000</retcode>" +
                " </response>";
        response.setContentType("application/xml");
        try {
            response.getWriter().write(re);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/callbackResult.do" ,consumes = "application/xml",produces ="application/xml",method = RequestMethod.POST)
    public void callbackResult(HttpServletRequest request , HttpServletResponse response, @RequestBody CallBackResultXmlEntity entity){

        System.out.println(entity);
        this.service.delCallBackResult(entity);

        String re = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <response>" +
                "<retcode>00000</retcode>" +
                " </response>";
        response.setContentType("application/xml");
        try {
            response.getWriter().write(re);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/test.do" )
    public void test(HttpServletRequest request , HttpServletResponse response){

//        String from = request.getParameter("from");
//        String to = request.getParameter("to");
//        StringBuilder sb = new StringBuilder();
//
//        try {
//            Map<String,String>  callMap = TelUtil.call(from,to);
//            asyncServcie.waitAndCall(null,null,1,null);
//            sb.append("resp:").append(callMap.get("ret")).append("\n").append("callId:").append(callMap.get("callId"));
//        } catch (Exception e) {
//            e.printStackTrace();
//           sb.append("呼叫异常!!!!!!\n");
//        }
//        try {
//            response.getWriter().write(sb.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        asyncServcie.sendWxMessage("10:a4:be:68:3a:14","101","/menjin/openRecord/jpg/20190109/openRecord6403.jpg");
        asyncServcie.sendWxMessage("10:a4:be:5d:62:30","808","/menjin/openRecord/jpg/20190109/openRecord6403.jpg");
    }
}
