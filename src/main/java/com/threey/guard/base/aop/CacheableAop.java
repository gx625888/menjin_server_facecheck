package com.threey.guard.base.aop;

import com.threey.guard.base.service.RedisService;
import com.threey.guard.base.util.CtxFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.WebApplicationContext;

@Aspect
public class CacheableAop {
	private static final Logger LOGGER = Logger.getLogger(CacheableAop.class);
	WebApplicationContext aWebApplicationContext;
	@Around("execution(* com.threey.guard.*.dao.*.*Cache(..))")
	public Object process(ProceedingJoinPoint point) throws Throwable {
		RedisService redisService =  CtxFactory.getCtx().getBean(RedisService.class);
		//获取目标方法的参数
		Object[] args = point.getArgs();
		String key = "";
		for(Object s:args){
			key+=s;
		}
		
		String val = redisService.getStringByKey(key);
		if(StringUtils.isNotBlank(val)){
			LOGGER.info("缓存中"+key+"的值："+val);
			return val;
		}
		
		Object returnValue = point.proceed(point.getArgs());
		if(null!=returnValue){
			redisService.put(key, returnValue.toString(), null);
			System.out.println("##############################:"+key);
			System.out.println(returnValue.toString());
		}
		return returnValue;
	}
}
