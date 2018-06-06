package com.biner.ru.mapper;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.model.User;

public interface UserMapper extends BaseMapper<User> {

	public int updateByMail(@Param("item")User obj);
}
