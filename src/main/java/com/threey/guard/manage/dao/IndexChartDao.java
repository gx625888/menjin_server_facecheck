package com.threey.guard.manage.dao;

import com.threey.guard.base.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 统计图表dao
 */
@Repository
public class IndexChartDao extends BaseDAO {


    public List<Map> openRecord(List<String> days){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.queryOpenRecord",days);
    }

    public List<Map> openRecord(Map<String,String> param){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.queryOpenRecordByParam",param);
    }
    public List<Map> getWarn(List<String> days){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.getWarn",days);
    }
    public List<Map> getWarn(Map<String,String> param){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.getWarnByParam",param);
    }

    public List<Map> openType(){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.openType");
    }

    public List<Map> deviceStatus(){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.deviceStatus");
    }

    public List<Map> openType(Map<String,String> params){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.openTypeByParam",params);
    }

    public List<Map> deviceStatus(Map<String,String> params){
        return this.getSqlMapClientTemplate().queryForList("IndexChartSql.deviceStatusByParam",params);
    }

}
