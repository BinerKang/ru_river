package com.biner.ru.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.IpUtils;
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.ContactService;

@Controller
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	private ContactService contactService;

	private Logger logger = Logger.getLogger(ContactController.class);

	@RequestMapping("/token/saveContact")
	public void saveArticle(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String telphone = params.get("telphone");
			String call = params.get("call");
			String gender = params.get("gender");
			String info = params.get("info");
			String ip = IpUtils.getIpAddr(request);
			result = contactService.saveContact(ip, telphone, call, gender, info, null);
		} catch (Exception e) {
			logger.error("Save Contact has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/updateContact")
	public void updateContact(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			Long id = Long.parseLong(params.get("id"));
			String telphone = params.get("telphone");
			String call = params.get("call");
			String gender = params.get("gender");
			String contactedTime = params.get("contactedTime");
			String ip = params.get("ip");
			String info = params.get("info");
			String userInfo = params.get("userInfo");
			result = contactService.updateContact(id, ip, telphone, call, gender, contactedTime, info, userInfo);
		} catch (Exception e) {
			logger.error("Update Contact has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
	@RequestMapping("/deleteContact")
	public void deleteContact(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			Long id = Long.parseLong(params.get("id"));
			String userInfo = params.get("userInfo");
			result = contactService.updateContact(id, null, null, null, null, null, null, userInfo);
		} catch (Exception e) {
			logger.error("Update Contact has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
	@RequestMapping("/getContactsCount")
	public void getContactsCount(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String keyword = params.get("keyword");
			String isContacted = params.get("isContacted");
			result = contactService.getContactsCount(keyword, isContacted);
		} catch (Exception e) {
			logger.error("GetContactsCount has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/getContacts")
	public void getContacts(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			int pageNo = Integer.parseInt(params.get("pageNo"));
			String keyword = params.get("keyword");
			String isContacted = params.get("isContacted");
			result = contactService.getContacts(pageNo, keyword, isContacted);
		} catch (Exception e) {
			logger.error("Get Contacts has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
}
