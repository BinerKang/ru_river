package com.biner.ru.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.MapResult;
import com.biner.ru.mapper.BrowseRecordMapper;
import com.biner.ru.model.BrowseRecord;
import com.biner.ru.model.User;
import com.biner.ru.service.BrowseRecordService;

@Service
public class BrowseRecordServiceImpl implements BrowseRecordService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static int CUSTOMER_PAGE_SIZE = 200;
	
	@Autowired
	private BrowseRecordMapper browseRecordMapper;
	
	@Override
	public MapResult getBrowseRecordsCount(String keyword) {
		BrowseRecord con = new BrowseRecord();
		con.setKeyword(keyword);
		int count = browseRecordMapper.getBrowseRecordsCount(con);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", count);
		data.put("totalPage", (count / CUSTOMER_PAGE_SIZE + 1));
		result.setData(data);
		return result;
	}

	@Override
	public MapResult getBrowseRecords(int pageNo, String keyword) {
		BrowseRecord con = new BrowseRecord();
		pageNo = pageNo * CUSTOMER_PAGE_SIZE;
		con.setPageNo(pageNo);
		con.setKeyword(keyword);
		con.setPageSize(CUSTOMER_PAGE_SIZE);
		List<BrowseRecord> browseRecords = browseRecordMapper.getBrowseRecords(con);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("browseRecords", browseRecords);
		result.setData(data);
		return result;
	}

	@Override
	@Async
	public MapResult saveBrowseRecord(String ip, String telphone) {
		// TODO校验参数
		BrowseRecord con = new BrowseRecord();
		con.setIp(ip);
		con.setTelphone(telphone);
		con.setCreatedBy("system");
		con.setUpdatedBy("system");
		browseRecordMapper.save(con);
		return new MapResult();
	}

}
