package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManagerPersonInfoDao;
import com.threey.guard.manage.domain.ManagerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerPersonInfoService extends CrudService<ManagerInfo> {

    @Autowired
    ManagerPersonInfoDao managerPersonInfoDao;

    @Override
    protected CrudDAO getDao() {
        return managerPersonInfoDao;
    }

    public ManagerInfo initRoleName(ManagerInfo m){
        List<String> list = managerPersonInfoDao.getRoleList(m.getId());
        StringBuilder sb = new StringBuilder();
        for(String s:list){
            sb.append(s+",");
        }
        String roleName = sb.substring(0,sb.lastIndexOf(",")).toString();
        m.setRole(roleName);
        return m;
    }

    public int updatePW(Map map){
        return managerPersonInfoDao.updatePW(map);
    }
}
