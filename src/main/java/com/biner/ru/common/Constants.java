package com.biner.ru.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Constants {

	public static boolean IS_ENCRYPT = false;

	public static String DES_KEY;

	public static boolean NEED_KICK = false;
	
	public static int TOKEN_EXPIRE_DAY = 30;
	
	public static String MQ_HOST; 
	
	public static String MQ_USERNAME;
	
	public static String MQ_PASSWORD;
	
	public static int MQ_PORT = 5672;
	
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
		IS_ENCRYPT = Boolean.parseBoolean(configProperties.getProperty("is_encrypt", "false").trim());
		NEED_KICK = Boolean.parseBoolean(configProperties.getProperty("needKick", "true").trim());
		DES_KEY = configProperties.getProperty("des.key", "biner_ru").trim();
		
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
