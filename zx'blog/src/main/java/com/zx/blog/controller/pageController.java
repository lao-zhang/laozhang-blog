package com.zx.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zx.blog.mapper.ArticleMapper;

@Controller
@RequestMapping("/page")
public class pageController {

    @Autowired
    ArticleMapper articleMapper;
    
    
    @RequestMapping("/editor")
    @ResponseBody
    public ModelAndView index() {
    	return new ModelAndView("pages/editor");
    }
    
    @RequestMapping("/home")
    public ModelAndView home() {
    	return new ModelAndView("pages/home");
    }
    //管理页面
    @RequestMapping("/manage")
    public ModelAndView manage() {
    	return new ModelAndView("pages/manage");
    }
}
