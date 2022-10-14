package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
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







    
}
