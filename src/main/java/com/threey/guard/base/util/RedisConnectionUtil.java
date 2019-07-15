package com.threey.guard.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Random;

@Component
public class RedisConnectionUtil{


	@Autowired
	private RedisTemplate redisTemplate;
	public static  JedisConnection getJedisConnection () {
		JedisConnectionFactory  jedisConnectionFactory = (JedisConnectionFactory) CtxFactory.getCtx().getBean("connectionFactory");
		return  jedisConnectionFactory.getConnection();

	}

	private void test(){

	}
	
	public static void main(String[] args) {
		final ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:spring-memcache.xml","classpath:spring-redis.xml"});
		for(int i = 1 ;i <= 40000 ;i ++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String r = new Random(400000).nextInt() + "";
						JedisConnection  jedisConnection = 	getJedisConnection();
						Jedis  j= jedisConnection.getNativeConnection();
						j.set("1111" + r, "222");


						System.out.println(r + ":" + j.get("1111" + r));
						
						System.out.println(j.del("1111" + r) );
						
						System.out.println(r + ":" + j.get("1111"  + r));
						jedisConnection.close();
						System.out.println("+++++++++++++++++++++++");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

	}
}
