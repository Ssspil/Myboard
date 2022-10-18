package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

public class BoardService {

    public int selectListCount() {
        Connection conn = getConnection();
        int listCount = new BoardDao().selectListCount(conn);
        
        close();
        
        return listCount;
    }
    
    
    public ArrayList<Board> selectList(PageInfo pi) {
        Connection conn = getConnection();
        
        ArrayList<Board> list = new BoardDao().selectList(pi, conn);
        
        close();
        
        return list;
    }


    public int increaseCount(int boardNo) {
        Connection conn = getConnection();
        
        int result = new BoardDao().increaseCount(boardNo, conn);
        
        commitAndRollback(result, conn);
        
        return result;
    }
    
    
    public Board selectBoard(int boardNo) {
        Connection conn = getConnection();
        
        Board b = new BoardDao().selectBoard(boardNo, conn);
        
        close();
        
        return b;
    }


    public Attachment selectAttachment(int boardNo) {
        Connection conn = getConnection();
        
        Attachment at = new BoardDao().selectAttachment(boardNo, conn);
        
        close();
        
        return at;
    }
    
    public void commitAndRollback(int result, Connection conn) {
        if (result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        
        close();
    }


    public ArrayList<Category> selectCategoryList() {
        Connection conn = getConnection();
        
        ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
        
        close();
        
        return list;
    }

    public int insertBoard(Board b, Attachment at) {
        Connection conn = getConnection();
        
        int result1 = new BoardDao().insertBoard(b, conn);
        
        int result2 = 1;
        
        if (at != null) {
            result2 = new BoardDao().insertAttachment(at, conn);
        }
        
        // 트랜잭션 처리
        if (result1 > 0 && result2 > 0) {
            // 첨부파일이 없는 경우, insert가 성공했을때도 result2는 여전이 0이라 rollback처리가 될 수 있으므로 result2 = 1로 초기화했
            commit(conn);
        } else {
            rollback(conn);
        }
        
        close();
        
        return result1 * result2;
        // 헉시라도 하나라도 실패하여 0이더라도 아예 실패값을 반환받기 위해 곱셈결과를 리턴
    }





    
}
