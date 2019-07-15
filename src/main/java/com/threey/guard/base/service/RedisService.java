package com.threey.guard.base.service;

import com.threey.guard.base.util.RedisConnectionUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class RedisService {

	private Logger LOGGER = Logger.getLogger(RedisService.class);

	/**
	 * 
	 * @相关说明：根据key从缓存中获取值
	 * @开发者：汤云涛
	 * @时间：2015年7月17日 下午7:13:50
	 */
	public String getStringByKey(String key) {
		JedisConnection jedisConnection = null;
		try {
			jedisConnection = RedisConnectionUtil.getJedisConnection();
			Jedis nativeConnection = jedisConnection.getNativeConnection();
			byte[] val = nativeConnection.get(key.getBytes("UTF-8"));
			if (null != val) {
				Object value;
				try {
					value = new String(val, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("从缓存中获取" + key + "的值失败", e);
					return null;
				}
				LOGGER.info("缓存中" + key + "的值：" + value);
				if (null != value && !"".equals(value)) {
					return value.toString();
				}
			}
		} catch (Exception e1) {
			LOGGER.info(e1);
		} finally{
			if(jedisConnection != null){
				jedisConnection.close();
			}
		}

		return null;
	}

	/**
	 * 
	 * @相关说明：将键值对放入缓存
	 * @开发者：汤云涛
	 * @时间：2015年7月17日 下午7:21:39
	 */
	public void put(String key, String value, Long delayTime) {
		JedisConnection jedisConnection = null;
		try {
			jedisConnection = RedisConnectionUtil.getJedisConnection();
			Jedis nativeConnection = jedisConnection.getNativeConnection();
			nativeConnection.set(key.getBytes("UTF-8"), value.getBytes("UTF-8"));
			if(delayTime != null){
				nativeConnection.expire(key.getBytes("UTF-8"), delayTime.intValue());
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.info(e);
		} finally{
			if(jedisConnection != null){
				jedisConnection.close();
			}

		}
	}

	/**
	 * 
	 * @相关说明：清缓存
	 * @开发者：汤云涛
	 * @时间：2015年8月6日 下午5:47:16
	 */
	  public Long clear(String key) {
			JedisConnection jedisConnection = null;
			try {
			    jedisConnection = RedisConnectionUtil.getJedisConnection();
			    Jedis nativeConnection = jedisConnection.getNativeConnection();
			    return nativeConnection.del(key.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.info(e);
				return new Long(0);
			} finally{
			    if(jedisConnection != null){
				jedisConnection.close();
			    }
			}
	 }

	public boolean keyExists(String key){
		JedisConnection jedisConnection = null;
		try {
			jedisConnection = RedisConnectionUtil.getJedisConnection();
			Jedis nativeConnection = jedisConnection.getNativeConnection();
			return nativeConnection.exists(key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.info(e);
		} finally{
			if(jedisConnection != null){
				jedisConnection.close();
			}
		}
		return false;
	}
	
	public String ping(){
		JedisConnection jedisConnection = null;
		try {
			jedisConnection = RedisConnectionUtil.getJedisConnection();
			Jedis nativeConnection = jedisConnection.getNativeConnection();
			return nativeConnection.ping();
		} finally{
			if(jedisConnection != null){
				jedisConnection.close();
			}
		}
	}
	
	public void batchSet(List<String[]> keyValList){
		JedisConnection jedisConnection = null;
		try {
			jedisConnection = RedisConnectionUtil.getJedisConnection();
			Jedis nativeConnection = jedisConnection.getNativeConnection();
			for(String[] arr : keyValList){
				nativeConnection.pipelined().set(arr[0].getBytes("UTF-8"), arr[1].getBytes("UTF-8"));
			}
			nativeConnection.sync();
		} catch (UnsupportedEncodingException e) {
			LOGGER.info(e);
		} finally{
			if(jedisConnection != null){
				jedisConnection.close();
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml",
		"classpath:spring-redis.xml" });

		RedisService bean = (RedisService) ctx.getBean("redisService");
		//bean.put("1", "2", null);
		// /05090df7ff044095864fed63d5ce8a44
		//System.out.println(bean.getStringByKey("1"));
		System.out.println(bean.ping());
	}
}
