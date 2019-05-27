package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface ContactService {

	public MapResult saveContact(String ip, String telphone, String call, String gender, String info, String userInfo);
	
	public MapResult updateContact(Long id, String ip, String telphone, String call, String gender,
			String contactedTime, String info, String userInfo);
	
	public MapResult getContactsCount(String keyword, String isContacted);
	
	public MapResult getContacts(int pageNo, String keyword, String isContacted);
}
