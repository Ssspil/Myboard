package com.board.service;

import java.util.List;

import com.board.domain.BoardVO;

public interface BoardService {
	// 寃뚯떆臾� 紐⑸줉
	public List<BoardVO> list() throws Exception;
	
	// 寃뚯떆臾� �옉�꽦
	public void write(BoardVO vo) throws Exception;
	
	// 寃뚯떆臾� 議고쉶
	public BoardVO view(int bno) throws Exception;
	
	// 寃뚯떆臾� �닔�젙
	public void modify(BoardVO vo) throws Exception;
	
	// 寃뚯떆臾� �궘�젣
	public void delete(int bno) throws Exception;

	// 게시물 총 갯수
	public int count() throws Exception;
	
	// 게시물 목록 + 페이징
	public List listPage(int displayPost, int postNum) throws Exception;
	
	// 게시물 목록 + 페이징 + 검색
	public List<BoardVO> listPageSearch(
	  int displayPost, int postNum, String searchType, String keyword) throws Exception;
	
	// 게시물 총 갯수 + 검색 적용
	public int searchCount(String searchType, String keyword) throws Exception;
	
	
}
