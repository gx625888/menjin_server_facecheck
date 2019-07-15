package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManagerCardDao;
import com.threey.guard.manage.dao.ManagerCommunityDao;
import com.threey.guard.manage.domain.AddCard;
import com.threey.guard.manage.domain.Area;
import com.threey.guard.manage.domain.Card;
import com.threey.guard.manage.domain.TreeNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerCardService extends CrudService<Card> {

    private Logger logger = Logger.getLogger(ManagerCardService.class);

    @Autowired
    ManagerCardDao managerCardDao;
    @Autowired
    ManagerCommunityDao managerCommunityDao;

    @Override
    protected CrudDAO getDao() {
        return managerCardDao;
    }

    public List<AddCard> getAddCardList(AddCard addCard,int page,int pageSize){
        return managerCardDao.getAddCardList(addCard,page,pageSize);
    }

    public void insetAddCard(Map map){
        managerCardDao.inserAddCard(map);
    }

    public int countAddCard(AddCard addCard){
        return managerCardDao.countAddCard(addCard);
    }

    public List<Card> getBandCardList(Map map,int page,int pageSize){
        return managerCardDao.getBandCardList(map,page,pageSize);
    }

    public int countBandCard(Map map){
        return managerCardDao.countBandCard(map);
    }

    public List<Card> getBandCardListNew(Map map,int page,int pageSize){
        return managerCardDao.getBandCardListNew(map,page,pageSize);
    }

    public int countBandCardNew(Map map){
        return managerCardDao.countBandCardNew(map);
    }

    public void toLoseCard(String id){
        managerCardDao.toLoseCard(id);
    }

    public void addBindInfo(Map map) {managerCardDao.addBindInfo(map);}

    public void toNoBand(String id){
        managerCardDao.toNoBand(id);
    }

    public boolean bandCount(String id){
        return managerCardDao.bandCount(id);
    }

    public String getDeviceId(String id){
        return managerCardDao.getDeviceId(id);
    }

    public void baseTxAddBindInfo(Map map){
        managerCardDao.addBindInfo(map);
        if(managerCardDao.getCardCount(map.get("cardId").toString())==0){
            Map<String,Object> cMap = new HashMap<>();
            cMap.put("id",map.get("cardId"));
            cMap.put("cardType",1);
            cMap.put("invailidDate", "2037-12-31 23:59:59");
            cMap.put("cardStatus",1);
            cMap.put("createUser", map.get("createUser"));
            managerCardDao.addBindCard(cMap);
        }
    }

    public boolean cardCount(String id){
        if(managerCardDao.getCardCount(id)>0){
            return true;
        }
        return false;
    }

    public List<TreeNode> treeNodes(ManagerUser loginUser){
        List<TreeNode> list = managerCommunityDao.findTreeNode("0",loginUser,0);
        return initList(list,loginUser);
    }

    public List<TreeNode> initList(List<TreeNode> list,ManagerUser loginUser){
        for(TreeNode t : list){
            if(t.getType()==2){
                t.setChildren(initList(managerCommunityDao.findCommunityTreeNode(t.getId(),loginUser),loginUser));
            } else if(t.getType()==3){
                t.setChildren(initList(managerCommunityDao.findResidentailTreeNode(t.getId(),loginUser),loginUser));
            } else if(t.getType()==4){
                t.setChildren(initList(managerCommunityDao.findBuildTreeNode(t.getId()),loginUser));
            } else if(t.getType()==5){
                t.setChildren(initList(managerCommunityDao.findUnitTreeNode(t.getId()),loginUser));
            }else {
                t.setChildren(initList(managerCommunityDao.findTreeNode(t.getId(),loginUser,1),loginUser));
            }

        }
        return list;
    }


}
