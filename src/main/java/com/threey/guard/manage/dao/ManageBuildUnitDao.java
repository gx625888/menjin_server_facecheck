package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.manage.domain.BuildUnit;
import org.springframework.stereotype.Repository;

/**
 * ManageBuildUnitDao.java
 *
 * @auth mulths@126.com
 * @date 2019/06/18
 */
@Repository
public class ManageBuildUnitDao extends CrudDAO<BuildUnit> {

    @Override
    protected String getNameSpace() {
        return "ManageBuildUnit";
    }

}
