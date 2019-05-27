package com.biner.ru.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.MapResult;
import com.biner.ru.mapper.ContactMapper;
import com.biner.ru.model.Contact;
import com.biner.ru.model.User;
import com.biner.ru.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static int CUSTOMER_PAGE_SIZE = 200;
	
	@Autowired
	private ContactMapper contactMapper;
	
	@Override
	public MapResult getContactsCount(String keyword, String isContacted) {
		Contact con = new Contact();
		con.setKeyword(keyword);
		con.setContactedTime(isContacted);
		int count = contactMapper.getContactsCount(con);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", count);
		data.put("totalPage", (count / CUSTOMER_PAGE_SIZE + 1));
		result.setData(data);
		return result;
	}

	@Override
	public MapResult getContacts(int pageNo, String keyword, String isContacted) {
		Contact con = new Contact();
		pageNo = pageNo * CUSTOMER_PAGE_SIZE;
		con.setPageNo(pageNo);
		con.setKeyword(keyword);
		con.setContactedTime(isContacted);
		con.setPageSize(CUSTOMER_PAGE_SIZE);
		List<Contact> contacts = contactMapper.getContacts(con);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("contacts", contacts);
		result.setData(data);
		return result;
	}

	@Override
	public MapResult saveContact(String ip, String telphone, String call, String gender,
			String info, String userInfo) {
		// TODO校验参数
		Contact con = new Contact();
		con.setIp(ip);
		con.setTelphone(telphone);
		con.setCall(call);
		con.setGender(gender);
		con.setInfo(info);
		con.setCreatedBy("system");
		con.setUpdatedBy("system");
		contactMapper.save(con);
		return new MapResult();
	}

	@Override
	public MapResult updateContact(Long id, String ip, String telphone, String call, String gender,
			String contactedTime, String info, String userInfo) {
		Contact con = new Contact();
		con.setId(id);
		con.setIp(ip);
		con.setTelphone(telphone);
		con.setCall(call);
		con.setGender(gender);
		con.setContactedTime(contactedTime);
		con.setInfo(info);
		User u = JSONObject.parseObject(userInfo, User.class);
		con.setUpdatedBy(u.getMail());
		contactMapper.update(con);
		return new MapResult();
	}
	
}
