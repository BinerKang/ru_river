package com.biner.ru.common;

import org.apache.log4j.Logger;

public class RedisKeyConstants {

	public static Logger logger = Logger.getLogger(RedisKeyConstants.class);
	
	public static String getTokenRedisKey(String token) {
		return token + "_token";
	}
}
