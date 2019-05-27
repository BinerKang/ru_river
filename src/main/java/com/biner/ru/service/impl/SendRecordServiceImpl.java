package com.biner.ru.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.MapResult;
import com.biner.ru.mapper.SendRecordMapper;
import com.biner.ru.model.SendRecord;
import com.biner.ru.model.User;
import com.biner.ru.service.SendRecordService;

@Service
public class SendRecordServiceImpl implements SendRecordService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static int CUSTOMER_PAGE_SIZE = 200;
	
	@Autowired
	private SendRecordMapper sendRecordMapper;
	
	@Override
	public MapResult getSendRecordsCount(String keyword) {
		SendRecord con = new SendRecord();
		con.setKeyword(keyword);
		int count = sendRecordMapper.getSendRecordsCount(con);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", count);
		data.put("totalPage", (count / CUSTOMER_PAGE_SIZE + 1));
		result.setData(data);
		return result;
	}

	@Override
	public MapResult getSendRecords(int pageNo, String keyword) {
		SendRecord con = new SendRecord();
		pageNo = pageNo * CUSTOMER_PAGE_SIZE;
		con.setPageNo(pageNo);
		con.setKeyword(keyword);
		con.setPageSize(CUSTOMER_PAGE_SIZE);
		List<SendRecord> sendRecords = sendRecordMapper.getSendRecords(con);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sendRecords", sendRecords);
		result.setData(data);
		return result;
	}

	@Override
	public MapResult saveSendRecord(String telphone, String msg, String userInfo) {
		SendRecord con = new SendRecord();
		con.setTelphone(telphone);
		con.setMsg(msg);
		User u = JSONObject.parseObject(userInfo, User.class);
		con.setCreatedBy(u.getMail());
		con.setUpdatedBy(u.getMail());
		sendRecordMapper.save(con);
		return new MapResult();
	}
	
}
