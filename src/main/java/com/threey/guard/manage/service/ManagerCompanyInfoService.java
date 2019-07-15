package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.manage.dao.ManagerCompanyInfoDao;
import com.threey.guard.manage.domain.CompanyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerCompanyInfoService extends CrudService<CompanyInfo> {
    @Autowired
    ManagerCompanyInfoDao managerCompanyInfoDao;
    @Override
    protected CrudDAO getDao() {
        return managerCompanyInfoDao;
    }
}
