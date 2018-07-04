package com.biner.ru.common;

import org.apache.log4j.Logger;

import com.biner.ru.util.RedisClient;

public class RedisKeyConstants {

	public static Logger logger = Logger.getLogger(RedisKeyConstants.class);
	
	public static String getTokenRedisKey(String token) {
		return token + "_token";
	}
	
	/**
	 * @author Binbin
	 * @date 2018-07-04
	 * @param token
	 * @param useId
	 * @description 登录，注册，验证邮件成功后，放入redis
	 */
	public static void addTokenToRedis(String token, String useId) {
		String tokenKey = RedisKeyConstants.getTokenRedisKey(token);
    	RedisClient.setString(tokenKey, useId, Constants.WEB_EXPIRE_MINS * 60);
	}
}
