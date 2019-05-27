package com.biner.ru.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.model.BrowseRecord;

public interface BrowseRecordMapper extends BaseMapper<BrowseRecord> {

	List<BrowseRecord> getBrowseRecords(@Param("item")BrowseRecord obj);
	
	int getBrowseRecordsCount(@Param("item")BrowseRecord obj);
}
