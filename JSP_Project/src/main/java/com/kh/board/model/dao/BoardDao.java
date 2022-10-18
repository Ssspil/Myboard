package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardDao {


    private Properties prop = new Properties();
    
    public BoardDao() {
        try {
            prop.loadFromXML(new FileInputStream(BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath()));
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int selectListCount(Connection conn) {
        // select문 => ResultSet
        int listCount = 0;
        
        PreparedStatement psmt = null;
        
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectListCount");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                listCount = rset.getInt("COUNT");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return listCount;
        
    }
    
    
    public ArrayList<Board> selectList(PageInfo pi, Connection conn){
        // select문 => ResultSet
        ArrayList<Board> list = new ArrayList<>();
        
        PreparedStatement psmt = null;
        
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectList");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            /*
             * boardLimit 이 10이라고 가정
             * 
             * currentPage = 1 => 시작값 1, 끝값 10
             * currentPage = 2 => 시작값이 11 , 끝값 20
             * currentPage = 3 => 시작값이 21 , 끝값 30
             * 
             * 시작값 = (currentPage - 1) * boardLimit + 1;
             * 끝값 = 시작값 + boardLimit - 1;
             */
            int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
            int endRow = startRow + pi.getBoardLimit() - 1;
            
            psmt.setInt(1, startRow);
            psmt.setInt(2, endRow);
            
            rset = psmt.executeQuery();
            
            while(rset.next()) {
                list.add(new Board(rset.getInt("BOARD_NO"),
                          rset.getString("CATEGORY_NAME"),
                          rset.getString("BOARD_TITLE"),
                          rset.getString("USER_ID"),
                          rset.getInt("COUNT"),
                          rset.getDate("CREATE_DATE")));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return list;
    }

    public int increaseCount(int boardNo, Connection conn) {

        int result = 0;

        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("increaseCount");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            result = psmt.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        
        return result;
    }

    public Board selectBoard(int boardNo, Connection conn) {
        
        // select => ResultSet
        Board b = null;
        PreparedStatement psmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectBoard");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                b = new Board(rset.getInt("BOARD_NO"),
                              rset.getString("CATEGORY_NAME"),
                              rset.getString("BOARD_TITLE"),
                              rset.getString("BOARD_CONTENT"),
                              rset.getString("USER_ID"),
                              rset.getDate("CREATE_DATE"));
                
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return b;
    }

    public Attachment selectAttachment(int boardNo, Connection conn) {
        
        Attachment at = null;
        PreparedStatement psmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                at = new Attachment();
                
                at.setFileNo(rset.getInt("FILE_NO"));
                at.setOriginName(rset.getString("ORIGIN_NAME"));
                at.setChangeName(rset.getString("CHANGE_NAME"));
                at.setFilePath(rset.getString("FILE_PATH"));
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return at;
    }

    public ArrayList<Category> selectCategoryList(Connection conn) {
        ArrayList<Category> list = new ArrayList<>();
        
        PreparedStatement psmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectCategoryList");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            rset = psmt.executeQuery();
            
            while(rset.next()) {
                Category c = new Category();
                c.setCategoryName(rset.getString("CATEGORY_NAME"));
                c.setCategoryNo(rset.getInt(1));
                list.add(c);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return list;
        

    }

    public int insertBoard(Board b, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertBoard");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, Integer.parseInt(b.getCategory()));
            psmt.setString(2, b.getBoardTitle());
            psmt.setString(3, b.getBoardContent());
            psmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
            
            result = psmt.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public int insertAttachment(Attachment at, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, at.getOriginName());
            psmt.setString(2, at.getChangeName());
            psmt.setString(3,  at.getFilePath());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
}
