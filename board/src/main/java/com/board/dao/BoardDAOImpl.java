package com.board.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

 @Inject
 private SqlSession sql;
 
 private static String namespace = "com.board.mappers.board3";

 // 寃뚯떆臾� 紐⑸줉
 @Override
 public List<BoardVO> list() throws Exception { 
  
  return sql.selectList(namespace + ".list");
 }

@Override
public void write(BoardVO vo) throws Exception {
	sql.insert(namespace + ".write", vo);
	
}


//寃뚯떆臾� 議고쉶
public BoardVO view(int bno) throws Exception {

return sql.selectOne(namespace + ".view", bno);
}

//寃뚯떆臾� �닔�젙
@Override
public void modify(BoardVO vo) throws Exception {
sql.update(namespace + ".modify", vo);
}

//寃뚯떆臾� �궘�젣
public void delete(int bno) throws Exception {
sql.delete(namespace + ".delete", bno);
}

//게시물 총 갯수
@Override
public int count() throws Exception {
return sql.selectOne(namespace + ".count"); 
}

//게시물 목록 + 페이징
@Override
public List listPage(int displayPost, int postNum) throws Exception {

HashMap data = new HashMap();

data.put("displayPost", displayPost);
data.put("postNum", postNum);

return sql.selectList(namespace + ".listPage", data);
}

//게시물 목록 + 페이징 + 검색
@Override
public List<BoardVO> listPageSearch(
int displayPost, int postNum, String searchType, String keyword) throws Exception {

HashMap<String, Object> data = new HashMap<String, Object>();

data.put("displayPost", displayPost);
data.put("postNum", postNum);

data.put("searchType", searchType);
data.put("keyword", keyword);

return sql.selectList(namespace + ".listPageSearch", data);
}

//게시물 총 갯수 + 검색 적용
@Override
public int searchCount(String searchType, String keyword) throws Exception {

HashMap data = new HashMap();

data.put("searchType", searchType);
data.put("keyword", keyword);

return sql.selectOne(namespace + ".searchCount", data); 
}


}
