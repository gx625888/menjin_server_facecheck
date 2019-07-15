package com.threey.guard.clientmsg;


import com.threey.guard.base.util.GETProperties;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitLinstener implements ApplicationListener<ContextRefreshedEvent> {


    private static final Logger logger = Logger.getLogger(InitLinstener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent ev) {
        // 防止重复执行。
        if (ev.getApplicationContext().getParent() == null) {
            if(Boolean.parseBoolean(GETProperties.readValue("slave.link"))){//配置文件配置启动
                initSocketSlave();
            }
        }
    }

    private void initSocketSlave(){
        logger.info("初始化soket slave....");
        String[] slaves = GETProperties.readValue("slave.servers").split(",");
        String[] ports = GETProperties.readValue("slave.ports").split(",");
        if (null==slaves||null==ports||slaves.length!=ports.length){
            logger.info("初始化socket slave 失败 参数异常");
        }
        for (int i=0;i<slaves.length;i++){
            new NettyClient(slaves[i],Integer.parseInt(ports[i])).start();
        }
        MsgUtil.init();
        MsgRouteFact.instance.getInstance().initHartBeat();

    }
}
