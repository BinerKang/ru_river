package com.biner.ru.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.biner.ru.common.MapResult;
import com.biner.ru.mapper.ScoreMapper;
import com.biner.ru.model.Score;
import com.biner.ru.service.GameService;
import com.biner.ru.webSocket.SpringWebSocketHandler;

@Service
public class GameServiceImpl implements GameService {

	private Logger logger = Logger.getLogger(this.getClass());
	// 将排名缓存在内存中，避免每次查询数据库
	private static Vector<Score> scores = new Vector<Score>();
	
	private final static int SCORE_PAGE_SIZE = 100;
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler webSocketHandler() {
        return new SpringWebSocketHandler();
    }
	
	public MapResult getRank(String gameType) {
		MapResult result = null;
		Map<String, Object> data = new HashMap<String, Object>();
		if (scores.size() == 0) {
			Score sc = new Score();
			sc.setType(gameType);
			sc.setPageNo(0);
			sc.setPageSize(SCORE_PAGE_SIZE);
			try {
				scores = scoreMapper.getAllRank(sc);
			} catch (Exception e) {
				logger.error("Get all ranks has error:::", e);
			}
		}
		result = new MapResult();
		data.put("scores", scores);
		result.setData(data);
		return result;
	}

	public MapResult recordScore(String score, String userId) {
		int scInt = Integer.parseInt(score);
		Score sc = new Score();
		sc.setUserId(Long.parseLong(userId));
		sc.setScore(scInt);
		sc.setType("1");
		scoreMapper.save(sc);
		if (scInt > scores.get(scores.size() - 1).getScore()) {// 推送给前端刷新
			//TODO 直接操作vector，不从数据库拿
			sc.setPageNo(0);
			sc.setPageSize(SCORE_PAGE_SIZE);
			scores = scoreMapper.getAllRank(sc);
			webSocketHandler().sendMessageToAll(JSONObject.toJSONString(scores));
		}
		return new MapResult();
	}
}
