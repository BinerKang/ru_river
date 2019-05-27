package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface SendRecordService {

	public MapResult saveSendRecord(String telphone, String msg, String userInfo);
	
	public MapResult getSendRecordsCount(String keyword);
	
	public MapResult getSendRecords(int pageNo, String keyword);
}
