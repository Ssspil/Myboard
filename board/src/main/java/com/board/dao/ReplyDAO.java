package com.board.dao;

import java.util.List;

import com.board.domain.ReplyVO;

public interface ReplyDAO {

	
	// ´ñ±Û Á¶È¸
	public List<ReplyVO> list(int bno) throws Exception;

	// ´ñ±Û Á¶È¸
	public void write(ReplyVO vo) throws Exception;

	// ´ñ±Û ¼öÁ¤
	public void modify(ReplyVO vo) throws Exception;

	// ´ñ±Û »èÁ¦
	public void delete(ReplyVO vo) throws Exception;
}
