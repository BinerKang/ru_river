package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface CommonService {

	public MapResult getHomeInfo(String ip) ;

	public String getVerifyMailContent(String mail, String username) throws Exception ;
	
	public MapResult recordScore(String score, String userId);
}
