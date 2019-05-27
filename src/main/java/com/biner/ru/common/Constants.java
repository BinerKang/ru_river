package com.biner.ru.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Constants {

	public static boolean IS_ENCRYPT = false;

	public static String SECRET_KEY;

	public static boolean NEED_KICK = false;
	
	public static int TOKEN_EXPIRE_DAYS = 30;
	
	public static int WEB_EXPIRE_MINS = 15;
	
	public static String MQ_HOST; 
	
	public static String MQ_USERNAME;
	
	public static String MQ_PASSWORD;
	
	public static int MQ_PORT = 5672;
	
	public static String ADMINSTRATOR_MAIL;
	
	public static int VERIFY_MAIL_EXPIRE_DAYS;
	
	public static String HOST_SERVER_BASE_URL;
	//验证邮件模板
	public static String VERIFY_MAIL_TITLE;
	public static String VERIFY_MAIL_CONTENT;
	public static String AD_MSG_CONTENT;
	//MaxMind 数据库GeoLite地址
	public static String geoLitePath;
	/**
	 * redis相关信息
	 */
	public static boolean usePool = true;
	public static boolean testOnBorrow = false;
	public static int maxIdle = 50;
	public static int maxTotal = 100;
	public static int minIdle = 20;
	public static int jedisPort = 6379;
	public static int jedisTimeout = 3000;
	public static String jedisIp = null;
	public static String jedisPassword = null;
	
	static {
		// 配置文件
		Properties configProperties = getProperties("/config.properties");
		IS_ENCRYPT = Boolean.parseBoolean(configProperties.getProperty("is.encrypt", "false").trim());
		NEED_KICK = Boolean.parseBoolean(configProperties.getProperty("needKick", "true").trim());
		SECRET_KEY = configProperties.getProperty("secret.key", "biner_ru").trim();
		ADMINSTRATOR_MAIL = configProperties.getProperty("administrator.mail").trim();
		VERIFY_MAIL_EXPIRE_DAYS = Integer.parseInt(configProperties.getProperty("verify.mail.expire.days").trim());
		HOST_SERVER_BASE_URL = configProperties.getProperty("host.server.base.url").trim();
		
		//redis
		jedisPort = Integer.parseInt(configProperties.getProperty("redis.port").trim());
		jedisIp = configProperties.getProperty("redis.ip").trim();
		jedisPassword = configProperties.getProperty("redis.password").trim();
		usePool = Boolean.parseBoolean(configProperties.getProperty("redis.usePool", "true").trim());
		testOnBorrow = Boolean.parseBoolean(configProperties.getProperty("redis.testOnBorrow", "false").trim());
		maxIdle = Integer.parseInt(configProperties.getProperty("redis.maxIdle").trim());;
		maxTotal = Integer.parseInt(configProperties.getProperty("redis.maxTotal").trim());;
		minIdle = Integer.parseInt(configProperties.getProperty("redis.minIdle").trim());;
		jedisTimeout = Integer.parseInt(configProperties.getProperty("redis.timeout").trim());
		
		geoLitePath =  configProperties.getProperty("geoLite.path").trim();
		
		// 模板配置文件
		configProperties = getProperties("/template.properties");
		VERIFY_MAIL_TITLE = configProperties.getProperty("verify.mail.title").trim();
		VERIFY_MAIL_CONTENT = configProperties.getProperty("verify.mail.content").trim();
		AD_MSG_CONTENT = configProperties.getProperty("ad.msg.content").trim();
		
	}
	
	public static Properties getProperties(String filePath) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = Constants.class.getResourceAsStream(filePath);
			props.load(new InputStreamReader(in, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return props;
	}
}
