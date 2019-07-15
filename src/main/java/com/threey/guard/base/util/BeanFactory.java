package com.threey.guard.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 提供spring上下文
 * @author XY
 *
 */
@Component
public class BeanFactory implements ApplicationContextAware{

	private static ApplicationContext ctx = null;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ctx = arg0;
	} 

	/**
	 * 相关说明：提供spring上下文
	 * 开发者：汤云涛
	 * 时间：2014年12月18日 下午2:18:51
	 */
	public static ApplicationContext getCtx(){
		
		return ctx;
	}
}
