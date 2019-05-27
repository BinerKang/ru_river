package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface CustomerService {

	public MapResult saveCustomer(String telphone, String name, String gender, String estate,
			String address, String url, Long phase, String info, String userInfo);
	
	public MapResult updateCustomer(Long id, String telphone, String name, String gender, String estate,
			String address, String url, Long phase, String info, String userInfo, Boolean deleted);
	
	public MapResult getCustomersCount(String keyword);
	
	public MapResult getCustomers(int pageNo, String keyword);
	
	public MapResult sendMsg(String ids, String userInfo);
}
