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
import com.biner.ru.service.BrowseRecordService;

@Controller
@RequestMapping("/browseRecord")
public class BrowseRecordController {

	@Autowired
	private BrowseRecordService browseRecordService;

	private Logger logger = Logger.getLogger(BrowseRecordController.class);

	@RequestMapping("/token/saveBrowseRecord")
	public void saveArticle(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String telphone = params.get("telphone");
			String ip = IpUtils.getIpAddr(request);
			result = browseRecordService.saveBrowseRecord(ip, telphone);
		} catch (Exception e) {
			logger.error("Save BrowseRecord has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
	@RequestMapping("/getBrowseRecordsCount")
	public void getBrowseRecordsCount(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String keyword = params.get("keyword");
			result = browseRecordService.getBrowseRecordsCount(keyword);
		} catch (Exception e) {
			logger.error("GetBrowseRecordsCount has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/getBrowseRecords")
	public void getBrowseRecords(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			int pageNo = Integer.parseInt(params.get("pageNo"));
			String keyword = params.get("keyword");
			result = browseRecordService.getBrowseRecords(pageNo, keyword);
		} catch (Exception e) {
			logger.error("Get BrowseRecords has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
}
