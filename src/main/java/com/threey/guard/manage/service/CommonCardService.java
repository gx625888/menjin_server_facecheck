package com.threey.guard.manage.service;

import com.threey.guard.api.domain.AccountData;
import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.CommonCardDao;
import com.threey.guard.manage.domain.CommonCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommonCardService extends CrudService<CommonCard> {

    @Autowired
    private CommonCardDao dao;
    @Override
    protected CrudDAO getDao() {
        return dao;
    }

    public List<AccountData> getCommonAccounts(String deviceId){
        return this.dao.getCommonAccounts(deviceId);
    }

    public List<CommonCard> getCardByCardNo(String cardNo){
        return this.dao.getCardByCardNo(cardNo);
    }
}
