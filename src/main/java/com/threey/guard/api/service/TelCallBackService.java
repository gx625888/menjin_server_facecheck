package com.threey.guard.api.service;

import com.threey.guard.api.dao.ClientApiDao;
import com.threey.guard.api.domain.TelRecord;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.dao.SendMsgDao;
import com.threey.guard.clientmsg.domain.MsgBean;
import com.threey.guard.quartz.util.MsgBeanUtil;
import com.threey.guard.tel.domain.CallBackResultXmlEntity;
import com.threey.guard.tel.domain.CallBackXmlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelCallBackService {

    @Autowired
    private SendMsgDao sendMsgDao;
    @Autowired
    private ClientApiDao clientApiDao;
    @Autowired
    private ClientApiService clientApiService;



    //CallBackXmlEntity{appId='8bcb53ad8dee699b7a2cddbf99b180d7',
    // callId='api001000229ec1541663927417p0N8z',
    // accountSid='26b6da389b7050e5bdea31aa0cd67a21',
    // caller='02566695505', called='15236192196',
    // keyFeedback='*', type='50', number='null',
    // workNumber='null', userData='1234'}

    public void delCallBack(CallBackXmlEntity entity){
        TelRecord record = this.clientApiDao.getCallRecord(entity.getCallId());
        if (null==record){
            return ;
        }
        if ("*".equals(entity.getKeyFeedback())){
            int status = open(record.getDeviceId(),record.getPhone(),record.getHouse(),record.getOpenType());
            if (status == MsgBean.STATUS_SUCCESS){
                record.setIsOpen(MsgBean.STATUS_SUCCESS);
                record.setCallBackInfo(entity.toString());
            }
        }else{
            record.setCallBackInfo(entity.toString());
        }
        this.clientApiDao.updateCallRecord(record);
    }

    public void delCallBackResult(CallBackResultXmlEntity entity){

        //分机(客户端)主动挂断时 更新call_num为100 以后不再发起呼叫
        if("0".equals(entity.getCallType())&&"3".equals(entity.getType())&&entity.getCalled().length()==4){
            //TelRecord record = this.clientApiDao.getCallRecord(entity.getCallId());
           // record.setCallNum(100);
            HashMap<String,String> param = new HashMap<>();
            param.put("callId",entity.getCallId());
            param.put("callNum","100");
            this.clientApiDao.updateCallRecordById(param);
            return ;
        }


        if("0".equals(entity.getCallType())&&"1".equals(entity.getType())&&entity.getCalled().length()>5){//只处理呼叫失败且是双向回拨的信息别的不处理
            TelRecord record = this.clientApiDao.getCallRecord(entity.getCallId());
            if (null==record){
                return ;
            }

            if (record.getCallNum()>1){//不是第一次呼叫了已经 不处理
                return ;
            }
            //以上条件都没满足,发起第二次呼叫
            try {//设备挂断需要时间
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("device",record.getDeviceId());
            map.put("house",record.getHouse());
            this.clientApiService.deviceCall(map,2);//第二次呼叫


        }

    }

    private int open(String deviceId,String phone,String house,int openType){
        MsgBean msgBean = new MsgBean();
        //msgBean.setMsgType(MsgBean.TYPE_OPEN);

        if (openType==1){//左开门
            msgBean.setMsgType(MsgBean.TYPE_OPEN_L);
        }else if (openType==2){//右开门
            msgBean.setMsgType(MsgBean.TYPE_OPEN_R);
        }else{//全开门
            msgBean.setMsgType(MsgBean.TYPE_OPEN);
        }


        msgBean.setStatus(MsgBean.STATUS_SUCCESS);
        msgBean.setDeviceId(deviceId);
        msgBean.setOptUser("call:"+phone+":"+house);
        int sendStatus = MsgUtil.send(MsgBeanUtil.parseMsg(msgBean));
        this.sendMsgDao.insert(msgBean);

        return sendStatus;
    }
}
