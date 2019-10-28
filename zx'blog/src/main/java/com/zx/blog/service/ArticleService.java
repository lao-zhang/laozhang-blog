package com.zx.blog.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.blog.bean.Article;
import com.zx.blog.mapper.ArticleMapper;
import com.zx.blog.utils.DateUtil;

@Service
public class ArticleService {

	@Autowired
	ArticleMapper articleMapper;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	//获取文章数
	public int getArticleCount() {
		return articleMapper.queryCount();
	}
	
	//新增文章
	public Map<String,Object> addArticle(String articleStr){
		
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("result", false);
		try {
			if(StringUtils.isBlank(articleStr)) {
				resMap.put("message","内容为空！");
				return resMap;
			}
			int index1 = articleStr.indexOf("<strong>");
			int index2 = articleStr.indexOf("</strong>");
			String title = articleStr.substring(index1+8,index2);
			if(StringUtils.isBlank(title)) {
				resMap.put("message","文章标题为空！");
				return resMap;
			}
			String content = articleStr.substring(index2+14);
			Article article = new Article();
			article.setTitle(title);
			article.setContent(content);
			Date now = new Date();
			article.setCreateTime(now);
			article.setUpdateTime(now);
			int res = articleMapper.addAtricle(article);
			if(res>0) {
				resMap.put("result", true);
				resMap.put("data",articleMapper.queryCount());
			}else {
				resMap.put("message","新增文章失败！");
			}
		}catch(Exception e) {
			log.error("新增文章失败："+e);
			resMap.put("message","新增文章失败！"+e);
		}
		return resMap;
	}
	
	
	//获取单篇文章
	public Map<String,Object> queryOneArticleById(String id){
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("result", false);
		try {
			if(StringUtils.isBlank(id)) {
				resMap.put("message","传入文章id为空！");
				return resMap;
			}
			Article atr = articleMapper.queryArticleById(id);
			if(StringUtils.isBlank(atr.getTitle())) {
				resMap.put("message","文章内容不全，请补充完整后保存！");
				return resMap;
			}
			resMap.put("result",true);
			resMap.put("data", atr);
		}catch(Exception e) {
			log.error("获取单篇文章失败："+e);
			resMap.put("message","获取单篇文章失败！"+e);
		}
		return resMap;
	}
	
	//获取文章列表
	public Map<String,Object> queryArticleList(){
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("result", false);
		try {
			List<Article> articleList = articleMapper.queryAllArticles();
			if(CollectionUtils.isEmpty(articleList)) {
				resMap.put("message","获取文章列表为空！");
				return resMap;
			}else {
				resMap.put("result", true);
				resMap.put("data",articleList);
			}
		}catch(Exception e) {
			log.error("获取文章列表失败："+e);
			resMap.put("message","获取文章列表失败！"+e);
		}
		return resMap;
	}
}
