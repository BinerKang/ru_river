package com.biner.ru.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.IpUtils;
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.CommonService;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	private CommonService commonService;

	private Logger logger = Logger.getLogger(CommonController.class);

	@RequestMapping("/token/getHomeInfo")
	public void getHomeInfo(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		String ip = IpUtils.getIpAddr(request);
		if(StringUtils.isEmpty(ip)) {
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "获取IP地址异常");
		} else {
			result = commonService.getHomeInfo(ip);
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/token/trickConvert")
	public void trickConvert(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		String cmd = "python -m art text \"%s\" %s";
		Process p = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String content = params.get("content");
			String font = params.get("font");
			cmd = String.format(cmd, content, font);
			p = Runtime.getRuntime().exec(cmd);
			 //取得命令结果的输出流  
            is = p.getInputStream();  
            //用缓冲器读行  
            br = new BufferedReader( new InputStreamReader(is, "UTF-8"));  
            String line = null;  
            StringBuffer art = new StringBuffer();
            while((line=br.readLine())!=null) {
            	art.append(line + "\r\n");
            }
            logger.info("Converted is: \r" + art);
            result = new MapResult();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("art", art);
            result.setData(data);
		} catch (IOException e) {
			logger.error("Trick Convert has error.", e);
			result = new MapResult(CodeMsg.FAIL, "转换异常");
		} finally {
			if (p != null) {
				p.destroy();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);	
	}
}
