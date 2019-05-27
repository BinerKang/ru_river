package com.biner.ru.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.model.Contact;

public interface ContactMapper extends BaseMapper<Contact> {

	List<Contact> getContacts(@Param("item")Contact obj);
	
	int getContactsCount(@Param("item")Contact obj);
}
