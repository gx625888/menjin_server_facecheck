package com.threey.guard.base.dao;

import com.threey.guard.base.domain.Menu;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.domain.Role;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDAO extends CrudDAO<Role> {

    public List<Role>  getRolesByUser(String userId){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".listByUser",userId);
    }

    public void deleteUserRoles(String userId){
        getSqlMapClientTemplate().delete(getNameSpace()+".deleteUserRoles",userId);
    }
    public void insertUserRole(String userId,String roleids){
        if (StringUtils.isEmpty(roleids)){
            return ;
        }
        getSqlMapClientTemplate().insert(getNameSpace()+".insertUserRoleBatch",formatMap(userId,roleids,"userId","roleId"));
    }

    public List<Menu>  getMenusByRole(String roleId){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".listMenuByRole",roleId);
    }

    public void deleteRoleMenus(String roleId){
        getSqlMapClientTemplate().delete(getNameSpace()+".deleteRoleMenus",roleId);
    }
    public void insertRoleMenus(String roleId,String menus){
        if (StringUtils.isEmpty(menus)){
            return ;
        }
        String[] menuArr = menus.split(",");
        getSqlMapClientTemplate().insert(getNameSpace()+".insertRoleMenus",formatMap(roleId,menus,"roleId","menuId"));
    }



    public List<Privilege>  getPrivilegeByRole(String roleId){
        return getSqlMapClientTemplate().queryForList(getNameSpace()+".listPrivilegeByRole",roleId);
    }

    public void deleteRolePrivilege(String roleId){
        getSqlMapClientTemplate().delete(getNameSpace()+".deleteRolePrivilege",roleId);
    }
    public void insertRolePrivilege(String roleId,String menus){
        if (StringUtils.isEmpty(menus)){
            return ;
        }
        getSqlMapClientTemplate().insert(getNameSpace()+".insertRolePrivilege",formatMap(roleId,menus,"roleId","privilege"));
    }

    private List<Map<String,String>> formatMap(String p,String ps,String kp ,String kps){
        List<Map<String,String>> list = new ArrayList<>();
        String[] arr = ps.split(",");
        for (String a: arr) {
            Map<String,String> map = new HashMap<>();
            map.put(kp,p);
            map.put(kps,a);
            list.add(map);
        }
        return list;
    }

    @Override
    public String getNameSpace() {
        return "RoleSql";
    }
}
