package com.board.domain;

import java.util.Date;

public class BoardVO {
	
/* create table tbl_board(
		  no int not null auto_increment,
		  title varchar(50) not null,
		  content text not null,
		  writer varchar(30) not null,
		  regDate timestamp not null default now(),
		  viewCnt int default 0,
		  primary key(no)
		); */
	
	private int no;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private int viewCnt;
	
	public int getno() {
		return no;
	}
	public void setno(int no) {
		this.no = no;
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	
	
}
