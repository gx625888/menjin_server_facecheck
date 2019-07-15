package com.threey.guard.manage.controller;


import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.Pages;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.domain.Area;
import com.threey.guard.manage.domain.Community;
import com.threey.guard.manage.service.ManagerCommunityService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区管理
 */
@Controller
@RequestMapping("/community")
public class ManagerCommunityController {
    private Logger logger = Logger.getLogger(ManagerCommunityController.class);
    @Autowired
    private ManagerCommunityService managerCommunityService;

    @RequestMapping("/toCommunityList.shtml")
    public String toManagerUserList(HttpServletRequest request){
        return "manage/community/communityIndex";
    }

    @RequestMapping(value = "/page.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Community> page(DataTable.Req p , HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Community community = new Community();
        community.setCity(request.getParameter("p_city"));
        community.setCounty(request.getParameter("p_county"));
        community.setProvince(request.getParameter("p_province"));
        community.setName(request.getParameter("p_name"));
        community.setStreet(request.getParameter("p_street"));
        community.setCreateUser(loginUser.getUserId());
        if(loginUser.getManagerType()!=0){
            community.setCreateUserCompany(loginUser.getManagerCompany());
            if(loginUser.getManagerType()==2){
                community.setCreateUserProvince(loginUser.getManagerProvince());
            }
            if(loginUser.getManagerType()==3){
                community.setCreateUserCity(loginUser.getManagerCity());
            }
        }
        List<Community> communitys = managerCommunityService.list(community,p.getPage()-1,p.getLimit());
        int count = this.managerCommunityService.count(community);
        return new DataTable.Resp<Community>(communitys,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Community community = this.managerCommunityService.getOne(id);
            request.setAttribute("community",community);
            request.setAttribute("opt","edit");
        }
        return "manage/community/communityAdd";
    }

    @RequestMapping(value = "/save.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveOrUpdate(Community community,HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        if (StringUtils.isNotEmpty(community.getId())){
            this.managerCommunityService.update(community);

        }else{
            community.setCreateUser(loginUser.getMid());
            this.managerCommunityService.add(community);
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
        if(this.managerCommunityService.getResidentailCount(id)){
            map.put("ret",-1);
            map.put("msg","该社区存在小区信息，不能删除！");
            return map;
        }
        this.managerCommunityService.del(id);
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/queryArea.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<Area> queryArea(HttpServletRequest request){
        Area area = new Area();
        area.setParentId(request.getParameter("parentId"));
        area.setType(Integer.parseInt(request.getParameter("aType")));
        return this.managerCommunityService.getArea(area);
    }
}
