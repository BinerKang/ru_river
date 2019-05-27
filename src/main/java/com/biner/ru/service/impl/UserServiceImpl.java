package com.biner.ru.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.common.RedisKeyConstants;
import com.biner.ru.mapper.UserMapper;
import com.biner.ru.model.User;
import com.biner.ru.service.CommonService;
import com.biner.ru.service.GameService;
import com.biner.ru.service.UserService;
import com.biner.ru.util.AESUtils;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private EmailUtils emailUtils;
	
	/**
	 * 注册用户方法
	 * @author KangBinbin
	 * @throws Exception 
	 * @date 2018-06-03
	 */
	public synchronized MapResult register(String sessionId, String username, String password,
			String mail, String code, String realCode, String score) throws Exception {
		//TODO 非空校验，参数校验
		if(!realCode.equalsIgnoreCase(code)) {
			return new MapResult(CodeMsg.FAIL, "验证码错误");
		}
		// 邮箱唯一性校验
		User u = new User();
		u.setMail(mail);
		u = userMapper.selectOne(u);
		if (u != null) {
			return new MapResult(CodeMsg.FAIL, "该邮箱已注册");
		}
		// 密码加密
		password = DigestUtils.md5Hex(password);
		u = new User();
		u.setPassword(password);
		u.setMail(mail);
		u.setUsername(username);
		// 保存数据库
		userMapper.save(u);
		if (!mail.equals(Constants.ADMINSTRATOR_MAIL)) {//不是管理员发送邮件验证
			// 发送验证邮件
			String content = commonService.getVerifyMailContent(mail, username);
			emailUtils.sendEmail(mail, Constants.VERIFY_MAIL_TITLE, content);
		}
		// 将session放入redis
		RedisKeyConstants.addTokenToRedis(sessionId, JSONObject.toJSONString(u));
		if (StringUtils.isNotEmpty(score)) {
			gameService.recordScore(score, String.valueOf(u.getId()));
		}
		return new MapResult();
	}

	public MapResult login(String sessionId, String password, String mail, String code, 
			String realCode, String score) throws Exception {
		//TODO 非空校验，参数校验
		if(!realCode.equalsIgnoreCase(code)) {
			return new MapResult(CodeMsg.FAIL, "验证码错误");
		}
		// 邮箱唯一性校验
		User u = new User();
		u.setMail(mail);
		u.setPassword(DigestUtils.md5Hex(password));
		u = userMapper.selectOne(u);
		if (u == null) {
			return new MapResult(CodeMsg.FAIL, "账号或密码错误");
		}
		// 将session放入redis
		RedisKeyConstants.addTokenToRedis(sessionId, JSONObject.toJSONString(u));
    	if (StringUtils.isNotEmpty(score)) {
			gameService.recordScore(score, String.valueOf(u.getId()));
		}
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userInfo", u);
		result.setData(data);
		return result;
	}
	
	@Transactional
	public MapResult authMail(String sessionId, String code) throws Exception {
		if(StringUtils.isEmpty(code)) {
			return new MapResult(CodeMsg.FAIL, "参数为空");
		}
		code = AESUtils.decrypt(code, Constants.SECRET_KEY);
		if (!code.contains("_")) {
			return new MapResult(CodeMsg.FAIL, "url异常");
		}
		String[] info = code.split("_");
		double day = (System.currentTimeMillis() - Long.parseLong(info[1])) / (1000 * 60 * 60 * 24.0);
		if (day > Constants.VERIFY_MAIL_EXPIRE_DAYS) {
			return new MapResult(CodeMsg.FAIL, "Sorry，该邮件链接已失效");
		}
		User u = new User();
		u.setMail(info[0]);
		u.setValidate(true);
		int count = userMapper.updateByMail(u);
		if (count != 1) {
			throw new Exception("Auth Mail update result count is not one.");
		} else {
			u = userMapper.selectOne(u);
		}
		// 将session放入redis
		RedisKeyConstants.addTokenToRedis(sessionId, JSONObject.toJSONString(u));
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userInfo", u);
		result.setData(data);
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("jiahe168"));
	}
}
