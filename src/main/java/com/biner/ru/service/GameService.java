package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface GameService {

	public MapResult getRank(String gameType);

	public MapResult recordScore(String score, String userId);
}
