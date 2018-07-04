package com.biner.ru.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.common.RedisKeyConstants;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.RedisClient;
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping("/token/register")
	public void register(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		Map<String, String> params = ParamThreadLocal.get();
		try {
			String username = params.get("username");
			String password = params.get("password");
			String mail = params.get("mail");
			String code = params.get("code");
			String score = params.get("score");
			String sessionId = request.getSession().getId();
			String realCode = (String) request.getSession().getAttribute("verCode");
			result = userService.register(sessionId, username, password, mail, code, realCode, score);
		} catch (Exception e) {
			logger.error("Register has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/token/authMail")
	public void authMail(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		Map<String, String> params = ParamThreadLocal.get();
		try {
			String code = params.get("code");
			String sessionId = request.getSession().getId();
			result = userService.authMail(sessionId, code);
		} catch (Exception e) {
			logger.error("Auth mail has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
	@RequestMapping("/token/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		Map<String, String> params = ParamThreadLocal.get();
		try {
			String password = params.get("password");
			String mail = params.get("mail");
			String code = params.get("code");
			String score = params.get("score");
			String sessionId = request.getSession().getId();
			String realCode = (String) request.getSession().getAttribute("verCode");
			result = userService.login(sessionId, password, mail, code, realCode, score);
		} catch (Exception e) {
			logger.error("Register has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
}
