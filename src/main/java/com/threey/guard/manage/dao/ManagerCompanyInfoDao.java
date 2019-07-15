package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.CompanyInfo;
import org.springframework.stereotype.Repository;


@Repository
public class ManagerCompanyInfoDao extends CrudDAO<CompanyInfo> {
    @Override
    protected String getNameSpace() {
        return "ManagerCompanySQL";
    }
}
