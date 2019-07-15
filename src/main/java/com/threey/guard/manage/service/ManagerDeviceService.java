package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.base.util.ExcelUtil;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.clientmsg.dao.SendMsgDao;
import com.threey.guard.clientmsg.domain.MsgBean;
import com.threey.guard.manage.dao.ManagerDeviceDao;
import com.threey.guard.manage.domain.Device;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerDeviceService extends CrudService<Device> {

    private Logger logger = Logger.getLogger(ManagerDeviceService.class);



    @Autowired
    ManagerDeviceDao managerDeviceDao;
    @Autowired
    private SendMsgDao sendMsgDao;


    public Device getDeviceByUnit(String unitId){
        return this.managerDeviceDao.getDeviceByUnit(unitId);
    }

    public Device getDeviceById(String deviceId) {
        return managerDeviceDao.getDeviceById(deviceId);
    }

    public List<Device> getDeviceByResidentail(String residentail){
        return  this.managerDeviceDao.getDeviceByResidentail(residentail);
    }

    /**
     * 绑定单元
     * @param deviceId
     * @param unitId
     */
    public void bind(String deviceId,String unitId){
        this.managerDeviceDao.bind(deviceId ,unitId);
    }

    /**
     * 解绑
     * @param deviceId
     * @param unitId
     */
    public void unBind(String deviceId,String unitId){
        this.managerDeviceDao.unBind(deviceId, unitId);
    }

    public List<Device> listUnBind(Map<String,Object> obj, int page, int pageSize) {
        return this.managerDeviceDao.listUnBind(obj, page, pageSize);
    }

    public int countUnbind(Map<String,Object> obj){
        return this.managerDeviceDao.countUnbind(obj);
    }

    public Map<String,Object> deviceOpt(String userId,String flag,String[] deviceIds){
        List<MsgBean> beans = new ArrayList<>();
        for (String deviceId:deviceIds) {
            MsgBean msgBean = new MsgBean();
            msgBean.setMsgType(MsgBean.TYPE_OPEN);
            if ("1".equals(flag)){//更新资源
                msgBean.setMsgType(MsgBean.TYPE_ADS);
            }else if("2".equals(flag)){//更新用户信息
                msgBean.setMsgType(MsgBean.TYPE_USER);
            }else if("3".equals(flag)){//更新客户端
                msgBean.setMsgType(MsgBean.TYPE_APP);
            }
            msgBean.setDeviceId(deviceId);
            msgBean.setOptUser(userId);
            beans.add(msgBean);
        }
        this.sendMsgDao.insertBatch(beans);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @Override
    protected CrudDAO getDao() {
        return managerDeviceDao;
    }

    public void batchInsert(List<Map<String,Object>> list){
        this.managerDeviceDao.batchInsertDevice(list);
        this.managerDeviceDao.batchDeviceTemp();
    }

    public void batchDeviceTemp(){
        this.managerDeviceDao.batchDeviceTemp();
    }

    public List<Device> getDeviceList(Map<String,Object> obj) {
        return this.managerDeviceDao.getDeviceList(obj);
    }

    public void export(HttpServletRequest request,List<Device> devices, HttpServletResponse response) {

        //excel标题
        String[] title = {"设备编号","设备类型","所属小区","所属楼栋","所属单元","设备状态","在线状态","分机号","设备手机号"};

        //excel文件名
        String fileName = "设备信息.xls";


        //sheet名
        String sheetName = "设备信息";

        String[][] content=new String[devices.size()][title.length];
        for (int i = 0; i < devices.size(); i++) {
            content[i][0] = devices.get(i).getDeviceId();
            if(devices.get(i).getDeviceType()==1){
                content[i][1] = "类型I";
            }else {
                content[i][1] = "类型II";
            }
            content[i][2] = devices.get(i).getResidentail();
            content[i][3] = devices.get(i).getBuild();
            content[i][4] = devices.get(i).getUnit();
            if(devices.get(i).getDeviceStatus()==1){
                content[i][5] = "停用";
            }else if(devices.get(i).getDeviceStatus()==2){
                content[i][5] = "报修";
            }else if(devices.get(i).getDeviceStatus()==3){
                content[i][5] = "废弃";
            }else{
                content[i][5] = "可用";
            }
            if(devices.get(i).isOnline()){
                content[i][6] = "在线";
            }else {
                content[i][6] = "离线";
            }
            content[i][7] = devices.get(i).getDevicePhone();
            content[i][8] = devices.get(i).getDeviceTel();


        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            ExcelUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
