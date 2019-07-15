package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.service.LoginService;
import com.threey.guard.base.util.MD5Util;
import com.threey.guard.manage.domain.CompanyInfo;
import com.threey.guard.manage.domain.ManagerInfo;
import com.threey.guard.manage.service.ManagerCompanyInfoService;
import com.threey.guard.manage.service.ManagerPersonInfoService;
import com.threey.guard.manage.service.ManagerPersonService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/personCenter")
public class ManagerCompanyInfoController {

    private Logger logger = Logger.getLogger(ManagerCompanyInfoController.class);

    @Autowired
    ManagerCompanyInfoService managerCompanyService;
    @Autowired
    ManagerPersonInfoService managerPersonInfoService;
    @Autowired
    LoginService loginService;

    @RequestMapping("/toCompanyInfo.shtml")
    public String toAdd(HttpServletRequest request){
        return "manage/personCenter/companyIndex";
    }

    @RequestMapping(value = "/companyPage.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<CompanyInfo> companyPage(DataTable.Req p , HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",request.getParameter("p_name"));
        List<CompanyInfo> persons = managerCompanyService.list(map,p.getPage()-1,Integer.MAX_VALUE);
        return new DataTable.Resp<CompanyInfo>(persons,persons.size(),0);

    }

    @RequestMapping("/companyToAdd.shtml")
    public String companyToAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if(id !=null){
            CompanyInfo company = this.managerCompanyService.getOne(id);
            request.setAttribute("company",company);
            request.setAttribute("opt","edit");
        }
        return "manage/personCenter/companyAdd";
    }

    @RequestMapping(value = "/save.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveOrUpdate(CompanyInfo company){
        if (StringUtils.isNotEmpty(company.getId())){
            this.managerCompanyService.update(company);

        }else{
            this.managerCompanyService.add(company);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/companyDel.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request){
        String id = request.getParameter("id");
        this.managerCompanyService.del(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/toManagerPersonInfo.shtml")
    public String toManagerPersonInfo(HttpServletRequest request){
        ManagerUser loginUser = loginService.getLoginManagerUser(request);
        String id = loginUser.getMid();
        ManagerInfo company = this.managerPersonInfoService.getOne(id);
        request.setAttribute("manager",this.managerPersonInfoService.initRoleName(company));
        request.setAttribute("opt","edit");
        return "manage/personCenter/managerAdd";
    }

    @RequestMapping(value = "/saveManager.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveManager(ManagerInfo company){
        if (StringUtils.isNotEmpty(company.getId())){
            this.managerPersonInfoService.update(company);

        }else{
            this.managerPersonInfoService.add(company);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/toUpdatePw.shtml")
    public String toUpdatePw(HttpServletRequest request){
        ManagerUser loginUser = loginService.getLoginManagerUser(request);
        String id = loginUser.getMid();
        ManagerInfo company = this.managerPersonInfoService.getOne(id);
        request.setAttribute("manager",company);
        request.setAttribute("opt","edit");
        return "manage/personCenter/passwordUpdate";
    }

    @RequestMapping(value = "/updatePw.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> updatePw(HttpServletRequest request){
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("id",request.getParameter("id"));
        String pwd = request.getParameter("pwd");
        if (StringUtils.isNotEmpty(pwd)){
            pwd = MD5Util.MD5(pwd);
        }
        rmap.put("pwd",pwd);
        this.managerPersonInfoService.updatePW(rmap);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }
}
