package com.threey.guard.clientmsg.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.threey.guard.base.dao.BaseDAO;
import com.threey.guard.clientmsg.domain.MsgBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询推送消息dao
 */
@Repository
public class SendMsgDao extends BaseDAO {

    /**
     * 查询需要推送的消息
     * @return
     */
    public List<MsgBean> queryMsgs(int limit,int tryCount){
        Map<String,Object> params = new HashMap<>();
        params.put("limit",limit);
        params.put("tryCount",tryCount);

        return this.getSqlMapClientTemplate().queryForList("SendMsgSql.query",params);
    }

    public void insert(MsgBean msg){
        this.getSqlMapClientTemplate().insert("SendMsgSql.insert",msg);
    }

    public void insertBatch(List<MsgBean> beans){
        this.getSqlMapClientTemplate().insert("SendMsgSql.insertBatch",beans);
    }

    public void insertOnlineDevice(List<String> devices){
        if (CollectionUtils.isEmpty(devices)){
            return ;
        }

        this.getSqlMapClientTemplate().insert("SendMsgSql.insertOnline",devices);
    }

    public void deleteOnlineDevice(){
        this.getSqlMapClientTemplate().delete("SendMsgSql.deleteOnline");
    }

    /**
     * 更新消息状态
     * @param msgs
     */
    public void updateStatus(List<MsgBean> msgs){

        SqlMapClient sqlMapClient = this.getSqlMapClient();
        try {
            sqlMapClient.startBatch();//开始批处理
            for (MsgBean msg:  msgs) {
                this.getSqlMapClientTemplate().update("SendMsgSql.update",msg);
            }
            sqlMapClient.executeBatch();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
