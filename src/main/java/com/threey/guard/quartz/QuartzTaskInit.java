package com.threey.guard.quartz;


import com.threey.guard.quartz.starter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * quartz定时任务初始化
 */
@Component
public class QuartzTaskInit  implements  ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TestStarter testStarter;

    @Autowired
    private SendMsgStarter sendMsgStarter;

    @Autowired
    private PwdInvalidStarter pwdInvalidStarter;

    @Autowired
    private DeviceStatusStarter deviceStatusStarter;

    @Autowired
    private AccessTokenFreshStarter accessTokenFreshStarter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent ev) {
        // 防止重复执行。
        if (ev.getApplicationContext().getParent() == null) {
            //testStarter.init();
            sendMsgStarter.init();
           // pwdInvalidStarter.init();
            deviceStatusStarter.init();
            accessTokenFreshStarter.init();
        }
    }
}
