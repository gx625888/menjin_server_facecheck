package com.threey.guard.base.service;

import com.threey.guard.base.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    @Autowired
    private CommonDAO dao;


    /**
     * 获取序列值   序列名插入到sequence 表中
     *
     * insert into sequence values (序列名 ,初始值 , 步长);
     * @param seqName
     * @return
     */
    public long getNextVal(String seqName){
        return this.dao.getNextVal(seqName);
    }
}
