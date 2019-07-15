package com.threey.guard.base.service;


import com.threey.guard.base.dao.LogAuditDAO;
import com.threey.guard.base.domain.LogAudit;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Service
public class LogAuditService {

    public final static int LOG_TYPE_OPENDOOR=2;

    @Autowired
    private LogAuditDAO dao;

    public List<LogAudit> getList(String key,String sDate,String eDate, int pageNumber,int pageSize){
        return this.dao.getList(key,sDate,eDate, pageNumber, pageSize);
    }

    public List<LogAudit> getLogList(String key,String sDate,String eDate){
        return this.dao.getLogList(key,sDate,eDate);
    }

    public int getCount(String key,String sDate,String eDate,String mid){
        return this.dao.getCount(key,sDate,eDate, mid);
    }

    /**
     * 登录日志记录
     * @param mu 参数信息
     * @param u 登录成功用户信息
     */
    public void loginLog(ManagerUser mu , ManagerUser u, HttpServletRequest request){
        LogAudit logAudit = new LogAudit();
        logAudit.setIpInfo(getIpAddr(request));
        logAudit.setOptTypeInt(1);
        logAudit.setOptType("登录");

        if (u==null){//登录失败
            logAudit.setUserId(mu.getUserId());
            logAudit.setInfo("登录失败,账号密码错误!Mid="+mu.getMid()+"&账号="+mu.getUserId()+"&密码="+mu.getPassword());
            logAudit.setMid(mu.getMid());

        }else {
            logAudit.setUserId(u.getUserId());
            logAudit.setUserName(u.getName());
            logAudit.setInfo("登录成功!Mid="+u.getMid());
            logAudit.setMid(u.getMid());
        }
        this.insert(logAudit);
    }

    public void log(int type ,String info,ManagerUser u,HttpServletRequest request){
        LogAudit logAudit = new LogAudit();
        logAudit.setUserName(u.getName());
        logAudit.setIpInfo(getIpAddr(request));
        logAudit.setOptTypeInt(type);
        logAudit.setOptType(getTypeStr(type));
        logAudit.setUserId(u.getUserId());
        logAudit.setMid(u.getMid());
        logAudit.setInfo(info);
        this.insert(logAudit);
    }

    private String getTypeStr(int type ){
        switch (type){
            case 1: return "登录";
            case LOG_TYPE_OPENDOOR: return "管理员开门";
            default:return "";
        }
    }

    public void insert(LogAudit log){
        this.dao.insert(log);
    }

    public void export(HttpServletRequest request, List<LogAudit> residentails, HttpServletResponse response) {

        //excel标题
        String[] title = {"操作人","操作人账号","操作类型","操作时间","ip地址","描述"};

        //excel文件名
        String fileName = "操作日志信息.xls";


        //sheet名
        String sheetName = "操作日志信息";

        String[][] content=new String[residentails.size()][title.length];
        for (int i = 0; i < residentails.size(); i++) {
            content[i][0] = residentails.get(i).getUserName();
            content[i][1] = residentails.get(i).getUserId();
            content[i][2] = residentails.get(i).getOptType();
            content[i][3] = residentails.get(i).getOptTime();
            content[i][4] = residentails.get(i).getIpInfo();
            content[i][5] = residentails.get(i).getInfo();
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

    private  static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
