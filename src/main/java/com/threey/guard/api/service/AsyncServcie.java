package com.threey.guard.api.service;

import com.threey.guard.base.util.GETProperties;
import com.threey.guard.wechat.dao.ManagerWxBandDao;
import com.threey.guard.wechat.domain.WeChatPropertis;
import com.threey.guard.wechat.domain.WxBand;
import com.threey.guard.wechat.util.AppTokenUtil;
import com.threey.guard.wechat.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 异步服务
 */

@Service
public class AsyncServcie {

    @Autowired
    private ManagerWxBandDao managerWxBandDao;



    /**
     * 等待 如果电话没有接通则取消呼叫发起新呼叫
     * @param deviceNo
     * @param house
     * @param count
     * @param callId
     */
    @Async
    public void waitAndCall(String deviceNo,String house,int count,String callId){
        if (count>3){
            return ;
        }
        count +=1;
        try {
            Thread.sleep(1000*15);
            System.out.println(Thread.currentThread().getName());
            System.out.println("call----->"+count);
            waitAndCall(null,null,count,null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送呼叫消息到微信
     * @param deviceNo
     * @param houseNo
     * @param pic
     */
    @Async
    public void sendWxMessage(String deviceNo,String houseNo,String pic){

        pic = GETProperties.readValue("wechat.callpath")+pic;

        List<WxBand>  list = this.managerWxBandDao.getWxBandByHouseDevice(houseNo,deviceNo);
        if (CollectionUtils.isNotEmpty(list)){

            String company = list.get(0).getCompany();
            WeChatPropertis propertis = this.managerWxBandDao.getWeChatPropertis(company);
            if (null==propertis){
                propertis = this.managerWxBandDao.getWeChatPropertis("0");//默认微信配置
            }

            String resName = this.managerWxBandDao.getResNameByDevice(deviceNo);
            for (WxBand wb:list) {
                AppTokenUtil.checkAndReflesh(propertis);//检查刷新微信token
                if (StringUtils.isNotEmpty(propertis.getToken())){//token获取成功发送消息
                    CommonUtil.sendMessage(propertis.getToken(),buildTempletMsg(wb.getWxId(),pic,
                            propertis.getTempletId(),"智慧门禁",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                            houseNo,resName,"请注意查看呼叫信息!"));
                }
            }
        }
    }

    private String buildTempletMsg(String wxId,String pic,String tempId,String first,String keyword1,String keyword2,String keyword3,String remark){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"touser\":\""+wxId+"\",\"template_id\":\""+tempId+"\",\"url\":\""+pic+"\",\"data\":{\"first\":{\"value\":\""+first+"\"},\"keyword1\":{\"value\":\""+keyword1+"\"},\"keyword2\":{\"value\":\""+keyword2+"\"},\"keyword3\":{\"value\":\""+keyword3+"\"},\"remark\":{\"value\":\""+remark+"\"}}}");
        return buffer.toString();
    }
}
