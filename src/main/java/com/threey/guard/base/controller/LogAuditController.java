package com.threey.guard.base.controller;



import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.LogAudit;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.service.LogAuditService;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/log/*")
public class LogAuditController {

    @Autowired
    private LogAuditService service;

    @RequestMapping("/logList.shtml")
    public String list(HttpServletRequest request,Model model){

//        ManagerUser user = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
//        if (user==null){
//            return "login";
//        }
//        int pageNumber =0 ;
//        String p=request.getParameter("p");
//        if(null!=p && !p.equals("")){
//            pageNumber=Integer.parseInt(p);
//        }
//
//        String mid = null;
//        if (!user.getIsRootUser().equals(Constants.ManagerUser.ROOT_USER_1)){
//            mid = user.getMid();
//        }
//        List<LogAudit> list=service.getList(null,pageNumber,mid);
//        int count=service.getCount(null,mid);

        return "base/log/logIndex";
    }

    @RequestMapping("/page.shtml")
    @ResponseBody
    public DataTable.Resp<LogAudit> page(DataTable.Req p , HttpServletRequest request){
        LogAudit role = new LogAudit();
//        role.setName(request.getParameter("name"));
        String sDate = request.getParameter("p_sDate");
        String eDate = request.getParameter("p_eDate");
        if(!StringUtil.isEmpty(sDate)){
            sDate = sDate+" 00:00:00";
        }
        if(!StringUtil.isEmpty(eDate)){
            eDate = eDate+" 23:59:59";
        }
        List<LogAudit>  roles = this.service.getList(request.getParameter("type"),sDate,eDate,p.getPage()-1,p.getLimit());
        int count = this.service.getCount(request.getParameter("type"),sDate,eDate,null);
        return new DataTable.Resp<>(roles,count,0);
    }

    @RequestMapping("export.shtml")
    @ResponseBody
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String sDate = request.getParameter("p_sDate");
        String eDate = request.getParameter("p_eDate");
        String type = request.getParameter("type");
        if(!StringUtil.isEmpty(sDate)){
            sDate = sDate+" 00:00:00";
        }
        if(!StringUtil.isEmpty(eDate)){
            eDate = eDate+" 23:59:59";
        }
        List<LogAudit>  roles = this.service.getLogList(request.getParameter("type"),sDate,eDate);

        this.service.export(request, roles,response);
    }
}
