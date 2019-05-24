package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface CustomerService {

	public MapResult saveCustomer(String telphone, String name, String gender, String estate,
			String address, String url, Long phase, String info);
	
	public MapResult getCustomersCount();
	
	public MapResult getCustomers(int pageNo);
}
