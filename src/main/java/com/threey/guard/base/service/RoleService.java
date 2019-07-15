package com.threey.guard.base.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.dao.RoleDAO;
import com.threey.guard.base.domain.Menu;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.base.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色管理service
 */
@Service
public class RoleService extends CrudService<Role>{

    @Autowired
    private RoleDAO dao;

    @Override
    protected CrudDAO getDao() {
        return dao;
    }

    public List<Role> getRolesByUser(String userId){
        return this.dao.getRolesByUser(userId);
    }

    public List<Menu>  getMenusByRole(String roleId){
        return this.dao.getMenusByRole(roleId);
    }

    public void saveMenuRoles(String roleId,String menus){
        this.dao.deleteRoleMenus(roleId);
        this.dao.insertRoleMenus(roleId,menus);
    }
    public void saveUserRoles(String userId,String roles){
        this.dao.deleteUserRoles(userId);
        this.dao.insertUserRole(userId,roles);
    }

    public List<Privilege>  getPrivilegeByRole(String roleId){
        return this.dao.getPrivilegeByRole(roleId);
    }
    public void saveRolePrivilege(String roleId,String privileges){
        this.dao.deleteRolePrivilege(roleId);
        this.dao.insertRolePrivilege(roleId,privileges);
    }
}
