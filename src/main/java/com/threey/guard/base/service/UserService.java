package com.threey.guard.base.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.dao.UserDAO;
import com.threey.guard.base.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<User>{
    @Autowired
    private UserDAO dao;

    @Override
    protected CrudDAO getDao() {
        return dao;
    }
}
