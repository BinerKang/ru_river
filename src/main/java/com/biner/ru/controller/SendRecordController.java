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
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.SendRecordService;

@Controller
@RequestMapping("/sendRecord")
public class SendRecordController {

	@Autowired
	private SendRecordService sendRecordService;

	private Logger logger = Logger.getLogger(SendRecordController.class);

	@RequestMapping("/getSendRecordsCount")
	public void getSendRecordsCount(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String keyword = params.get("keyword");
			result = sendRecordService.getSendRecordsCount(keyword);
		} catch (Exception e) {
			logger.error("GetSendRecordsCount has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/getSendRecords")
	public void getSendRecords(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			int pageNo = Integer.parseInt(params.get("pageNo"));
			String keyword = params.get("keyword");
			result = sendRecordService.getSendRecords(pageNo, keyword);
		} catch (Exception e) {
			logger.error("Get SendRecords has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
}
