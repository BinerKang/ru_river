package com.biner.ru.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.model.IpInfo;
import com.biner.ru.service.CommonService;
import com.biner.ru.util.AESUtils;
import com.biner.ru.util.MaxMindUtils;
import com.biner.ru.webSocket.SpringWebSocketHandler;

@Service
public class CommonServiceImpl implements CommonService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler webSocketHandler() {
        return new SpringWebSocketHandler();
    }
	
	public MapResult getHomeInfo(String ip) {
		MapResult result = null;
		Map<String, Object> data = new HashMap<String, Object>();
		IpInfo ipInfo = null;
		try {
			ipInfo = MaxMindUtils.getIpInfo(ip); 
		} catch (Exception e) {
			logger.error("Request Freegeoip has error:::", e);
		}
		result = new MapResult();
		data.put("ipInfo", ipInfo);
		result.setData(data);
		return result;
	}

	public String getVerifyMailContent(String mail, String username) throws Exception {
		String token = AESUtils.encrypt(mail + "_" + System.currentTimeMillis(), Constants.SECRET_KEY);
		String url = Constants.HOST_SERVER_BASE_URL + "#/authMail/" + token;
		return String.format(Constants.VERIFY_MAIL_CONTENT, username, mail, url);
	}
	
}
