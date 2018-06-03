package com.biner.ru.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.Constants;
import com.biner.ru.common.MD5Util;
import com.biner.ru.common.MapResult;
import com.biner.ru.common.RedisKeyConstants;
import com.biner.ru.mapper.UserMapper;
import com.biner.ru.model.User;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.DateUtil;
import com.biner.ru.util.RedisClient;
import com.biner.ru.util.RegularExpressionUtil;

@Service
public class UserService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 注册用户方法
	 * @author KangBinbin
	 * @date 2018-06-03
	 */
	public synchronized MapResult register(String telphone, String verifyCode, String password, String osType, String registrationId,
			String appType) {
		return null;
	}


}
