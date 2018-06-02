package com.biner.ru.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biner.ru.common.MapResult;
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
		try {
			Thread.sleep(3000);
			result = new MapResult();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

}
