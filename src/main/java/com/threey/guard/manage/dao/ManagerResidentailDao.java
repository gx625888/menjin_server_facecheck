package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.Community;
import com.threey.guard.manage.domain.Residentail;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ManagerResidentailDao extends CrudDAO<Residentail> {
    @Override
    protected String getNameSpace() {
        return "ManagerResidentailSQL";
    }

    public List<Community> getAreaList(Community community){
        return (List<Community>)getSqlMapClientTemplate().queryForList("ManagerCommunitySQL.list",community);
    }

    public boolean checkHasChild(String id){
        return (Integer)getSqlMapClientTemplate().queryForObject("ManagerCommunitySQL.checkHasChild",id)>0;
    }

    public List<Residentail> getResidentailList(){
        return (List<Residentail>)getSqlMapClientTemplate().queryForList(this.getNameSpace()+".queryResidentail");
    }

    public List<Residentail> getResidentailListByCommunity(String community){
        return (List<Residentail>)getSqlMapClientTemplate().queryForList(this.getNameSpace()+".queryResidentailByCommunity",community);
    }

    public List<Residentail> getExportList(Residentail obj) {
        return  getSqlMapClientTemplate().queryForList(getNameSpace()+".list",obj);
    }
}
