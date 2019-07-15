package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.AddCard;
import com.threey.guard.manage.domain.Card;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ManagerCardDao extends CrudDAO<Card> {
    @Override
    protected String getNameSpace() {
        return "ManagerCardSQL";
    }

    public List<AddCard> getAddCardList(AddCard addCard,int page,int pageSize){
        return (List<AddCard>)getSqlMapClientTemplate().queryForList("ManagerCardSQL.selectAddCard",addCard,page*pageSize,pageSize);
    }

    public void inserAddCard(Map map){
        getSqlMapClientTemplate().update("ManagerCardSQL.insertAddCard",map);
    }

    public int countAddCard(AddCard addCard){
        return (Integer) getSqlMapClientTemplate().queryForObject("ManagerCardSQL.addCardCount",addCard);
    }

    public List<Card> getBandCardList(Map map,int page,int pageSize){
        return (List<Card>)getSqlMapClientTemplate().queryForList("ManagerCardSQL.selectBandCard",map,page*pageSize,pageSize);
    }

    public int countBandCard(Map map){
        return (Integer)getSqlMapClientTemplate().queryForObject("ManagerCardSQL.bandCardCount",map);
    }

    public List<Card> getBandCardListNew(Map map,int page,int pageSize){
        return (List<Card>)getSqlMapClientTemplate().queryForList("ManagerCardSQL.selectBandCardNew",map,page*pageSize,pageSize);
    }

    public int countBandCardNew(Map map){
        return (Integer)getSqlMapClientTemplate().queryForObject("ManagerCardSQL.bandCardCountNew",map);
    }

    public void toLoseCard(String id){
        getSqlMapClientTemplate().update("ManagerCardSQL.toLoseCard",id);
    }

    public void addBindInfo(Map map){
        getSqlMapClientTemplate().update("ManagerCardSQL.addBandInfo",map);
    }

    public void toNoBand(String id){
        getSqlMapClientTemplate().delete("ManagerCardSQL.toNoBand",id);
    }

    public boolean bandCount(String id){
        int count = (Integer) getSqlMapClientTemplate().queryForObject("ManagerCardSQL.getBandCount",id);
        if(count>0){
            return true;
        }
        return false;
    }

    public void addBindCard(Map map){
        getSqlMapClientTemplate().update("ManagerCardSQL.addBindCard",map);
    }

    public int getCardCount(String id){
        return (Integer)getSqlMapClientTemplate().queryForObject("ManagerCardSQL.getCardCount",id);
    }
    public String getDeviceId(String id){
        return (String)getSqlMapClientTemplate().queryForObject("ManagerCardSQL.getDeviceId",id);
    }
}
