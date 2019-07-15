package com.threey.guard.mapp.dao;

import com.threey.guard.base.dao.BaseDAO;
import com.threey.guard.mapp.domain.WxAppBindingVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WxAppDao.java
 *
 * @auth mulths@126.com
 * @date 2019/02/18
 */
@Repository
public class WxAppDao extends BaseDAO {

    protected String getNameSpace() {
        return "WxAppDao";
    }

    /**
     * 获取绑定的手机号
     * @param param
     * @return
     */
    public String getBindingPhone(Map<String,String> param){
        return  (String) getSqlMapClientTemplate().queryForObject(this.getNameSpace()+".getBindingPhone",param);
    }

    /**
     * 根据手机号查询住户绑定信息
     * */
    public List<WxAppBindingVO> getBindingHouses(String phone) {
        return getSqlMapClientTemplate().queryForList(this.getNameSpace() + ".getBindingHouses", phone);
    }

    public void cleanBind(String appid, String openid) {
        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);
        param.put("openid", openid);
        getSqlMapClientTemplate().update(this.getNameSpace() + ".cleanBind", param);
    }

    public void bindPhone(String appid, String openid, String phone) {
        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);
        param.put("openid", openid);
        param.put("phone", phone);
        getSqlMapClientTemplate().insert(this.getNameSpace() + ".bindPhone", param);
    }
}
