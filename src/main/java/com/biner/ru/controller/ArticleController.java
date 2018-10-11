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
import com.biner.ru.util.CodeMsg;
import com.biner.ru.util.ResponseUtil;
import com.biner.ru.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	private Logger logger = Logger.getLogger(ArticleController.class);

	@RequestMapping("/token/saveArticle")
	public void saveArticle(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			String title = params.get("title");
			String introduction = params.get("introduction");
			String image = params.get("image");
			String author = params.get("author");
			String publishTime = params.get("publishTime");
			String link = params.get("link");
			String category = params.get("category");
			result = articleService.saveArticle(title, introduction, image, author, publishTime, link, category);
		} catch (Exception e) {
			logger.error("Save Article has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/token/getArticlesCount")
	public void getArticlesCount(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			result = articleService.getArticlesCount();
		} catch (Exception e) {
			logger.error("GetArticlesCount has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}

	@RequestMapping("/token/getArticles")
	public void getArticles(HttpServletRequest request, HttpServletResponse response) {
		MapResult result = null;
		try {
			Map<String, String> params = ParamThreadLocal.get();
			int pageNo = Integer.parseInt(params.get("pageNo"));
			result = articleService.getArticles(pageNo);
		} catch (Exception e) {
			logger.error("GetArticles has error:", e);
			result = new MapResult(CodeMsg.SERVER_EXCEPTION, "请求异常");
		}
		ResponseUtil.outputJSONResponseEncrypt(request, response, result);
	}
}
