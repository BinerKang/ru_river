package com.biner.ru.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biner.ru.common.MapResult;
import com.biner.ru.mapper.CustomerMapper;
import com.biner.ru.model.Customer;
import com.biner.ru.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static int CUSTOMER_PAGE_SIZE = 200;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public MapResult saveCustomer(String telphone, String name, String gender, String estate,
			String address, String url, Long phase, String info) {
		// TODO校验参数
		Customer cu = new Customer();
		cu.setTelphone(telphone);
		cu.setName(name);
		cu.setGender(gender);
		cu.setEstate(estate);
		cu.setAddress(address);
		cu.setUrl(url);
		cu.setPhase(phase);
		cu.setInfo(info);
		customerMapper.save(cu);
		return new MapResult();
	}

	@Override
	public MapResult getCustomersCount() {
		Customer cu = new Customer();
		int count = customerMapper.getCustomersCount(cu);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", count);
		data.put("totalPage", (count / CUSTOMER_PAGE_SIZE + 1));
		result.setData(data);
		return result;
	}

	@Override
	public MapResult getCustomers(int pageNo) {
		Customer cu = new Customer();
		pageNo = pageNo * CUSTOMER_PAGE_SIZE;
		cu.setPageNo(pageNo);
		cu.setPageSize(CUSTOMER_PAGE_SIZE);
		List<Customer> customers = customerMapper.getCustomers(cu);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("customers", customers);
		result.setData(data);
		return result;
	}
}
