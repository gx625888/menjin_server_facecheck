package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.domain.Privilege;
import com.threey.guard.manage.domain.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ManagerResourceDao extends CrudDAO<Resource> {
    @Override
    protected String getNameSpace() {
        return "ManagerResourceSQL";
    }

    public List<Privilege> listUnitByResidentail(Map map){
        return (List<Privilege>)getSqlMapClientTemplate().queryForList(getNameSpace()+".listUnitByResidentail",map);
    }

    public void insertUnitByResidentail(String resourceId,String units){
        if (StringUtils.isEmpty(units)){
            return ;
        }
        getSqlMapClientTemplate().insert(getNameSpace()+".insertUnitByResidentail",formatMap(resourceId,units,"resourceId","unitId"));
    }

    public void deleteUnitByResidentail(String resourceId){
        getSqlMapClientTemplate().delete(getNameSpace()+".deleteUnitByResidentail",resourceId);
    }

    private List<Map<String,String>> formatMap(String p, String ps, String kp , String kps){
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

    public void updateStatus(String id){
        getSqlMapClientTemplate().update(getNameSpace()+".updateStatus",id);
    }
}
