package com.biner.ru.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biner.ru.common.MapResult;
import com.biner.ru.common.ParamThreadLocal;
import com.biner.ru.model.GameType;
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
	private GameService gameService;

	private Logger logger = Logger.getLogger(GameController.class);

	@RequestMapping("/token/getPinballRank")
	public void getPinballRank(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		result = gameService.getRank(GameType.PINBALL);
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/recordScore")
	public void recordScore(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String score = params.get("score");
			String userId = params.get("userId");
			result = gameService.recordScore(score, userId);
		} catch (Exception e) {
			logger.error("Record score has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
}
