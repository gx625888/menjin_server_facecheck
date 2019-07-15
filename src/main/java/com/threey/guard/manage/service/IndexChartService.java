package com.threey.guard.manage.service;

import com.threey.guard.manage.dao.IndexChartDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 统计图表serivce
 */
@Service
public class IndexChartService {

    @Autowired
    private IndexChartDao chartDao;


    public List<Map> getRecentOpenData(List<String> daysList){
        if (CollectionUtils.isEmpty(daysList)){
            return null;
        }

        return this.chartDao.openRecord(daysList);
    }
    public List<Map> getRecentOpenData(Map<String,String> params){

        return this.chartDao.openRecord(params);
    }


    public List<Map> getRecentWarnData(List<String> daysList){
        if (CollectionUtils.isEmpty(daysList)){
            return null;
        }
        return this.chartDao.getWarn(daysList);
    }
    public List<Map> getRecentWarnData(Map<String,String> params){
        return this.chartDao.getWarn(params);
    }


    public List<Map> openType(){
        return this.chartDao.openType();
    }
    public List<Map> openType(Map<String,String> params){
            return this.chartDao.openType(params);
    }

    public List<Map> deviceStatus(Map<String,String> params){
        return this.chartDao.deviceStatus(params);
    }
    public List<Map> deviceStatus(){
        return this.chartDao.deviceStatus();
    }

}
