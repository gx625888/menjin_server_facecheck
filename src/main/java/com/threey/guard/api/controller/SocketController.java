package com.threey.guard.api.controller;

import com.threey.guard.api.domain.ApiResult;
import com.threey.guard.api.domain.ClientLink;
import com.threey.guard.api.domain.NettyBaseInfo;
import com.threey.guard.api.service.AsyncServcie;
import com.threey.guard.base.util.GETProperties;
import com.threey.guard.base.util.JsonUtil;
import com.threey.guard.clientmsg.MsgRouteFact;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.domain.Msg;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class SocketController {



    @RequestMapping("/api/socket/getserver.do")
    @ResponseBody
    public ApiResult<ClientLink> getSockerServer(){
        //TODO 优化算法返回最优sockerServer
        ApiResult<ClientLink> nb = new ApiResult<>();
        nb.setCode(0);
        nb.setMsg("success");
        String[] slaves = GETProperties.readValue("slave.servers").split(",");
        String[] ports = GETProperties.readValue("slave.ports").split(",");
        int index = new Random().nextInt(slaves.length);
        //随机分配算法
        ClientLink cl = new ClientLink();
        cl.setIp(slaves[index]);
        cl.setPort(ports[index]);
        nb.setData(cl);


        return nb;
    }



    @RequestMapping("/api/socket/test.do")
    @ResponseBody
    public Object test(HttpServletRequest req){
        String test = req.getParameter("test");
        if ("1".equals(test)){
            return MsgRouteFact.instance.getInstance().info();
        }

        Msg msg = new Msg();
        msg.setCmd(Integer.parseInt(req.getParameter("cmd")));
        msg.setDest(req.getParameter("dest"));
        msg.setData(req.getParameter("data"));
        int status = MsgUtil.send(msg);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",status);
        map.put("msg","成功");
        return map;
    }


    public static void main(String[] args) {
        String msg = "{\"code\":0,\"msg\":\"success\",\"data\":\"123123123123\"}";
        ApiResult<Object> ar = (ApiResult<Object>) JsonUtil.getObject(msg,ApiResult.class);
        String s = (String) ar.getData();
        System.out.println(s);
        ClientLink cl = (ClientLink) JsonUtil.getObject(JsonUtil.getJsonString(ar.getData()),ClientLink.class);
        System.out.println(ar);


    }
}
