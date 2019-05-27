package com.biner.ru.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.model.SendRecord;

public interface SendRecordMapper extends BaseMapper<SendRecord> {

	List<SendRecord> getSendRecords(@Param("item")SendRecord obj);
	
	int getSendRecordsCount(@Param("item")SendRecord obj);
}
