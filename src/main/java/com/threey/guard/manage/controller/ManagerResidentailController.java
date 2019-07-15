package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.service.CommonService;
import com.threey.guard.base.util.Constants;
import com.threey.guard.clientmsg.MsgRouteFact;
import com.threey.guard.manage.domain.Community;
import com.threey.guard.manage.domain.Residentail;
import com.threey.guard.manage.service.ManagerResidentailService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/residentail")
public class ManagerResidentailController {
    private Logger logger = Logger.getLogger(ManagerResidentailController.class);
    @Autowired
    private ManagerResidentailService managerResidentailService;
    @Autowired
    private CommonService commonService;

    @RequestMapping("/toResidentailList.shtml")
    public String toManagerUserList(HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Community community = new Community();
        if(loginUser.getManagerType() != 0){
            community.setCreateUserCompany(loginUser.getManagerCompany());
            if(loginUser.getManagerType() == 2){
                community.setCreateUserProvince(loginUser.getManagerProvince());
            }
            if(loginUser.getManagerType() == 3){
                community.setCreateUserCity(loginUser.getManagerCity());
            }
            if(loginUser.getManagerType() == 4){
                community.setCreateUserResidentail(loginUser.getManagerResidentail());
            }
        }
        List<Community> communities = this.managerResidentailService.getCommunity(community);
        request.setAttribute("communities",communities);
        return "manage/residentail/residentailIndex";
    }

    @RequestMapping(value = "/page.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Residentail> page(DataTable.Req p , HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Residentail residentail = new Residentail();
        residentail.setCommunity(request.getParameter("p_community"));
        if("全部".equals(request.getParameter("p_name"))){
            residentail.setName("");
        }else {
            residentail.setName(request.getParameter("p_name"));
        }
        if(loginUser.getManagerType() != 0){
            residentail.setCreateUserCompany(loginUser.getManagerCompany());
            if(loginUser.getManagerType() == 2){
                residentail.setCreateUserProvince(loginUser.getManagerProvince());
            }
            if(loginUser.getManagerType() == 3){
                residentail.setCreateUserCity(loginUser.getManagerCity());
            }
            if(loginUser.getManagerType() == 4){
                residentail.setCreateUserResidentail(loginUser.getManagerResidentail());
            }
        }
        List<Residentail> residentails = managerResidentailService.list(residentail,p.getPage()-1,p.getLimit());
        int count = this.managerResidentailService.count(residentail);
        return new DataTable.Resp<Residentail>(residentails,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Residentail residentail = this.managerResidentailService.getOne(id);
            request.setAttribute("residentail",residentail);
            request.setAttribute("opt","edit");
        }
        return "manage/residentail/residentailAdd";
    }

    @RequestMapping(value = "/save.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveOrUpdate(Residentail residentail,HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        if (StringUtils.isNotEmpty(residentail.getId())){
            this.managerResidentailService.update(residentail);
        }else{
            String rId = "xq"+commonService.getNextVal("mj_residentail");
            residentail.setId(rId);
            residentail.setCreateUser(loginUser.getMid());
            this.managerResidentailService.add(residentail);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/del.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request){
        String id = request.getParameter("id");
        Map<String,Object> map = new HashMap<>();
        if (this.managerResidentailService.checkHasChild(id)){//有下级组织禁止删除
            map.put("ret",-1);
            map.put("msg","需要先删除小区楼栋信息!");
            return map;
        }

        this.managerResidentailService.del(id);

        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/queryCommunity.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<Community> queryCommunity(HttpServletRequest request){
        Community community = new Community();
        return this.managerResidentailService.getCommunity(community);
    }

    @RequestMapping(value = "/queryResidentailByCommunity.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<Residentail> queryResidentailByCommunity(HttpServletRequest request){
        String id = request.getParameter("community");
        return this.managerResidentailService.getResidentailListByCommunity(id);
    }

    @RequestMapping("export.shtml")
    @ResponseBody
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception {
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Residentail residentail = new Residentail();
        residentail.setCommunity(request.getParameter("p_community"));
        String p_name = request.getParameter("p_name");
        p_name = URLDecoder.decode(p_name,"UTF-8");
        if("全部".equals(p_name)){
            residentail.setName("");
        }else {
            residentail.setName(p_name);
        }
        if(loginUser.getManagerType() != 0){
            residentail.setCreateUserCompany(loginUser.getManagerCompany());
            if(loginUser.getManagerType() == 2){
                residentail.setCreateUserProvince(loginUser.getManagerProvince());
            }
            if(loginUser.getManagerType() == 3){
                residentail.setCreateUserCity(loginUser.getManagerCity());
            }
            if(loginUser.getManagerType() == 4){
                residentail.setCreateUserResidentail(loginUser.getManagerResidentail());
            }
        }
        List<Residentail> residentails = managerResidentailService.getExportList(residentail);

        this.managerResidentailService.export(request, residentails,response);
    }
}
