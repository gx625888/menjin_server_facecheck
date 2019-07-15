package com.threey.guard.base.dao;

import com.threey.guard.base.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends CrudDAO<User> {
    @Override
    public String getNameSpace() {
        return "UserSql";
    }
}
