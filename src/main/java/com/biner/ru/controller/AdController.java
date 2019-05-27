package com.biner.ru.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.IpUtils;
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.BrowseRecordService;

@Controller
@RequestMapping("/ad")
public class AdController {

	private Logger logger = Logger.getLogger(AdController.class);

	@Autowired
	private BrowseRecordService browseRecordService;
	
	@RequestMapping("/show/{telphone}")
	public void saveArticle(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value="telphone") String telphone) {
		MapResult result = null;
		try {
			String ip = IpUtils.getIpAddr(request);
			result = browseRecordService.saveBrowseRecord(ip, telphone);
		} catch (Exception e) {
			logger.error("Save BrowseRecord has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		//重定向
		response.setStatus(302);
		try {
			response.sendRedirect(Constants.HOST_SERVER_BASE_URL + "pages/shiniman.html?tel="+telphone);
		} catch (IOException e) {
			logger.error("Redirect has error:", e);
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
	
}
