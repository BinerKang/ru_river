package com.biner.ru.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	private Logger logger = Logger.getLogger(CustomerController.class);

	@RequestMapping("/saveCustomer")
	public void saveArticle(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String telphone = params.get("telphone");
			String name = params.get("name");
			String gender = params.get("gender");
			String estate = params.get("estate");
			String address = params.get("address");
			String url = params.get("url");
			String info = params.get("info");
			String userInfo = params.get("userInfo");
			result = customerService.saveCustomer(telphone, name, gender, estate, address, url, 10L, info, userInfo);
		} catch (Exception e) {
			String errMsg = "请求异常";
			if(e instanceof DuplicateKeyException) {
				errMsg = "手机号重复，请调整";
			} 
			logger.error("Save Customer has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, errMsg);
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/updateCustomer")
	public void updateCustomer(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			Long id = Long.parseLong(params.get("id"));
			String telphone = params.get("telphone");
			String name = params.get("name");
			String gender = params.get("gender");
			String estate = params.get("estate");
			String address = params.get("address");
			String url = params.get("url");
			String info = params.get("info");
			String userInfo = params.get("userInfo");
			result = customerService.updateCustomer(id, telphone, name, gender, estate, address, url, 10L, info, userInfo, null);
		} catch (Exception e) {
			String errMsg = "请求异常";
			if(e instanceof DuplicateKeyException) {
				errMsg = "手机号重复，请调整";
			} 
			logger.error("Update Customer has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, errMsg);
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
	@RequestMapping("/deleteCustomer")
	public void deleteCustomer(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			Long id = Long.parseLong(params.get("id"));
			String userInfo = params.get("userInfo");
			result = customerService.updateCustomer(id, null, null, null, null, null, null, null, null, userInfo, true);
		} catch (Exception e) {
			String errMsg = "请求异常";
			if(e instanceof DuplicateKeyException) {
				errMsg = "手机号重复，请调整";
			} 
			logger.error("Update Customer has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, errMsg);
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
	@RequestMapping("/getCustomersCount")
	public void getCustomersCount(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String keyword = params.get("keyword");
			result = customerService.getCustomersCount(keyword);
		} catch (Exception e) {
			logger.error("GetCustomersCount has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/getCustomers")
	public void getCustomers(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			int pageNo = Integer.parseInt(params.get("pageNo"));
			String keyword = params.get("keyword");
			result = customerService.getCustomers(pageNo, keyword);
		} catch (Exception e) {
			logger.error("Get Customers has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
	@RequestMapping("/sendMsg")
	public void sendMsg(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String ids = params.get("ids");
			String userInfo = params.get("userInfo");
			result = customerService.sendMsg(ids, userInfo);
		} catch (Exception e) {
			logger.error("Send Msg has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
}
