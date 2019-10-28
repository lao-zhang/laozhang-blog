package com.zx.blog.bean;

import java.util.Date;

public class Article {
	
	private int id;
	private String title;
	private String content;
	private Date createTime;
	private Date updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createtime) {
		this.createTime = createtime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updatetime) {
		this.updateTime = updatetime;
	}

	public static void main() {
		
	}
		

}
