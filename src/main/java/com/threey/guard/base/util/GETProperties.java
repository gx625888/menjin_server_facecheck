package com.threey.guard.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/*
 * 获取.properties文件中的信息
 */
public class GETProperties {

	private static Properties props = new Properties();

	private GETProperties() {}

	static {
		InputStream in = GETProperties.class.getClassLoader().getResourceAsStream("system.properties");
		InputStream remoteIn = GETProperties.class.getClassLoader().getResourceAsStream("remote.properties");
		try {
			props.load(in);
			props.load(remoteIn);
			in.close();
			remoteIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 相关描述：根据key读取value
	 * @Author：tangchang   
	 * @Time：2013-8-22 下午01:21:33
	 * @Version 1.0
	 */
	public static String readValue(String key) {
		String value="";
		try {
			value = props.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 
	 * 相关描述：读取properties的全部信息
	 * @Author：tangchang   
	 * @Time：2013-8-22 下午01:21:40
	 * @Version 1.0
	 */
	public static void readAllProperties() {
		try {
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key +"="+ Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(GETProperties.readValue("USER_NAME_PASSWORD_NOT_EXIST"));
	}
}
