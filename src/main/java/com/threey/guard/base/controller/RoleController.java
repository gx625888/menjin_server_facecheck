package com.threey.guard.base.controller;


import com.threey.guard.base.domain.*;
import com.threey.guard.base.service.RoleService;
import com.threey.guard.base.util.JsonUtil;
import com.threey.guard.base.util.Pages;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.domain.Msg;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@Controller
@RequestMapping("/manageRole")
public class RoleController {

    @Autowired
    private RoleService service;
    @RequestMapping("/role.shtml")
    public String toManagerUserList(ManagerUser mu, Pages<ManagerUser> pages){
      //  pages = managerUserService.getManagerUserByPage(mu, pages);
//        ModelAndView mv = new ModelAndView("manageUser/manageUserList");
//        mv.addObject("pages", pages);
//        mv.addObject("mu",mu);
        return "base/role/roleIndex";
    }

    @RequestMapping("/page.shtml")
    @ResponseBody
    public DataTable.Resp<Role> page(DataTable.Req p , HttpServletRequest request){
        Role role = new Role();
        role.setName(request.getParameter("name"));
        List<Role>  roles = this.service.list(role,p.getPage()-1,p.getLimit());
        int count = this.service.count(role);
        return new DataTable.Resp<Role>(roles,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toAdd(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Role role = this.service.getOne(id);
            request.setAttribute("role",role);
        }
        return "base/role/roleAdd";
    }

    @RequestMapping("/save.shtml")
    @ResponseBody
    public Map<String,Object> saveOrUpdate(Role role){
        if (StringUtils.isNotEmpty(role.getId())){
            role.setCreateTime(StringUtil.getTimetmp2());
            this.service.update(role);

        }else{
            role.setId(StringUtil.getUuid());
            role.setCreateTime(StringUtil.getTimetmp2());
            role.setDelFlag("0");
            this.service.add(role);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/del.shtml")
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request){
        String id = request.getParameter("id");
        this.service.del(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/queryUserRole.shtml")
    public String queryUserRole(HttpServletRequest req){
        List<Role> roles = this.service.getRolesByUser(req.getParameter("userId"));
        req.setAttribute("roles",JsonUtil.getJsonString(roles));
        req.setAttribute("userId",req.getParameter("userId"));
        return "base/user/selectRole";
    }

    @RequestMapping("/saveUserRole.shtml")
    @ResponseBody
    public Map<String,Object> saveUserRole(HttpServletRequest req){
        String userId = req.getParameter("userId");
        String roles = req.getParameter("roles");
        this.service.saveUserRoles(userId,roles);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }


    @RequestMapping("/queryRoleMenu.shtml")
    public String queryRoleMenu(HttpServletRequest req){
        List<Menu> menus = this.service.getMenusByRole(req.getParameter("roleId"));
        req.setAttribute("menus",JsonUtil.getJsonString(menus));
        req.setAttribute("roleId",req.getParameter("roleId"));
        return "base/role/selectMenu";
    }

    @RequestMapping("/saveRoleMenu.shtml")
    @ResponseBody
    public Map<String,Object> saveRoleMenu(HttpServletRequest req){
        String roleId = req.getParameter("roleId");
        String menus = req.getParameter("menus");
        this.service.saveMenuRoles(roleId,menus);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/queryRolePrivilege.shtml")
    public String queryRolePrivilege(HttpServletRequest req){
        List<Privilege> privileges = this.service.getPrivilegeByRole(req.getParameter("roleId"));
        req.setAttribute("privileges",JsonUtil.getJsonString(privileges));
        req.setAttribute("roleId",req.getParameter("roleId"));
        return "base/role/selectButton";
    }

    @RequestMapping("/saveRolePrivilege.shtml")
    @ResponseBody
    public Map<String,Object> saveRolePrivilege(HttpServletRequest req){
        String roleId = req.getParameter("roleId");
        String privilege = req.getParameter("privilege");
        this.service.saveRolePrivilege(roleId,privilege);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

}
