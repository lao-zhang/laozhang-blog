package com.zx.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zx.blog.bean.Article;

@Mapper
public interface ArticleMapper {

	//获取文章数
	int queryCount();
	
	//增添文章
	int addAtricle(Article article);
	
	//获取文章列表
	List<Article> queryAllArticles();
	
	//根据id获取单篇文章
	Article queryArticleById(String id);
}
