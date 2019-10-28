package com.zx.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zx.blog.bean.Article;
import com.zx.blog.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping("")
	public ModelAndView xxx(HttpServletRequest request) {
		ModelAndView mdv = new ModelAndView();
		Map<String,Object> resMap = new HashMap<>();
		if(MapUtils.getBooleanValue(resMap, "result")) {
			 
		}else {
			mdv.addObject("message",MapUtils.getString(resMap,"message",""));
			mdv.setViewName("error");
		}
		return mdv;
	}
	
	//新增文章
	@RequestMapping("/save")
	@ResponseBody
	public ModelAndView show(HttpServletRequest request) {
		String content = request.getParameter("editor1");
		ModelAndView mdv = new ModelAndView();
		Map<String,Object> resMap = articleService.addArticle(content);
		if(MapUtils.getBooleanValue(resMap, "result")) {
			String id = MapUtils.getString(resMap, "data","");
			System.out.println(id);
			mdv.addObject("article",(Article)MapUtils.getObject(articleService.queryOneArticleById(id),"data"));
			mdv.setViewName("article/detail");
		}else {
			mdv.addObject("message",MapUtils.getString(resMap,"message",""));
			mdv.setViewName("error");
		}
		return mdv;
	}
	
	//获取文章列表
	@RequestMapping("/articleList")
	public ModelAndView getArticleList(HttpServletRequest request) {
		ModelAndView mdv = new ModelAndView();
		Map<String,Object> resMap = articleService.queryArticleList();
		if(MapUtils.getBooleanValue(resMap, "result")) {
			@SuppressWarnings("unchecked")
			List<Article> articleList = (List<Article>)resMap.get("data");
			mdv.addObject("articleList",articleList);
			mdv.setViewName("article/articles");
		}else {
			mdv.addObject("message",MapUtils.getString(resMap,"message",""));
			mdv.setViewName("error");
		}
		return mdv;
	}
	
	//查看单篇文章
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(@PathVariable("id")String id) {
		ModelAndView mdv = new ModelAndView();
		Map<String,Object> resMap = articleService.queryOneArticleById(id);
		if(MapUtils.getBooleanValue(resMap, "result")) {
			Article atr = (Article)MapUtils.getObject(resMap, "data");
			mdv.addObject("article",atr);
			mdv.setViewName("article/detail");
		}else {
			mdv.addObject("message",MapUtils.getString(resMap,"message",""));
			mdv.setViewName("error");
		}
		return mdv;
	}
	
	//查看上/下一篇
	@RequestMapping("/pageTurn/{action}")
	public ModelAndView pageTurn(@PathVariable("action")String action) {
		ModelAndView mdv = new ModelAndView();
		String []strs = action.split("_");
		String act = strs[0];
		int id = Integer.parseInt(strs[1]);
		if("last".equals(act)) {
			if(id==1) {
				mdv = detail(id+"");
				mdv.addObject("message", "已经是第一篇");
				return mdv;
			}
			mdv = detail((--id)+"");
		}else if("next".equals(act)){
			int count = articleService.getArticleCount();
			if(id==count) {
				mdv = detail(id+"");
				mdv.addObject("message", "已经是最后一篇");
				return mdv;
			}
			mdv = detail((++id)+"");
		}
		return mdv;
	}
	
	
}
