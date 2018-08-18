package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface UserService {

	public MapResult register(String sessionId, String username, String password,
			String mail, String code, String realCode, String score) throws Exception ;
		

	public MapResult login(String sessionId, String password, String mail, String code, 
			String realCode, String score) throws Exception ;
	
	public MapResult authMail(String sessionId, String code) throws Exception ;
	
}
