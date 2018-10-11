package com.biner.ru.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.model.Article;

public interface ArticleMapper extends BaseMapper<Article> {

	List<Article> getArticles(@Param("item")Article obj);
	
	int getArticlesCount(@Param("item")Article obj);
}
