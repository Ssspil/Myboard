package com.board.domain;

import java.util.Date;

public class ReplyVO {
	private int rbo;
	private int bno;
	private String writer;
	private String content;
	private Date regDate;
	public int getRbo() {
		return rbo;
	}
	public void setRbo(int rbo) {
		this.rbo = rbo;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
