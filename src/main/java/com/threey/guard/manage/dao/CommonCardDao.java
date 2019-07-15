package com.threey.guard.manage.dao;

import com.threey.guard.api.domain.AccountData;
import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.CommonCard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommonCardDao extends CrudDAO<CommonCard> {
    @Override
    protected String getNameSpace() {
        return "CommonCardSql";
    }


    public List<AccountData> getCommonAccounts(String deviceId){
        return (List<AccountData>)getSqlMapClientTemplate().queryForList("CommonCardSql.getCommonAccounts",deviceId);
    }
    public List<CommonCard> getCardByCardNo(String cardId){
        return getSqlMapClientTemplate().queryForList("CommonCardSql.listByCard",cardId);
    }

}
