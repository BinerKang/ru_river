package com.biner.ru.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biner.ru.common.MapResult;
import com.biner.ru.mapper.ArticleMapper;
import com.biner.ru.model.Article;
import com.biner.ru.model.ArticleCategory;
import com.biner.ru.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static int ARTICLE_PAGE_SIZE = 10;
	
	@Autowired
	private ArticleMapper articleMapper;
	
	public MapResult saveArticle(String title, String introduction, String image, String author,
			String publishTime, String link, String category) {
		// TODO校验参数
		Article ar = new Article();
		ar.setTitle(title);
		ar.setIntroduction(introduction);
		ar.setImage(image);
		ar.setAuthor(author);
		ar.setPublishTime(publishTime);
		ar.setLink(link);
		ar.setCategory(Integer.parseInt(category));
		articleMapper.save(ar);
		return new MapResult();
	}

	@Override
	public MapResult getArticlesCount() {
		Article ar = new Article();
		ar.setCategory(ArticleCategory.STUDY);
		int studyCount = articleMapper.getArticlesCount(ar);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("studyCount", studyCount);
		data.put("studyTotalPage", (studyCount / ARTICLE_PAGE_SIZE + 1));
		result.setData(data);
		return result;
	}

	@Override
	public MapResult getArticles(int pageNo) {
		Article ar = new Article();
		ar.setCategory(ArticleCategory.STUDY);
		pageNo = pageNo * ARTICLE_PAGE_SIZE;
		ar.setPageNo(pageNo);
		ar.setPageSize(ARTICLE_PAGE_SIZE);
		List<Article> articles = articleMapper.getArticles(ar);
		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("articles", articles);
		result.setData(data);
		return result;
	}
}
