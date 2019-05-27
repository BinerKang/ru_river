package com.biner.ru.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.model.Customer;

public interface CustomerMapper extends BaseMapper<Customer> {

	List<Customer> getCustomers(@Param("item")Customer obj);
	
	int getCustomersCount(@Param("item")Customer obj);
	
	List<String> queryTelphones(@Param("ids")String[] ids);
}
