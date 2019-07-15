package com.threey.guard.wechat.service;

import com.threey.guard.api.dao.ClientApiDao;
import com.threey.guard.base.util.DynamicPwd;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.dao.SendMsgDao;
import com.threey.guard.clientmsg.domain.MsgBean;
import com.threey.guard.quartz.util.MsgBeanUtil;
import com.threey.guard.wechat.dao.ManagerWxBandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的房间
 */
@Service
public class MyHourseService {

    @Autowired
    private ClientApiDao clientApiDao;
    @Autowired
    private SendMsgDao sendMsgDao;
    @Autowired
    private ManagerWxBandDao managerWxBandDao;

    private final int PWD_LENGTH = 6;

    /**
     * 生成临时密码
     * @param deviceId
     * @param userId
     * @return 为空时生成失败
     */
    public String createPwd(String deviceId,String userId){
        //TODO 可优化算法,返回不重复的code
        List<String> pwds = this.clientApiDao.getPwd(deviceId);//当前生效的密码
        String code = createCode(PWD_LENGTH);
        while(pwds.contains(code)){//与当前生效的密码不重复
            code = createCode(PWD_LENGTH);
        }
        Map<String,String> map = new HashMap<>();
        map.put("deviceId",deviceId);
        map.put("pwd",code);
        map.put("userId",userId);
        this.clientApiDao.insertPwd(map);
        return code;
    }
    public String createPwdDynamic(String deviceId,String userId){
        List<String> pwds = this.clientApiDao.getPwd(deviceId);//当前生效的密码
        String code = DynamicPwd.createPwd(deviceId);
        if(!pwds.contains(code)){//与当前生效的密码不重复
            Map<String,String> map = new HashMap<>();
            map.put("deviceId",deviceId);
            map.put("pwd",code);
            map.put("userId",userId);
            this.clientApiDao.insertPwd(map);
        }
        return code;
    }

    public int openDoor(String houseNo, String houseId, String deviceId,
                        String userId,String openId,String openType) {
        MsgBean msgBean = new MsgBean();
        if ("1".equals(openType)){//左开门
            msgBean.setMsgType(MsgBean.TYPE_OPEN_L);
        }else if ("2".equals(openType)){//右开门
            msgBean.setMsgType(MsgBean.TYPE_OPEN_R);
        }else{//全开门
            msgBean.setMsgType(MsgBean.TYPE_OPEN);
        }

        msgBean.setStatus(MsgBean.STATUS_SUCCESS);
        msgBean.setDeviceId(deviceId);
        msgBean.setOptUser("wx:" + houseNo + ":" + houseId + ":" + userId+ ":" + openId);
        int sendStatus = MsgUtil.send(MsgBeanUtil.parseMsg(msgBean));
        this.sendMsgDao.insert(msgBean);

        return sendStatus;
    }


    private String createCode(int length){
         return Math.abs(Math.round(Math.random()*(Math.pow(10, length-1)*9-1)+(Math.pow(10, length-1))))+"";
    }

    public void unbind(String wxOpenId,String houseId){
        try {
            this.managerWxBandDao.unbind(wxOpenId, houseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
