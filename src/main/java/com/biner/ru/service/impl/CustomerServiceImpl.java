package com.biner.ru.service.impl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.Constants;
import com.biner.ru.common.MapResult;
import com.biner.ru.mapper.CustomerMapper;
import com.biner.ru.mapper.SendRecordMapper;
import com.biner.ru.model.Customer;
import com.biner.ru.model.SendRecord;
import com.biner.ru.model.User;
import com.biner.ru.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static int CUSTOMER_PAGE_SIZE = 200;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private SendRecordMapper sendRecordMapper;
	
	@Override
	public MapResult saveCustomer(String telphone, String name, String gender, String estate,
			String address, String url, Long phase, String info, String userInfo) {
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
		User u = JSONObject.parseObject(userInfo, User.class);
		cu.setCreatedBy(u.getMail());
		cu.setUpdatedBy(u.getMail());
		customerMapper.save(cu);
		return new MapResult();
	}

	@Override
	public MapResult getCustomersCount(String keyword) {
		Customer cu = new Customer();
		cu.setKeyword(keyword);
		int count = customerMapper.getCustomersCount(cu);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", count);
		data.put("totalPage", (count / CUSTOMER_PAGE_SIZE + 1));
		result.setData(data);
		return result;
	}

	@Override
	public MapResult getCustomers(int pageNo, String keyword) {
		Customer cu = new Customer();
		pageNo = pageNo * CUSTOMER_PAGE_SIZE;
		cu.setPageNo(pageNo);
		cu.setKeyword(keyword);
		cu.setPageSize(CUSTOMER_PAGE_SIZE);
		List<Customer> customers = customerMapper.getCustomers(cu);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("customers", customers);
		result.setData(data);
		return result;
	}

	@Override
	public MapResult updateCustomer(Long id, String telphone, String name, String gender, String estate, String address,
			String url, Long phase, String info, String userInfo, Boolean deleted) {
		Customer cu = new Customer();
		cu.setId(id);
		cu.setTelphone(telphone);
		cu.setName(name);
		cu.setGender(gender);
		cu.setEstate(estate);
		cu.setAddress(address);
		cu.setUrl(url);
		cu.setPhase(phase);
		cu.setInfo(info);
		cu.setDeleted(deleted);
		User u = JSONObject.parseObject(userInfo, User.class);
		cu.setUpdatedBy(u.getMail());
		customerMapper.update(cu);
		return new MapResult();
	}

	@Override
	public MapResult sendMsg(String ids, String userInfo) {
		//查询出手机号
		List<String> telphones = customerMapper.queryTelphones(ids.split(","));
		Set<String> tels = new HashSet<String>(telphones);
		SendRecord re = new SendRecord();
		User u = JSONObject.parseObject(userInfo, User.class);
		re.setCreatedBy(u.getMail());
		re.setUpdatedBy(u.getMail());
		for(String tel: tels) {
			try {
				re.setMsg(String.format(Constants.AD_MSG_CONTENT, tel));
				re.setTelphone(tel);
				//入表
				sendRecordMapper.save(re);
				//调用api接口
				
				//
			} catch (Exception e) {
				logger.error("Send Msg has error", e);
			}
		}
		return new MapResult();
	}
	
}
