package com.biner.ru.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.HttpUtils;

@Service
public class CommonService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public MapResult getIpLocation(String ip) {
		MapResult result = null;
		String ipUrl = Constants.FREEGEOIP_URL + ip;
		String location = null;
		try {
			String resultStr = HttpUtils.sendGet(ipUrl);
			JSONObject resultJson = JSONObject.parseObject(resultStr);
			if ("CN".equals(resultJson.getString("country_code"))) {
				String priCode = resultJson.getString("region_code");
				location = Constants.PROVINCE_CODE.get(priCode);
			} else {
				location = "远方";
			}
			result = new MapResult();
			Map<String, String> data = new HashMap<String, String>();
			data.put("province", location);
			result.setData(data);
		} catch (Exception e) {
			logger.error("Request Freegeoip has error:::", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION);
		}
		return result;
	}


}
