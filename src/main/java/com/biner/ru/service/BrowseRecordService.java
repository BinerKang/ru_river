package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface BrowseRecordService {

	public MapResult saveBrowseRecord(String ip, String telphone);
	
	public MapResult getBrowseRecordsCount(String keyword);
	
	public MapResult getBrowseRecords(int pageNo, String keyword);
}
