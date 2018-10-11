package com.biner.ru.service;

import com.biner.ru.common.MapResult;

public interface ArticleService {

	public MapResult saveArticle(String title, String introduction, String image, String author,
			String publishTime, String link, String category);
	
	public MapResult getArticlesCount();
	
	public MapResult getArticles(int pageNo);
}
