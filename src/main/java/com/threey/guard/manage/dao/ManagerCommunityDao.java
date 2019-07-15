package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.manage.domain.Area;
import com.threey.guard.manage.domain.Community;
import com.threey.guard.manage.domain.TreeNode;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ManagerCommunityDao extends CrudDAO<Community> {

    @Override
    protected String getNameSpace() {
        return "ManagerCommunitySQL";
    }

    public List<Area> getAreaList(Area area){
        return (List<Area>)getSqlMapClientTemplate().queryForList("ManagerAreaSQL.findArea",area);
    }

    public boolean getResidentailCount(String community){
        int count = (Integer) getSqlMapClientTemplate().queryForObject("ManagerCommunitySQL.getResidentCount",community);
        if(count>0){
            return true;
        }
        return false;
    }

    public List<TreeNode> findTreeNode(String parentId,ManagerUser loginUser,int nodeType){
        Map<String,Object> map = new HashMap<>();
        map.put("parentId",parentId);
        if(loginUser.getManagerType()!=0){
            if(nodeType==1){
                if(loginUser.getManagerType()>2){
                    map.put("city",loginUser.getManagerCity());
                }
            } else {
                map.put("company",loginUser.getManagerCompany());
                if(loginUser.getManagerType()>1){
                    map.put("province",loginUser.getManagerProvince());
                }
            }
        }

        return (List<TreeNode>)getSqlMapClientTemplate().queryForList("ManagerAreaSQL.findTreeNode",map);
    }

    public List<TreeNode> findCommunityTreeNode(String parentId,ManagerUser loginUser){
        Map<String,Object> map = new HashMap<>();
        map.put("parentId",parentId);
        if(loginUser.getManagerType() !=0){
            map.put("company",loginUser.getManagerCompany());
            if(loginUser.getManagerType()==4){
                map.put("residentail",loginUser.getManagerResidentail());
            }
        }
        return (List<TreeNode>)getSqlMapClientTemplate().queryForList("ManagerAreaSQL.findCommunityTreeNode",map);
    }

    public List<TreeNode> findResidentailTreeNode(String parentId,ManagerUser loginUser){
        Map<String,Object> map = new HashMap<>();
        map.put("parentId",parentId);
        if(loginUser.getManagerType() !=0){
            map.put("company",loginUser.getManagerCompany());
            if(loginUser.getManagerType()==4){
                map.put("residentail",loginUser.getManagerResidentail());
            }
        }
        return (List<TreeNode>)getSqlMapClientTemplate().queryForList("ManagerAreaSQL.findResidentailTreeNode",map);
    }

    public List<TreeNode> findBuildTreeNode(String parentId){
        return (List<TreeNode>)getSqlMapClientTemplate().queryForList("ManagerAreaSQL.findBuildTreeNode",parentId);
    }

    public List<TreeNode> findUnitTreeNode(String parentId){
        return (List<TreeNode>)getSqlMapClientTemplate().queryForList("ManagerAreaSQL.findUnitTreeNode",parentId);
    }
}
